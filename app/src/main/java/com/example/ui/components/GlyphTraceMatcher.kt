package com.example.ui.components

import android.graphics.Bitmap
import android.graphics.Canvas as AndroidCanvas
import android.graphics.Color as AndroidColor
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Layout
import android.text.StaticLayout
import android.text.TextDirectionHeuristics
import android.text.TextPaint
import androidx.compose.ui.geometry.Offset
import kotlin.math.ceil
import kotlin.math.sqrt

/** A bitmap silhouette and its one-pixel medial skeleton, both derived from the selected glyph. */
class GlyphTraceTemplate internal constructor(
  val bitmap: Bitmap,
  val skeleton: List<Offset>,
  val components: List<List<Offset>>,
  val canvasWidth: Int,
  val canvasHeight: Int
)

data class GlyphTraceMatch(
  val accepted: Boolean,
  val precision: Float,
  val coverage: Float,
  val missingComponents: Int
)

/**
 * Builds a trace reference from the same Arabic glyph string shown on the form buttons.
 * The generated skeleton is used for shape matching; it is not a claim about calligraphic
 * stroke order, which can vary by teaching style.
 */
object GlyphTraceMatcher {
  private const val MAX_BITMAP_DIMENSION = 320
  private const val ALPHA_THRESHOLD = 12
  private const val SAMPLE_SPACING_PX = 2.5f
  private const val TOLERANCE_FRACTION = 0.035f
  private const val MIN_PRECISION = 0.86f
  const val REQUIRED_COVERAGE = 0.84f
  private const val MIN_COMPONENT_COVERAGE = 0.68f
  private const val MIN_STROKE_LENGTH_FRACTION = 0.14f

  fun createTemplate(
    glyph: String,
    canvasWidth: Int,
    canvasHeight: Int,
    textSizePx: Float
  ): GlyphTraceTemplate? {
    if (glyph.isBlank() || canvasWidth < 2 || canvasHeight < 2 || textSizePx <= 0f) return null

    val scale = minOf(
      1f,
      MAX_BITMAP_DIMENSION / maxOf(canvasWidth, canvasHeight).toFloat()
    )
    val bitmapWidth = (canvasWidth * scale).toInt().coerceAtLeast(2)
    val bitmapHeight = (canvasHeight * scale).toInt().coerceAtLeast(2)
    val bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888)

    val paint = TextPaint(Paint.ANTI_ALIAS_FLAG or Paint.SUBPIXEL_TEXT_FLAG).apply {
      color = AndroidColor.argb(54, 95, 105, 125)
      textSize = textSizePx * scale
      typeface = Typeface.create(
        Typeface.create("sans-serif", Typeface.NORMAL),
        900,
        false
      )
      textAlign = Paint.Align.LEFT
      isSubpixelText = true
    }
    val maxTextWidth = bitmapWidth * 0.88f
    var layout: StaticLayout? = null
    for (attempt in 0 until 8) {
      val measuredWidth = paint.measureText(glyph)
      if (measuredWidth > maxTextWidth && measuredWidth > 0f) {
        paint.textSize *= maxTextWidth / measuredWidth
      }

      val candidate = try {
        StaticLayout.Builder
          .obtain(glyph, 0, glyph.length, paint, bitmapWidth)
          .setAlignment(Layout.Alignment.ALIGN_CENTER)
          .setTextDirection(TextDirectionHeuristics.RTL)
          .setIncludePad(false)
          .setMaxLines(1)
          .build()
      } catch (_: RuntimeException) {
        bitmap.recycle()
        return null
      }
      if (candidate.lineCount != 1 || candidate.height <= 0) break
      if (candidate.height <= bitmapHeight * 0.90f) {
        layout = candidate
        break
      }
      paint.textSize *= (bitmapHeight * 0.86f / candidate.height).coerceIn(0.5f, 0.9f)
    }

    val fittedLayout = layout
    if (fittedLayout == null) {
      bitmap.recycle()
      return null
    }

    val baseline = bitmapHeight * 0.72f
    val top = (baseline - fittedLayout.height * 0.78f)
      .coerceIn(0f, (bitmapHeight - fittedLayout.height).toFloat())
    val androidCanvas = AndroidCanvas(bitmap)
    androidCanvas.save()
    androidCanvas.translate(0f, top)
    fittedLayout.draw(androidCanvas)
    androidCanvas.restore()

