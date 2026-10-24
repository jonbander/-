package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ArabicLetter
import com.example.data.repository.LetterRepository
import com.example.ui.theme.KidsAccentGreen
import com.example.ui.theme.KidsAccentYellow
import com.example.ui.theme.KidsPrimary
import com.example.ui.theme.KidsSecondary
import com.example.ui.theme.KidsTertiary

@Composable
fun HomeScreen(
  letters: List<ArabicLetter>,
  childName: String,
  resumeActivityName: String,
  onResumeLearning: () -> Unit,
  completedLetters: Set<String>,
  totalStars: Int,
  onSelectLetter: (ArabicLetter) -> Unit,
  onQuickStartTracing: () -> Unit,
  onQuickStartVowels: () -> Unit,
  onQuickStartQuiz: () -> Unit,
  onPlaySound: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedFilter by remember { mutableStateOf("all") }

  val filteredLetters = remember(selectedFilter, completedLetters) {
    when (selectedFilter) {
      "completed" -> letters.filter { completedLetters.contains(it.char) }
      "easy" -> letters.take(8)
      else -> letters
    }
  }

  LazyVerticalGrid(
    columns = GridCells.Adaptive(minSize = 100.dp),
    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    modifier = modifier.fillMaxSize()
  ) {
    // 1. Hero Banner
    item(span = { GridItemSpan(maxLineSpan) }) {
      Card(
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp)
      ) {
        Box(modifier = Modifier.fillMaxSize()) {
          Image(
            painter = painterResource(id = R.drawable.banner_arabic_kids_1792832528029),
            contentDescription = "تعلّموا العربية",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
          )
          // Gentle gradient scrim at bottom
          Box(
            modifier = Modifier
              .fillMaxSize()
              .background(
                Brush.verticalGradient(
                  colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.55f))
                )
              )
          )

          // Mascot greetings pill
          Row(
            modifier = Modifier
              .align(Alignment.BottomStart)
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(2.dp, KidsAccentYellow, CircleShape)
            ) {
              Image(
                painter = painterResource(id = R.drawable.ic_kids_arabic_letters_1792832477145),
                contentDescription = "الجمل ظريف",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "مرحباً يا $childName! 🌟",
                fontSize = 17.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
              )
              Text(
                text = "هيا نكتب وننطق حروف لغتنا الجميلة",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.9f)
              )
            }
          }
        }
      }
    }

    // 2. Stars & Progress Bar
    item(span = { GridItemSpan(maxLineSpan) }) {
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .clickable(onClick = onResumeLearning)
          .testTag("resume_learning"),
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFEAF4FF),
        border = androidx.compose.foundation.BorderStroke(1.dp, KidsPrimary.copy(alpha = 0.25f))
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text("تابع التعلّم يا $childName", fontWeight = FontWeight.Black, color = KidsPrimary)
            Text("آخر نشاط: $resumeActivityName", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
          Icon(Icons.Default.PlayArrow, contentDescription = "متابعة التعلم", tint = KidsPrimary)
        }
      }
    }

    // 3. Stars & Progress Bar
    item(span = { GridItemSpan(maxLineSpan) }) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Stars pill
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = Color(0xFFFFF8E1),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, KidsAccentYellow),
          modifier = Modifier
            .weight(1f)
            .shadow(2.dp, RoundedCornerShape(20.dp))
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = null,
              tint = Color(0xFFFFA000),
              modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Column {
              Text(
                text = "$totalStars",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFFE65100)
              )
              Text(
                text = "نجوم ذهبية",
                fontSize = 11.sp,
                color = Color(0xFF8D6E63)
              )
            }
          }
        }

        // Learned Letters pill
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = Color(0xFFE8F5E9),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, KidsAccentGreen),
          modifier = Modifier
            .weight(1f)
            .shadow(2.dp, RoundedCornerShape(20.dp))
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = null,
              tint = KidsAccentGreen,
              modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Column {
              Text(
                text = "${completedLetters.size} / 28",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF2E7D32)
              )
              Text(
                text = "حرفاً متقناً",
                fontSize = 11.sp,
                color = Color(0xFF388E3C)
              )
            }
          }
        }
      }
    }

    // 3. Quick Action Activity Launchers
    item(span = { GridItemSpan(maxLineSpan) }) {
      Column(modifier = Modifier.fillMaxWidth()) {
        Text(
          text = "أنشطة ومغامرات اليوم 🚀",
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface,
          modifier = Modifier.padding(vertical = 4.dp)
        )
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          QuickActivityCard(
            title = "كتابة الحرف",
            subtitle = "أول ووسط وآخر",
            emoji = "✍️",
            bgBrush = Brush.linearGradient(listOf(KidsPrimary, Color(0xFF0077B6))),
            onClick = onQuickStartTracing,
            modifier = Modifier.weight(1f)
          )
          QuickActivityCard(
            title = "عالم المدود",
            subtitle = "قصير وطويل",
            emoji = "🎶",
            bgBrush = Brush.linearGradient(listOf(KidsSecondary, Color(0xFFE85D04))),
            onClick = onQuickStartVowels,
            modifier = Modifier.weight(1f)
          )
          QuickActivityCard(
            title = "لعبة التحدي",
            subtitle = "ألعاب ومرح",
            emoji = "🎯",
            bgBrush = Brush.linearGradient(listOf(KidsTertiary, Color(0xFFC2185B))),
            onClick = onQuickStartQuiz,
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    // 4. Section Title & Filters
    item(span = { GridItemSpan(maxLineSpan) }) {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "قطار الحروف العربية 🚂",
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "المس الحرف لتعلّمه",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          FilterChip(
            selected = selectedFilter == "all",
            onClick = { selectedFilter = "all" },
            label = { Text("الكل (28)") },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = KidsPrimary,
              selectedLabelColor = Color.White
            )
          )
          FilterChip(
            selected = selectedFilter == "easy",
            onClick = { selectedFilter = "easy" },
            label = { Text("الأولى (8)") },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = KidsSecondary,
              selectedLabelColor = Color.White
            )
          )
          FilterChip(
            selected = selectedFilter == "completed",
            onClick = { selectedFilter = "completed" },
            label = { Text("المكتملة ⭐ (${completedLetters.size})") },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = KidsAccentGreen,
              selectedLabelColor = Color.White
            )
          )
        }
      }
    }

    // 5. Grid of 28 Arabic Letters
    items(filteredLetters, key = { it.id }) { letter ->
      val isDone = completedLetters.contains(letter.char)
      LetterCardItem(
        letter = letter,
        isCompleted = isDone,
        onClick = {
          onPlaySound("حرف ${letter.phoneticName}")
          onSelectLetter(letter)
        },
        onPlayAudio = {
          onPlaySound("حرف ${letter.phoneticName}.. ${letter.char}")
        }
      )
    }
  }
}

