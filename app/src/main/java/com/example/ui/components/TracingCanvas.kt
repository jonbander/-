package com.example.ui.components

import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
import com.example.ui.theme.KidsCanvasBg
import com.example.ui.theme.KidsCanvasGrid
import com.example.ui.theme.KidsPrimary
import com.example.ui.theme.KidsSecondary
import com.example.ui.theme.KidsTertiary
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class DrawnStroke(
  val path: List<Offset>,
  val color: Color,
  val strokeWidth: Float
)

data class SparkleParticle(
  val position: Offset,
  val color: Color,
  val radius: Float
)

@Composable
fun TracingCanvas(
  glyph: String,
  primaryColor: Color,
  sessionKey: String,
  onCompleted: () -> Unit,
  modifier: Modifier = Modifier
) {
  val strokes = remember(sessionKey) { mutableStateListOf<DrawnStroke>() }
  var currentPoints by remember(sessionKey) { mutableStateOf<List<Offset>>(emptyList()) }
  val sparkles = remember(sessionKey) { mutableStateListOf<SparkleParticle>() }
  var hasCompleted by remember(sessionKey) { mutableStateOf(false) }
  var validationMessage by remember(sessionKey) { mutableStateOf<String?>(null) }
  var canvasPixelSize by remember(sessionKey) { mutableStateOf(IntSize.Zero) }
  val glyphTextSizePx = with(LocalDensity.current) { 210.sp.toPx() }
  var glyphTemplate by remember(sessionKey) { mutableStateOf<GlyphTraceTemplate?>(null) }
  LaunchedEffect(glyph, canvasPixelSize, glyphTextSizePx) {
    glyphTemplate = null
    if (canvasPixelSize.width > 0 && canvasPixelSize.height > 0) {
      glyphTemplate = withContext(Dispatchers.Default) {
        GlyphTraceMatcher.createTemplate(
          glyph = glyph,
          canvasWidth = canvasPixelSize.width,
          canvasHeight = canvasPixelSize.height,
          textSizePx = glyphTextSizePx
        )
      }
    }
  }

  val brushColors = listOf(
    KidsPrimary,
    KidsSecondary,
    KidsTertiary,
    Color(0xFF2EC4B6),
    Color(0xFF8338EC),
    Color(0xFFFFBE0B)
  )
  var selectedColor by remember { mutableStateOf(brushColors[0]) }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 8.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Canvas container with paper texture & notebook baseline
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(310.dp)
        .shadow(8.dp, RoundedCornerShape(24.dp))
        .clip(RoundedCornerShape(24.dp))
        .background(KidsCanvasBg)
        .border(3.dp, primaryColor.copy(alpha = 0.4f), RoundedCornerShape(24.dp))
    ) {
      Canvas(
        modifier = Modifier
          .fillMaxSize()
          .onSizeChanged { canvasPixelSize = it }
          .pointerInput(selectedColor, sessionKey) {
            detectDragGestures(
              onDragStart = { offset ->
                currentPoints = listOf(offset)
                sparkles.add(SparkleParticle(offset, selectedColor, 12f))
              },
              onDrag = { change, _ ->
                change.consume()
                currentPoints = currentPoints + change.position
                if (currentPoints.size % 4 == 0) {
                  sparkles.add(SparkleParticle(change.position, selectedColor, 10f))
                  if (sparkles.size > 25) sparkles.removeAt(0)
                }
              },
              onDragEnd = {
                if (currentPoints.isNotEmpty()) {
                  strokes.add(DrawnStroke(currentPoints, selectedColor, 30f))
                  currentPoints = emptyList()
                  validationMessage = null
                }
              },
              onDragCancel = {
                currentPoints = emptyList()
              }
            )
          }
      ) {
        val width = size.width
        val height = size.height

        // 1. Draw notebook guide lines
        val baselineY = height * 0.72f
        val toplineY = height * 0.28f
        val midlineY = height * 0.50f

        // Top line (dashed gray)
        drawLine(
          color = KidsCanvasGrid,
          start = Offset(20f, toplineY),
          end = Offset(width - 20f, toplineY),
          strokeWidth = 2.dp.toPx()
        )
        // Mid line (dashed)
        drawLine(
          color = KidsCanvasGrid.copy(alpha = 0.8f),
          start = Offset(20f, midlineY),
          end = Offset(width - 20f, midlineY),
          strokeWidth = 2.dp.toPx()
        )
        // Arabic writing baseline (solid warm coral red)
        drawLine(
          color = Color(0xFFFF6B6B).copy(alpha = 0.5f),
          start = Offset(16f, baselineY),
          end = Offset(width - 16f, baselineY),
          strokeWidth = 3.5.dp.toPx()
        )

        // 2. Show the selected form itself as the reference, using the same glyph string
        // displayed by the form buttons. The matcher validates against this exact bitmap.
        glyphTemplate?.let { template ->
          drawContext.canvas.nativeCanvas.drawBitmap(
            template.bitmap,
            null,
            Rect(0, 0, width.roundToInt(), height.roundToInt()),
            Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
          )
        }

        // 3. Render completed strokes
        strokes.forEach { stroke ->
          if (stroke.path.size > 1) {
            val composePath = Path().apply {
              moveTo(stroke.path.first().x, stroke.path.first().y)
              for (i in 1 until stroke.path.size) {
                lineTo(stroke.path[i].x, stroke.path[i].y)
              }
            }
            drawPath(
              path = composePath,
              color = stroke.color,
              style = Stroke(
                width = stroke.strokeWidth,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
              )
            )
          }
        }

        // 4. Render currently active finger stroke
        if (currentPoints.size > 1) {
          val activePath = Path().apply {
            moveTo(currentPoints.first().x, currentPoints.first().y)
            for (i in 1 until currentPoints.size) {
              lineTo(currentPoints[i].x, currentPoints[i].y)
            }
          }
          drawPath(
            path = activePath,
            color = selectedColor,
            style = Stroke(
              width = 32f,
              cap = StrokeCap.Round,
              join = StrokeJoin.Round
            )
          )
        }

        // 5. Draw playful sparkles around finger
        sparkles.forEach { sp ->
          drawCircle(
            color = sp.color.copy(alpha = 0.75f),
            radius = sp.radius,
            center = sp.position
          )
        }
      }

      // Small hint badge in the corner
      Surface(
        modifier = Modifier
          .align(Alignment.TopEnd)
          .padding(12.dp),
        color = Color.White.copy(alpha = 0.9f),
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 2.dp
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = glyph,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            color = primaryColor
          )
          Spacer(modifier = Modifier.width(8.dp))
          Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = KidsSecondary,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "تتبّع شكل الحرف بإصبعك",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Palette & Action Controls
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Color choices
      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        brushColors.forEach { color ->
          val isSelected = selectedColor == color
          Box(
            modifier = Modifier
              .size(if (isSelected) 36.dp else 28.dp)
              .shadow(if (isSelected) 4.dp else 1.dp, CircleShape)
              .clip(CircleShape)
              .background(color)
              .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) Color.White else Color.Transparent,
                shape = CircleShape
              )
              .pointerInput(color) {
                detectDragGestures(onDragStart = { selectedColor = color }, onDrag = { _, _ -> })
              }
          )
        }
      }

      // Undo & Clear buttons
      Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        IconButton(
          onClick = {
            if (strokes.isNotEmpty()) {
              strokes.removeAt(strokes.lastIndex)
              validationMessage = null
            }
          },
          modifier = Modifier
            .size(44.dp)
            .background(Color.White, CircleShape)
            .border(1.5.dp, KidsCanvasGrid, CircleShape)
            .testTag("undo_stroke_button")
        ) {
          Icon(
            imageVector = Icons.Default.Undo,
            contentDescription = "تراجع",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        IconButton(
          onClick = {
            strokes.clear()
            sparkles.clear()
            validationMessage = null
          },
          modifier = Modifier
            .size(44.dp)
            .background(Color.White, CircleShape)
            .border(1.5.dp, KidsCanvasGrid, CircleShape)
            .testTag("clear_canvas_button")
        ) {
          Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "مسح اللوحة",
            tint = Color(0xFFFF6B6B)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Big Celebratory "أحسنت / تم الإنجاز" Button for 5-Year-Olds
    Button(
      onClick = {
        if (strokes.isNotEmpty() && !hasCompleted) {
          val result = GlyphTraceMatcher.evaluate(glyphTemplate, strokes.map { it.path })
          if (result.accepted) {
            hasCompleted = true
            validationMessage = null
            onCompleted()
          } else {
            validationMessage = when {
              glyphTemplate == null -> "لم أتمكن من تجهيز شكل الحرف. أعد اختيار الحرف ثم حاول مجددًا."
              result.missingComponents > 0 -> "لا تنسَ نقاط الحرف أو الهمزة، وأكمل الرسم فوق النموذج."
              result.coverage < 0.75f -> "أكمل رسم جسم الحرف ونقاطه فوق النموذج، ثم حاول مرة أخرى."
              else -> "الرسم بعيد عن شكل الحرف. اتبع النموذج وحاول مرة أخرى."
            }
          }
        }
      },
      enabled = strokes.isNotEmpty() && !hasCompleted,
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .testTag("finish_tracing_button"),
      shape = RoundedCornerShape(20.dp),
      colors = ButtonDefaults.buttonColors(
        containerColor = KidsSecondary,
        disabledContainerColor = KidsSecondary.copy(alpha = 0.45f)
      ),
      elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.size(26.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = when {
            hasCompleted -> "تم تسجيل هذا الشكل ✓"
            strokes.isEmpty() -> "ارسم الحرف أولاً ✍️"
            validationMessage != null -> "حاول مرة أخرى ✍️"
            else -> "تحقق من مطابقة الرسم ✓"
          },
          fontSize = 18.sp,
          fontWeight = FontWeight.ExtraBold,
          color = Color.White
        )
      }
    }
    validationMessage?.let { message ->
      Text(
        text = message,
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp)
          .testTag("tracing_validation_message"),
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = KidsTertiary
      )
    }
  }
}
