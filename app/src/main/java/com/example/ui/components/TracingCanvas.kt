package com.example.ui.components

import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GuidePoint
import com.example.ui.theme.KidsCanvasBg
import com.example.ui.theme.KidsCanvasGrid
import com.example.ui.theme.KidsPrimary
import com.example.ui.theme.KidsSecondary
import com.example.ui.theme.KidsTertiary
import kotlin.math.cos
import kotlin.math.sin

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
  guidePoints: List<GuidePoint>,
  primaryColor: Color,
  sessionKey: String,
  onCompleted: () -> Unit,
  modifier: Modifier = Modifier
) {
  val strokes = remember(sessionKey) { mutableStateListOf<DrawnStroke>() }
  var currentPoints by remember(sessionKey) { mutableStateOf<List<Offset>>(emptyList()) }
  val sparkles = remember(sessionKey) { mutableStateListOf<SparkleParticle>() }
  var hasCompleted by remember(sessionKey) { mutableStateOf(false) }

  val brushColors = listOf(
    KidsPrimary,
    KidsSecondary,
    KidsTertiary,
    Color(0xFF2EC4B6),
    Color(0xFF8338EC),
    Color(0xFFFFBE0B)
  )
  var selectedColor by remember { mutableStateOf(brushColors[0]) }

  // Pulsating animation for start dot
  val pulseScale = remember { Animatable(1f) }
  LaunchedEffect(Unit) {
    pulseScale.animateTo(
      targetValue = 1.35f,
      animationSpec = infiniteRepeatable(
        animation = tween(700, easing = FastOutSlowInEasing),
        repeatMode = RepeatMode.Reverse
      )
    )
  }

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
        .pointerInput(selectedColor, sessionKey) {
          detectDragGestures(
            onDragStart = { offset ->
              currentPoints = listOf(offset)
              // Add a couple sparkles
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
              }
            },
            onDragCancel = {
              currentPoints = emptyList()
            }
          )
        }
    ) {
      Canvas(modifier = Modifier.fillMaxSize()) {
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

        // 2. Render big faint target Arabic glyph in the center background
        drawContext.canvas.nativeCanvas.apply {
          val paint = Paint().apply {
            textSize = 210.sp.toPx()
            color = android.graphics.Color.argb(45, 80, 100, 140)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
            isFakeBoldText = true
          }
          // Center glyph coordinates
          drawText(glyph, width / 2f, baselineY - 12f, paint)
        }

        // 3. Draw guide waypoints and start arrow
        guidePoints.forEach { pt ->
          val ptOffset = Offset(pt.x * width, pt.y * height)
          if (pt.isStart) {
            // Big pulsating start target
            drawCircle(
              color = Color(0xFF2EC4B6).copy(alpha = 0.35f),
              radius = 26.dp.toPx() * pulseScale.value,
              center = ptOffset
            )
            drawCircle(
              color = Color(0xFF2EC4B6),
              radius = 16.dp.toPx(),
              center = ptOffset
            )
            // Start star icon or number
            drawContext.canvas.nativeCanvas.apply {
              val numPaint = Paint().apply {
                textSize = 14.sp.toPx()
                color = android.graphics.Color.WHITE
                textAlign = Paint.Align.CENTER
                isFakeBoldText = true
              }
              drawText("ابدأ", ptOffset.x, ptOffset.y + 5.dp.toPx(), numPaint)
            }
          } else if (pt.isDot) {
            // Dot target (pulsating gold circle)
            drawCircle(
              color = KidsSecondary.copy(alpha = 0.3f),
              radius = 18.dp.toPx() * pulseScale.value,
              center = ptOffset
            )
            drawCircle(
              color = KidsSecondary,
              radius = 11.dp.toPx(),
              center = ptOffset
            )
          } else {
            // Dotted milestone waypoint
            drawCircle(
              color = primaryColor.copy(alpha = 0.7f),
              radius = 10.dp.toPx(),
              center = ptOffset
            )
            drawCircle(
              color = Color.White,
              radius = 5.dp.toPx(),
              center = ptOffset
            )
          }
        }

        // 4. Render completed strokes
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

        // 5. Render currently active finger stroke
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

        // 6. Draw playful sparkles around finger
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
          Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = KidsSecondary,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "تتبع الحرف بإصبعك",
            fontSize = 13.sp,
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
          hasCompleted = true
          onCompleted()
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
            else -> "أنهيت كتابة الحرف! 🌟"
          },
          fontSize = 18.sp,
          fontWeight = FontWeight.ExtraBold,
          color = Color.White
        )
      }
    }
  }
}
