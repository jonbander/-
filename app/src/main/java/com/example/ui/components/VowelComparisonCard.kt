package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VowelComparison
import com.example.data.model.VowelDetail
import com.example.ui.theme.KidsAccentGreen
import com.example.ui.theme.KidsPrimary
import com.example.ui.theme.KidsSecondary
import com.example.ui.theme.KidsTertiary

@Composable
fun VowelComparisonCard(
  comparison: VowelComparison,
  onPlayShort: (VowelDetail) -> Unit,
  onPlayLong: (VowelDetail) -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp),
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      // Comparison Title Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(10.dp)
              .background(KidsSecondary, CircleShape)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = comparison.title,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        }
        Icon(
          imageVector = Icons.Default.GraphicEq,
          contentDescription = null,
          tint = KidsPrimary,
          modifier = Modifier.size(20.dp)
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Side-by-side cards: Short vs Long
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Short Vowel Box
        VowelItemBox(
          vowel = comparison.shortVowel,
          headerLabel = "مد قصير (حركة) ⏱️",
          headerColor = Color(0xFF00B4D8),
          bgBrush = Brush.verticalGradient(
            listOf(Color(0xFFE0F7FA), Color(0xFFB2EBF2))
          ),
          onPlay = { onPlayShort(comparison.shortVowel) },
          modifier = Modifier.weight(1f)
        )

        // Long Vowel Box
        VowelItemBox(
          vowel = comparison.longVowel,
          headerLabel = "مد طويل (حرف مد) 🎶",
          headerColor = Color(0xFFFF9F1C),
          bgBrush = Brush.verticalGradient(
            listOf(Color(0xFFFFF3E0), Color(0xFFFFE0B2))
          ),
          onPlay = { onPlayLong(comparison.longVowel) },
          modifier = Modifier.weight(1f)
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Kid-friendly teaching hint
      Surface(
        color = Color(0xFFF1F5F9),
        shape = RoundedCornerShape(14.dp)
      ) {
        Text(
          text = "💡 سر النطق: ${comparison.contrastHint}",
          fontSize = 13.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
          lineHeight = 18.sp
        )
      }
    }
  }
}

@Composable
private fun VowelItemBox(
  vowel: VowelDetail,
  headerLabel: String,
  headerColor: Color,
  bgBrush: Brush,
  onPlay: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(20.dp))
      .background(bgBrush)
      .border(2.dp, headerColor.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
      .clickable { onPlay() }
      .padding(12.dp)
  ) {
    Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Small label pill
      Surface(
        color = headerColor,
        shape = RoundedCornerShape(8.dp)
      ) {
        Text(
          text = headerLabel,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = Color.White,
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Huge Arabic glyph with haraka
      Text(
        text = vowel.vowelGlyph,
        fontSize = 44.sp,
        fontWeight = FontWeight.Black,
        color = Color(0xFF1E293B),
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(4.dp))

      // Word example + emoji
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Text(text = vowel.exampleEmoji, fontSize = 20.sp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = vowel.exampleWord,
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = headerColor
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Tap to listen button
      Row(
        modifier = Modifier
          .background(Color.White, RoundedCornerShape(12.dp))
          .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.VolumeUp,
          contentDescription = "استمع للصوت",
          tint = headerColor,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "المس واسمع",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = headerColor
        )
      }
    }
  }
}
