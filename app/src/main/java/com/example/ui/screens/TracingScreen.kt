package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ArabicLetter
import com.example.data.model.LetterPosition
import com.example.ui.components.CelebrationDialog
import com.example.ui.components.TracingCanvas
import com.example.ui.theme.KidsAccentGreen
import com.example.ui.theme.KidsAccentYellow
import com.example.ui.theme.KidsPrimary
import com.example.ui.theme.KidsSecondary
import com.example.ui.theme.KidsTertiary

@Composable
fun TracingScreen(
  currentLetter: ArabicLetter,
  allLetters: List<ArabicLetter>,
  onSelectLetter: (ArabicLetter) -> Unit,
  onFormCompleted: (ArabicLetter, LetterPosition) -> Unit,
  onPlaySound: (String) -> Unit,
  onPlaySuccessChime: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedPositionIndex by remember { mutableIntStateOf(0) }
  var showCelebration by remember { mutableStateOf(false) }

  val letterColor = Color(currentLetter.colorHex)
  val currentForm = currentLetter.forms.getOrNull(selectedPositionIndex) ?: currentLetter.forms.first()

  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(bottom = 90.dp)
  ) {
    // 1. Top Letters Quick Ribbon
    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      items(allLetters, key = { it.id }) { letter ->
        val isSelected = letter.id == currentLetter.id
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(46.dp)
            .shadow(if (isSelected) 4.dp else 1.dp, RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(14.dp))
            .background(if (isSelected) Color(letter.colorHex) else Color.White)
            .border(
              width = if (isSelected) 2.5.dp else 1.dp,
              color = if (isSelected) Color.White else Color(letter.colorHex).copy(alpha = 0.3f),
              shape = RoundedCornerShape(14.dp)
            )
            .clickable {
              onSelectLetter(letter)
              selectedPositionIndex = 0
              onPlaySound("حرف ${letter.phoneticName}")
            }
        ) {
          Text(
            text = letter.char,
            fontSize = 22.sp,
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
              .size(54.dp)
              .clip(RoundedCornerShape(18.dp))
              .background(letterColor.copy(alpha = 0.15f))
              .border(2.dp, letterColor, RoundedCornerShape(18.dp))
          ) {
            Text(
              text = currentLetter.char,
              fontSize = 32.sp,
              fontWeight = FontWeight.Black,
              color = letterColor
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Text(
              text = "حرف ${currentLetter.name}",
              fontSize = 19.sp,
              fontWeight = FontWeight.Black,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "تعلم كتابته بأشكاله الأربعة 🌟",
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        IconButton(
          onClick = {
            onPlaySound("حرف ${currentLetter.phoneticName}.. ${currentForm.position.arabicName}")
          },
          modifier = Modifier
            .size(44.dp)
            .background(letterColor.copy(alpha = 0.12f), CircleShape)
        ) {
          Icon(
            imageVector = Icons.Default.VolumeUp,
            contentDescription = "استمع للاسم",
            tint = letterColor
          )
        }
      }
    }

    // 3. 4-Positions Tab Row (منفصل, أول الكلمة, وسط الكلمة, آخر الكلمة)
    val positions = listOf(
      LetterPosition.ISOLATED,
      LetterPosition.INITIAL,
      LetterPosition.MEDIAL,
      LetterPosition.FINAL
    )

    Surface(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
      shape = RoundedCornerShape(20.dp),
      color = Color(0xFFF1F5F9)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        positions.forEachIndexed { index, pos ->
          val isSelected = selectedPositionIndex == index
          val matchingForm = currentLetter.forms.getOrNull(index)

          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .weight(1f)
              .height(54.dp)
              .clip(RoundedCornerShape(16.dp))
              .background(if (isSelected) letterColor else Color.Transparent)
              .clickable {
                selectedPositionIndex = index
                matchingForm?.let {
                  onPlaySound("${pos.arabicName}.. ${it.glyph}")
                }
              }
              .padding(4.dp)
          ) {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Text(
                text = matchingForm?.glyph ?: "",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = if (isSelected) Color.White else letterColor
              )
              Text(
                text = pos.arabicName,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      }
    }

    // 4. Word Context Card showing letter inside word
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 6.dp),
      shape = RoundedCornerShape(22.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          Text(
            text = currentForm.exampleEmoji,
            fontSize = 36.sp
          )
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = currentForm.exampleWord,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = letterColor
              )
              Spacer(modifier = Modifier.width(8.dp))
              Surface(
                color = letterColor.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text(
                  text = currentForm.position.arabicName,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = letterColor,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }
            Text(
              text = currentForm.exampleMeaning,
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        IconButton(
          onClick = {
            onPlaySound("${currentForm.glyph}.. في ${currentForm.position.arabicName}.. مثل كلمة ${currentForm.exampleWord}")
          },
          modifier = Modifier
            .size(40.dp)
            .background(Color(0xFFF1F5F9), CircleShape)
        ) {
          Icon(
            imageVector = Icons.Default.VolumeUp,
            contentDescription = "استمع للكلمة",
            tint = KidsPrimary,
            modifier = Modifier.size(20.dp)
          )
        }
      }
    }

    // 5. Instruction guide tip
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 4.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = Icons.Default.Info,
        contentDescription = null,
        tint = KidsSecondary,
        modifier = Modifier.size(18.dp)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = currentForm.guideDescription,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = KidsSecondary
      )
    }

    Spacer(modifier = Modifier.height(4.dp))

    // 6. Interactive Tracing Canvas
    TracingCanvas(
      glyph = currentForm.glyph,
      guidePoints = currentForm.guidePoints,
      primaryColor = letterColor,
      onCompleted = {
        onPlaySuccessChime()
        onPlaySound("أحسنت يا بطل! رسمك لحرف ${currentLetter.name} في ${currentForm.position.arabicName} ممتاز جداً!")
        onFormCompleted(currentLetter, currentForm.position)
        showCelebration = true
      }
    )
  }

  // Celebration Dialog
  if (showCelebration) {
    CelebrationDialog(
      title = "رائع ومبدع! 🌟",
      message = "أتقنت كتابة حرف (${currentLetter.char}) في ${currentForm.position.arabicName} بنجاح!",
      earnedStars = 1,
      onDismiss = { showCelebration = false },
      onContinue = {
        showCelebration = false
        // Move to next position or next letter
        if (selectedPositionIndex < 3) {
          selectedPositionIndex++
        } else {
          val currentIndex = allLetters.indexOfFirst { it.id == currentLetter.id }
          if (currentIndex != -1 && currentIndex + 1 < allLetters.size) {
            onSelectLetter(allLetters[currentIndex + 1])
            selectedPositionIndex = 0
          }
        }
      }
    )
  }
}
