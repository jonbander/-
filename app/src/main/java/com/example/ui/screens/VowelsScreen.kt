package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.data.model.ArabicLetter
import com.example.data.model.VowelDetail
import com.example.data.model.VowelType
import com.example.ui.components.VowelComparisonCard
import com.example.ui.theme.KidsAccentGreen
import com.example.ui.theme.KidsAccentYellow
import com.example.ui.theme.KidsPrimary
import com.example.ui.theme.KidsSecondary
import com.example.ui.theme.KidsTertiary
import kotlinx.coroutines.launch

@Composable
fun VowelsScreen(
  currentLetter: ArabicLetter,
  allLetters: List<ArabicLetter>,
  onSelectLetter: (ArabicLetter) -> Unit,
  onPlaySound: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  val letterColor = Color(currentLetter.colorHex)

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(bottom = 90.dp)
  ) {
    // 1. Letters Ribbon at top
    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      items(allLetters, key = { it.id }) { letter ->
        val isSelected = letter.id == currentLetter.id
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(44.dp)
            .shadow(if (isSelected) 4.dp else 1.dp, RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(14.dp))
            .background(if (isSelected) Color(letter.colorHex) else Color.White)
            .border(
              width = if (isSelected) 2.dp else 1.dp,
              color = if (isSelected) Color.White else Color(letter.colorHex).copy(alpha = 0.3f),
              shape = RoundedCornerShape(14.dp)
            )
            .clickable {
              onSelectLetter(letter)
              onPlaySound("حرف ${letter.phoneticName}")
            }
        ) {
          Text(
            text = letter.char,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            color = if (isSelected) Color.White else Color(letter.colorHex)
          )
        }
      }
    }

    // 2. Active Letter Overview Banner
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 6.dp),
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(52.dp)
              .clip(RoundedCornerShape(16.dp))
              .background(
                Brush.radialGradient(
                  listOf(letterColor.copy(alpha = 0.25f), letterColor.copy(alpha = 0.08f))
                )
              )
              .border(2.dp, letterColor, RoundedCornerShape(16.dp))
          ) {
            Text(
              text = currentLetter.char,
              fontSize = 30.sp,
              fontWeight = FontWeight.Black,
              color = letterColor
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Text(
              text = "مدود حرف ${currentLetter.name}",
              fontSize = 18.sp,
              fontWeight = FontWeight.Black,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "استمع للفرق بين الصوت القصير والصوت الطويل 🎶",
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        IconButton(
          onClick = {
            onPlaySound("مدود حرف ${currentLetter.phoneticName}")
          },
          modifier = Modifier
            .size(42.dp)
            .background(Color(0xFFE0F7FA), CircleShape)
        ) {
          Icon(
            imageVector = Icons.Default.Hearing,
            contentDescription = "استمع",
            tint = KidsPrimary
          )
        }
      }
    }

    // 3. Tab Selector: مقارنة المدود | المد القصير | المد الطويل
    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = Color(0xFFF1F5F9),
      modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 6.dp)
        .clip(RoundedCornerShape(18.dp))
    ) {
      Tab(
        selected = selectedTab == 0,
        onClick = { selectedTab = 0 },
        text = {
          Text(
            text = "مقارنة المدود ⚖️",
            fontWeight = if (selectedTab == 0) FontWeight.Black else FontWeight.Normal,
            fontSize = 13.sp
          )
        }
      )
      Tab(
        selected = selectedTab == 1,
        onClick = { selectedTab = 1 },
        text = {
          Text(
            text = "المد القصير ⏱️",
            fontWeight = if (selectedTab == 1) FontWeight.Black else FontWeight.Normal,
            fontSize = 13.sp
          )
        }
      )
      Tab(
        selected = selectedTab == 2,
        onClick = { selectedTab = 2 },
        text = {
          Text(
            text = "المد الطويل 🎶",
            fontWeight = if (selectedTab == 2) FontWeight.Black else FontWeight.Normal,
            fontSize = 13.sp
          )
        }
      )
    }

    // 4. Content List
    LazyColumn(
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.fillMaxSize()
    ) {
      when (selectedTab) {
        0 -> {
          // Comparisons mode
          item {
            Surface(
              color = Color(0xFFFFF9C4),
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(text = "👂", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                  text = "المس الحرف القصير ثم الحرف الطويل ولاحظ كيف يمتد الصوت مع حروف المد (الألف، الواو، الياء)!",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF795548),
                  lineHeight = 18.sp
                )
              }
            }
          }

          items(currentLetter.comparisons) { comp ->
            VowelComparisonCard(
              comparison = comp,
              onPlayShort = { v ->
                onPlaySound("${v.title}.. صوت قصير.. ${v.vowelGlyph}.. ${v.exampleWord}")
              },
              onPlayLong = { v ->
                onPlaySound("${v.title}.. صوت طويل ممدود.. ${v.vowelGlyph}.. ${v.exampleWord}")
              }
            )
          }
        }

        1 -> {
          // Short Vowels List (الفتحة، الضمة، الكسرة، السكون)
          item {
            Surface(
              color = Color(0xFFE0F7FA),
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(text = "⏱️", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                  text = "المد القصير هو الحركات: الفتحة (ـَ) والضمة (ـُ) والكسرة (ـِ) والسكون (ـْ). صوتها سريع وخاطف!",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF006064),
                  lineHeight = 18.sp
                )
              }
            }
          }

          items(currentLetter.shortVowels) { v ->
            VowelCardDetailed(
              vowel = v,
              accentColor = KidsPrimary,
              onPlay = {
                onPlaySound("${v.title}.. ${v.vowelGlyph}.. ${v.exampleWord}")
              }
            )
          }
        }

        2 -> {
          // Long Vowels List (مد بالألف، مد بالواو، مد بالياء)
          item {
            Surface(
              color = Color(0xFFFFF3E0),
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(text = "🎶", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                  text = "المد الطويل هو مد الصوت بحروف المد الثلاثة: الألف (ا) والواو (و) والياء (ي). صوتها ممدود وموسيقي!",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFFE65100),
                  lineHeight = 18.sp
                )
              }
            }
          }

          items(currentLetter.longVowels) { v ->
            VowelCardDetailed(
              vowel = v,
              accentColor = KidsSecondary,
              onPlay = {
                onPlaySound("${v.title}.. ${v.vowelGlyph}.. ${v.exampleWord}")
              }
            )
          }
        }
      }
    }
  }
}

