package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.StickerItem
import com.example.data.repository.LetterRepository
import com.example.ui.theme.KidsAccentGreen
import com.example.ui.theme.KidsAccentYellow
import com.example.ui.theme.KidsPrimary
import com.example.ui.theme.KidsSecondary
import com.example.ui.theme.KidsTertiary
import kotlinx.coroutines.launch

@Composable
fun StickersScreen(
  totalStars: Int,
  onPlaySound: (String) -> Unit,
  onPlayChime: () -> Unit,
  modifier: Modifier = Modifier
) {
  val stickers = remember { LetterRepository.stickers }
  val unlockedCount = stickers.count { totalStars >= it.starsRequired }

  LazyVerticalGrid(
    columns = GridCells.Adaptive(minSize = 135.dp),
    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    modifier = modifier.fillMaxSize()
  ) {
    // Top Trophy Header
    item(span = { GridItemSpan(maxLineSpan) }) {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
          containerColor = Color(0xFFFFF8E1)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(56.dp)
                .background(
                  Brush.radialGradient(listOf(KidsAccentYellow, Color(0xFFFFA000))),
                  CircleShape
                ),
              contentAlignment = Alignment.Center
            ) {
              Text(text = "🎨", fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
              Text(
                text = "دفتر ملصقات الأبطال 🏆",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFFE65100)
              )
              Text(
                text = "جمعت $unlockedCount من أصل ${stickers.size} ملصقاً رائعاً!",
                fontSize = 12.sp,
                color = Color(0xFF8D6E63)
              )
            }
          }

          // Stars pill
          Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, KidsAccentYellow)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFFFA000),
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "$totalStars",
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFFE65100)
              )
            }
          }
        }
      }
    }

    item(span = { GridItemSpan(maxLineSpan) }) {
      Text(
        text = "اكتب الحروف واجمع النجوم لفتح ملصقات جديدة! ✨",
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(vertical = 4.dp)
      )
    }

    // Grid of Sticker Items
    items(stickers, key = { it.id }) { item ->
      val isUnlocked = totalStars >= item.starsRequired
      StickerCard(
        item = item,
        isUnlocked = isUnlocked,
        onClick = {
          if (isUnlocked) {
            onPlayChime()
            onPlaySound("ملصق ${item.name} لحرف ${item.letter}!")
          } else {
            val needed = item.starsRequired - totalStars
            onPlaySound("هذا الملصق مقفل، تحتاج إلى $needed نجوم إضافية لفتحه!")
          }
        }
      )
    }
  }
}

@Composable
private fun StickerCard(
  item: StickerItem,
  isUnlocked: Boolean,
  onClick: () -> Unit
) {
  val scaleAnim = remember { Animatable(1f) }
  val scope = rememberCoroutineScope()

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .aspectRatio(0.92f)
      .scale(scaleAnim.value)
      .clickable {
        scope.launch {
          scaleAnim.animateTo(1.08f, tween(100))
          scaleAnim.animateTo(1f, tween(120))
        }
        onClick()
      }
      .testTag("sticker_${item.id}"),
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isUnlocked) Color.White else Color(0xFFF1F5F9)
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = if (isUnlocked) 4.dp else 1.dp)
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .border(
          width = 2.dp,
          color = if (isUnlocked) KidsAccentYellow else Color(0xFFE2E8F0),
          shape = RoundedCornerShape(22.dp)
        )
        .padding(10.dp)
    ) {
      if (isUnlocked) {
        // Star badge
        Surface(
          modifier = Modifier.align(Alignment.TopStart),
          shape = CircleShape,
          color = KidsAccentGreen
        ) {
          Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
              .padding(4.dp)
              .size(12.dp)
          )
        }
      } else {
        // Lock badge
        Surface(
          modifier = Modifier.align(Alignment.TopStart),
          shape = CircleShape,
          color = Color(0xFF94A3B8)
        ) {
          Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "مقفل",
            tint = Color.White,
            modifier = Modifier
              .padding(4.dp)
              .size(12.dp)
          )
        }
      }

      // Letter indicator top-end
      Surface(
        modifier = Modifier.align(Alignment.TopEnd),
        shape = RoundedCornerShape(8.dp),
        color = if (isUnlocked) KidsPrimary.copy(alpha = 0.15f) else Color(0xFFE2E8F0)
      ) {
        Text(
          text = item.letter,
          fontSize = 12.sp,
          fontWeight = FontWeight.Black,
          color = if (isUnlocked) KidsPrimary else Color(0xFF64748B),
          modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
      }

      // Content centered
      Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        if (isUnlocked) {
          Text(
            text = item.emoji,
            fontSize = 42.sp
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = item.name,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp
          )
        } else {
          Text(
            text = "🔒",
            fontSize = 32.sp
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = item.name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF94A3B8),
            textAlign = TextAlign.Center
          )
          Spacer(modifier = Modifier.height(4.dp))
          Surface(
            color = Color(0xFFFFF9C4),
            shape = RoundedCornerShape(8.dp)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFFFA000),
                modifier = Modifier.size(12.dp)
              )
              Spacer(modifier = Modifier.width(3.dp))
              Text(
                text = "${item.starsRequired} نجوم",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE65100)
              )
            }
          }
        }
      }
    }
  }
}