    val pixels = IntArray(bitmapWidth * bitmapHeight)
    bitmap.getPixels(pixels, 0, bitmapWidth, 0, 0, bitmapWidth, bitmapHeight)
    val mask = BooleanArray(pixels.size) { index ->
      ((pixels[index] ushr 24) and 0xFF) >= ALPHA_THRESHOLD
    }
    val skeletonMask = thin(mask, bitmapWidth, bitmapHeight)
    val thinnedSkeleton = skeletonMask.indices
      .asSequence()
      .filter { skeletonMask[it] }
      .map { index -> Offset((index % bitmapWidth).toFloat(), (index / bitmapWidth).toFloat()) }
      .toList()
    // Some Arabic glyphs become very small or fragmented after thinning at low bitmap scales.
    // Retain the rendered ink mask in that case rather than silently disabling tracing.
    val skeleton = if (thinnedSkeleton.size >= 8) {
      thinnedSkeleton
    } else {
      mask.indices
        .asSequence()
        .filter { mask[it] }
        .map { index -> Offset((index % bitmapWidth).toFloat(), (index / bitmapWidth).toFloat()) }
        .toList()
    }
    if (skeleton.size < 8) {
      bitmap.recycle()
      return null
    }

    val components = findComponents(skeletonMask, bitmapWidth, bitmapHeight)
      .filter { it.isNotEmpty() }
    return GlyphTraceTemplate(
      bitmap = bitmap,
      skeleton = skeleton,
      components = components.ifEmpty { listOf(skeleton) },
      canvasWidth = canvasWidth,
      canvasHeight = canvasHeight
    )
  }

  fun evaluate(template: GlyphTraceTemplate?, strokes: List<List<Offset>>): GlyphTraceMatch {
    if (template == null || template.skeleton.isEmpty()) {
      return GlyphTraceMatch(false, 0f, 0f, 1)
    }

    val drawn = sampleStrokes(template, strokes)
    if (drawn.size < 12) return GlyphTraceMatch(false, 0f, 0f, template.components.size)

    val tolerance = minOf(template.bitmap.width, template.bitmap.height) * TOLERANCE_FRACTION
    val toleranceSquared = tolerance * tolerance
    val precision = drawn.count { point ->
      nearestDistanceSquared(point, template.skeleton) <= toleranceSquared
    }.toFloat() / drawn.size

    val coverage = template.skeleton.count { target ->
      nearestDistanceSquared(target, drawn) <= toleranceSquared
    }.toFloat() / template.skeleton.size

    val missingComponents = template.components.count { component ->
      val componentCoverage = component.count { target ->
        nearestDistanceSquared(target, drawn) <= toleranceSquared
      }.toFloat() / component.size
      componentCoverage < MIN_COMPONENT_COVERAGE
    }

    val totalLength = totalStrokeLength(template, strokes)
    val minimumLength = minOf(template.bitmap.width, template.bitmap.height) * MIN_STROKE_LENGTH_FRACTION
    val enoughLength = totalLength >= minimumLength
    val accepted = precision >= MIN_PRECISION &&
      coverage >= REQUIRED_COVERAGE &&
      missingComponents == 0 &&
      enoughLength

    return GlyphTraceMatch(accepted, precision, coverage, missingComponents)
  }

  private fun sampleStrokes(template: GlyphTraceTemplate, strokes: List<List<Offset>>): List<Offset> {
    val scaleX = template.bitmap.width.toFloat() / template.canvasWidth
    val scaleY = template.bitmap.height.toFloat() / template.canvasHeight
    val samples = ArrayList<Offset>()

    strokes.forEach { stroke ->
      if (stroke.isEmpty()) return@forEach
      if (stroke.size == 1) {
        samples += Offset(stroke[0].x * scaleX, stroke[0].y * scaleY)
      } else {
        for (index in 0 until stroke.lastIndex) {
          val start = Offset(stroke[index].x * scaleX, stroke[index].y * scaleY)
          val end = Offset(stroke[index + 1].x * scaleX, stroke[index + 1].y * scaleY)
          val dx = end.x - start.x
          val dy = end.y - start.y
          val distance = sqrt(dx * dx + dy * dy)
          val steps = ceil(distance / SAMPLE_SPACING_PX).toInt().coerceAtLeast(1)
          for (step in 0 until steps) {
            val fraction = step.toFloat() / steps
            samples += Offset(start.x + dx * fraction, start.y + dy * fraction)
          }
        }
        val last = stroke.last()
        samples += Offset(last.x * scaleX, last.y * scaleY)
      }
    }

    if (samples.size <= 1600) return samples
    val stride = samples.size.toFloat() / 1600f
    return List(1600) { index -> samples[(index * stride).toInt().coerceAtMost(samples.lastIndex)] }
  }

  private fun totalStrokeLength(template: GlyphTraceTemplate, strokes: List<List<Offset>>): Float {
    val scaleX = template.bitmap.width.toFloat() / template.canvasWidth
    val scaleY = template.bitmap.height.toFloat() / template.canvasHeight
    var length = 0f
    strokes.forEach { stroke ->
      for (index in 0 until stroke.lastIndex) {
        val dx = (stroke[index + 1].x - stroke[index].x) * scaleX
        val dy = (stroke[index + 1].y - stroke[index].y) * scaleY
        length += sqrt(dx * dx + dy * dy)
      }
    }
    return length
  }

  private fun nearestDistanceSquared(point: Offset, candidates: List<Offset>): Float {
    var best = Float.POSITIVE_INFINITY
    candidates.forEach { candidate ->
      val dx = point.x - candidate.x
      val dy = point.y - candidate.y
      val distance = dx * dx + dy * dy
      if (distance < best) best = distance
    }
    return best
  }

  private fun thin(source: BooleanArray, width: Int, height: Int): BooleanArray {
    val pixels = source.copyOf()
    var changed: Boolean
    var iterations = 0
    do {
      changed = false
      for (phase in 0..1) {
        val remove = ArrayList<Int>()
        for (y in 1 until height - 1) {
          for (x in 1 until width - 1) {
            val index = y * width + x
            if (!pixels[index]) continue

            val neighbors = booleanArrayOf(
              pixels[(y - 1) * width + x],
              pixels[(y - 1) * width + x + 1],
              pixels[y * width + x + 1],
              pixels[(y + 1) * width + x + 1],
              pixels[(y + 1) * width + x],
              pixels[(y + 1) * width + x - 1],
              pixels[y * width + x - 1],
              pixels[(y - 1) * width + x - 1]
            )
            val neighborCount = neighbors.count { it }
            if (neighborCount !in 2..6) continue

            var transitions = 0
            for (neighborIndex in neighbors.indices) {
              if (!neighbors[neighborIndex] && neighbors[(neighborIndex + 1) % neighbors.size]) {
                transitions++
              }
            }
            if (transitions != 1) continue

            val p2 = neighbors[0]
            val p4 = neighbors[2]
            val p6 = neighbors[4]
            val p8 = neighbors[6]
            val shouldRemove = if (phase == 0) {
              !(p2 && p4 && p6) && !(p4 && p6 && p8)
            } else {
              !(p2 && p4 && p8) && !(p2 && p6 && p8)
            }
            if (shouldRemove) remove += index
          }
        }
        if (remove.isNotEmpty()) {
          remove.forEach { pixels[it] = false }
          changed = true
        }
      }
      iterations++
    } while (changed && iterations < 64)
    return pixels
  }

  private fun findComponents(mask: BooleanArray, width: Int, height: Int): List<List<Offset>> {
    val visited = BooleanArray(mask.size)
    val components = ArrayList<List<Offset>>()
    val neighbors = intArrayOf(-width - 1, -width, -width + 1, -1, 1, width - 1, width, width + 1)

    mask.indices.forEach { start ->
      if (!mask[start] || visited[start]) return@forEach
      val component = ArrayList<Offset>()
      val queue = ArrayDeque<Int>()
      queue.addLast(start)
      visited[start] = true

      while (queue.isNotEmpty()) {
        val index = queue.removeFirst()
        val x = index % width
        val y = index / width
        component += Offset(x.toFloat(), y.toFloat())
        for (delta in neighbors) {
          val next = index + delta
          if (next < 0 || next >= mask.size || visited[next] || !mask[next]) continue
          val nextX = next % width
          val nextY = next / width
          if (kotlin.math.abs(nextX - x) > 1 || kotlin.math.abs(nextY - y) > 1) continue
          visited[next] = true
          queue.addLast(next)
        }
      }
      components += component
    }
    return components
  }
}
