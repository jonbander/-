package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.KidsAccentYellow
import com.example.ui.theme.KidsPrimary
import com.example.ui.theme.KidsSecondary

@Composable
fun CelebrationDialog(
  title: String = "أحسنت يا بطل! 🌟",
  message: String = "لقد كتبت الحرف بشكل رائع ومميز!",
  earnedStars: Int = 1,
  onDismiss: () -> Unit,
  onContinue: () -> Unit
) {
  val starScale = remember { Animatable(0.3f) }
  val badgeRotate = remember { Animatable(-10f) }

  LaunchedEffect(Unit) {
    starScale.animateTo(
      targetValue = 1.15f,
      animationSpec = tween(400, easing = FastOutSlowInEasing)
    )
    starScale.animateTo(
      targetValue = 1.0f,
      animationSpec = tween(150)
    )
  }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
  ) {
    Surface(
      shape = RoundedCornerShape(32.dp),
      color = Color.White,
      shadowElevation = 16.dp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .border(4.dp, KidsAccentYellow, RoundedCornerShape(32.dp))
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Cheerful mascot avatar / big star badge
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(100.dp)
            .scale(starScale.value)
            .shadow(10.dp, CircleShape)
            .background(
              Brush.radialGradient(
                colors = listOf(Color(0xFFFFE082), KidsAccentYellow, Color(0xFFFFA000))
              ),
              CircleShape
            )
            .border(3.dp, Color.White, CircleShape)
        ) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(62.dp)
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = title,
          fontSize = 24.sp,
          fontWeight = FontWeight.Black,
          color = KidsSecondary,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = message,
          fontSize = 15.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          textAlign = TextAlign.Center,
          lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Stars reward pill
        Surface(
          color = Color(0xFFFFF9C4),
          shape = RoundedCornerShape(20.dp),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, KidsAccentYellow)
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = null,
              tint = Color(0xFFFFA000),
              modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "+$earnedStars نجمة ذهبية أضيفت لرصيدك!",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFFE65100)
            )
          }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedButton(
            onClick = onDismiss,
            modifier = Modifier
              .weight(1f)
              .height(50.dp)
              .testTag("celebration_repeat_button"),
            shape = RoundedCornerShape(16.dp)
          ) {
            Text(
              text = "إعادة الرسم",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
          }

          Button(
            onClick = onContinue,
            modifier = Modifier
              .weight(1f)
              .height(50.dp)
              .testTag("celebration_continue_button"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = KidsPrimary)
          ) {
            Text(
              text = "تابع التعلّم 🌟",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = Color.White
            )
          }
        }
      }
    }
  }
}