@Composable
private fun VowelCardDetailed(
  vowel: VowelDetail,
  accentColor: Color,
  onPlay: () -> Unit
) {
  val scaleAnim = remember { Animatable(1f) }
  val scope = rememberCoroutineScope()

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .scale(scaleAnim.value)
      .clickable {
        scope.launch {
          scaleAnim.animateTo(1.04f, tween(100))
          scaleAnim.animateTo(1f, tween(100))
        }
        onPlay()
      },
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.5.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        // Big badge for vowel glyph
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(62.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(accentColor.copy(alpha = 0.12f))
            .border(2.dp, accentColor, RoundedCornerShape(18.dp))
        ) {
          Text(
            text = vowel.vowelGlyph,
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            color = accentColor
          )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {
          Text(
            text = vowel.title,
            fontSize = 17.sp,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.height(2.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = vowel.exampleEmoji, fontSize = 18.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = vowel.exampleWord,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = accentColor
            )
          }
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = vowel.explanation,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      // Speaker icon button
      IconButton(
        onClick = onPlay,
        modifier = Modifier
          .size(44.dp)
          .background(accentColor.copy(alpha = 0.12f), CircleShape)
      ) {
        Icon(
          imageVector = Icons.Default.VolumeUp,
          contentDescription = "استمع",
          tint = accentColor,
          modifier = Modifier.size(22.dp)
        )
      }
    }
  }
}