@Composable
private fun QuickActivityCard(
  title: String,
  subtitle: String,
  emoji: String,
  bgBrush: Brush,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .height(86.dp)
      .clip(RoundedCornerShape(20.dp))
      .background(bgBrush)
      .clickable { onClick() }
      .padding(10.dp)
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Text(text = emoji, fontSize = 24.sp)
      Column {
        Text(
          text = title,
          fontSize = 13.sp,
          fontWeight = FontWeight.Black,
          color = Color.White
        )
        Text(
          text = subtitle,
          fontSize = 10.sp,
          color = Color.White.copy(alpha = 0.85f)
        )
      }
    }
  }
}

@Composable
private fun LetterCardItem(
  letter: ArabicLetter,
  isCompleted: Boolean,
  onClick: () -> Unit,
  onPlayAudio: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .aspectRatio(0.92f)
      .testTag("letter_card_${letter.char}")
      .clickable { onClick() },
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .border(
          width = 2.dp,
          color = Color(letter.colorHex).copy(alpha = 0.35f),
          shape = RoundedCornerShape(22.dp)
        )
        .padding(8.dp)
    ) {
      // Completed Star Badge
      if (isCompleted) {
        Box(
          modifier = Modifier
            .align(Alignment.TopStart)
            .size(22.dp)
            .background(KidsAccentYellow, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "مكتمل",
            tint = Color.White,
            modifier = Modifier.size(15.dp)
          )
        }
      }

      // Audio speaker button
      IconButton(
        onClick = onPlayAudio,
        modifier = Modifier
          .align(Alignment.TopEnd)
          .size(28.dp)
      ) {
        Icon(
          imageVector = Icons.Default.VolumeUp,
          contentDescription = "استمع للحرف",
          tint = Color(letter.colorHex),
          modifier = Modifier.size(18.dp)
        )
      }

      // Main Letter & details centered
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Text(
          text = letter.char,
          fontSize = 42.sp,
          fontWeight = FontWeight.Black,
          color = Color(letter.colorHex),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
          text = letter.name,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
          text = letter.emoji,
          fontSize = 16.sp
        )
      }
    }
  }
}
