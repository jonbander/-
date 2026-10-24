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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.ui.components.CelebrationDialog
import com.example.ui.theme.KidsAccentGreen
import com.example.ui.theme.KidsAccentYellow
import com.example.ui.theme.KidsPrimary
import com.example.ui.theme.KidsSecondary
import com.example.ui.theme.KidsTertiary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class QuizQuestion(
  val prompt: String,
  val voicePrompt: String,
  val targetLetter: String,
  val options: List<QuizOption>,
  val explanation: String
)

data class QuizOption(
  val text: String,
  val emoji: String = "",
  val isCorrect: Boolean
)

@Composable
fun QuizGameScreen(
  letters: List<ArabicLetter>,
  onAddStars: (Int) -> Unit,
  onPlaySound: (String) -> Unit,
  onPlaySuccessChime: () -> Unit,
  modifier: Modifier = Modifier
) {
  var currentQuestionIndex by remember { mutableIntStateOf(0) }
  var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
  var isAnswerSubmitted by remember { mutableStateOf(false) }
  var showCelebration by remember { mutableStateOf(false) }
  var correctCount by remember { mutableIntStateOf(0) }

  val questions = remember {
    listOf(
      QuizQuestion(
        prompt = "أين شكل حرف (ب) في أول الكلمة؟",
        voicePrompt = "أين شكل حرف الباء في أول الكلمة؟",
        targetLetter = "ب",
        options = listOf(
          QuizOption("بـ", "🦆", true),
          QuizOption("ـبـ", "🪢", false),
          QuizOption("ـب", "🍇", false)
        ),
        explanation = "حرف الباء في أول الكلمة يمد يده لليسار (بـ) مثل كلمة بَـطَّة!"
      ),
      QuizQuestion(
        prompt = "أي من هذه الخيارات مد طويل؟",
        voicePrompt = "أي من هذه الخيارات يعتبر مداً طويلاً؟",
        targetLetter = "بَا",
        options = listOf(
          QuizOption("بَا", "🚪", true),
          QuizOption("بَ", "🐮", false),
          QuizOption("بْ", "🍞", false)
        ),
        explanation = "(بَا) مد طويل بالألف ويمتد فيه الصوت، بينما (بَ) حركة قصيرة!"
      ),
      QuizQuestion(
        prompt = "أين شكل حرف (ع) في وسط الكلمة؟",
        voicePrompt = "أين شكل حرف العين في وسط الكلمة؟",
        targetLetter = "ع",
        options = listOf(
          QuizOption("عـ", "👁️", false),
          QuizOption("ـعـ", "🦊", true),
          QuizOption("ـع", "🛣️", false)
        ),
        explanation = "في وسط الكلمة يمد يديه الاثنتين (ـعـ) مثل كلمة ثَـعْـلَب!"
      ),
      QuizQuestion(
        prompt = "أي كلمة تبدأ بحرف (أ)؟",
        voicePrompt = "أي كلمة تبدأ بحرف الألف؟",
        targetLetter = "أ",
        options = listOf(
          QuizOption("أَسَد", "🦁", true),
          QuizOption("بَطَّة", "🦆", false),
          QuizOption("جَمَل", "🐪", false)
        ),
        explanation = "أَسَد يبدأ بحرف الألف المفتوح (أَ)! أحسنت!"
      ),
      QuizQuestion(
        prompt = "أين المد بالواو الطويل؟",
        voicePrompt = "أين المد بالواو الطويل؟",
        targetLetter = "ـُو",
        options = listOf(
          QuizOption("بُومَة (بُو)", "🦉", true),
          QuizOption("بُرْتُقَال (بُ)", "🍊", false),
          QuizOption("بِنْت (بِ)", "👧", false)
        ),
        explanation = "بُومَة فيها مد طويل بالواو (بُو)! صوت ممدود وجميل!"
      ),
      QuizQuestion(
        prompt = "أين شكل حرف (م) في آخر الكلمة؟",
        voicePrompt = "أين شكل حرف الميم في آخر الكلمة؟",
        targetLetter = "م",
        options = listOf(
          QuizOption("مـ", "🍌", false),
          QuizOption("ـمـ", "☀️", false),
          QuizOption("ـم", "✏️", true)
        ),
        explanation = "في آخر الكلمة تتصل الميم وتنزل عصاها للأسفل (ـم) مثل قَلَم!"
      ),
      QuizQuestion(
        prompt = "أي كلمة تحتوي على مد بالياء؟",
        voicePrompt = "أي كلمة تحتوي على مد بالياء الطويل؟",
        targetLetter = "ـِي",
        options = listOf(
          QuizOption("طَبِيب (بِي)", "🩺", true),
          QuizOption("تَمْر (تَ)", "🌴", false),
          QuizOption("خُبْز (خُ)", "🍞", false)
        ),
        explanation = "طَبِيب تحتوي على مد طويل بالياء (بِي)! رائع جداً!"
      )
    )
  }

  val currentQ = questions[currentQuestionIndex % questions.size]

  // Speak question on launch or change
  LaunchedEffect(currentQuestionIndex) {
    onPlaySound(currentQ.voicePrompt)
  }

  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .padding(bottom = 90.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Top Score & Progress Card
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(22.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.EmojiEvents,
            contentDescription = null,
            tint = KidsAccentYellow,
            modifier = Modifier.size(24.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "تحدي الأبطال 🏆",
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onSurface
          )
        }

        Surface(
          color = Color(0xFFFFF9C4),
          shape = RoundedCornerShape(12.dp)
        ) {
          Text(
            text = "السؤال ${(currentQuestionIndex % questions.size) + 1} من ${questions.size}",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE65100),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Question Box with speaker
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(28.dp),
      colors = CardDefaults.cardColors(
        containerColor = Color(0xFFE0F7FA)
      ),
      elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            color = KidsPrimary,
            shape = RoundedCornerShape(10.dp)
          ) {
            Text(
              text = "اختر الإجابة الصحيحة",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
          }

          IconButton(
            onClick = { onPlaySound(currentQ.voicePrompt) },
            modifier = Modifier
              .size(40.dp)
              .background(Color.White, CircleShape)
          ) {
            Icon(
              imageVector = Icons.Default.VolumeUp,
              contentDescription = "استمع للسؤال",
              tint = KidsPrimary
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = currentQ.prompt,
          fontSize = 24.sp,
          fontWeight = FontWeight.Black,
          color = Color(0xFF0077B6),
          textAlign = TextAlign.Center,
          lineHeight = 32.sp
        )
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Answer Options (Big chunky buttons for 5-year-olds)
    Column(
      modifier = Modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      currentQ.options.forEachIndexed { index, option ->
        val isSelected = selectedOptionIndex == index
        val isCorrect = option.isCorrect

        val btnColor = when {
          !isAnswerSubmitted -> Color.White
          isSelected && isCorrect -> Color(0xFFE8F5E9)
          isSelected && !isCorrect -> Color(0xFFFFEBEE)
          isCorrect -> Color(0xFFE8F5E9)
          else -> Color.White
        }

        val borderColor = when {
          !isAnswerSubmitted && isSelected -> KidsPrimary
          !isAnswerSubmitted -> Color(0xFFCBD5E1)
          isCorrect -> KidsAccentGreen
          isSelected && !isCorrect -> Color(0xFFFF5252)
          else -> Color(0xFFE2E8F0)
        }

        Surface(
          modifier = Modifier
            .fillMaxWidth()
            .shadow(if (isSelected) 4.dp else 1.dp, RoundedCornerShape(22.dp))
            .clip(RoundedCornerShape(22.dp))
            .clickable(enabled = !isAnswerSubmitted) {
              selectedOptionIndex = index
              isAnswerSubmitted = true
              if (option.isCorrect) {
                correctCount++
                onAddStars(1)
                onPlaySuccessChime()
                onPlaySound("أحسنت يا بطل! إجابة صحيحة! ${currentQ.explanation}")
                showCelebration = true
              } else {
                onPlaySound("حاول مرة أخرى! الإجابة الصحيحة هي: ${currentQ.explanation}")
              }
            }
            .border(2.5.dp, borderColor, RoundedCornerShape(22.dp)),
          color = btnColor,
          shape = RoundedCornerShape(22.dp)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = option.emoji,
                fontSize = 28.sp
              )
              Spacer(modifier = Modifier.width(14.dp))
              Text(
                text = option.text,
                fontSize = 26.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface
              )
            }

            if (isAnswerSubmitted) {
              if (isCorrect) {
                Box(
                  modifier = Modifier
                    .size(34.dp)
                    .background(KidsAccentGreen, CircleShape),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                  )
                }
              } else if (isSelected) {
                Box(
                  modifier = Modifier
                    .size(34.dp)
                    .background(Color(0xFFFF5252), CircleShape),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                  )
                }
              }
            }
          }
        }
      }
    }

    // Explanation Card when answered
    if (isAnswerSubmitted) {
      Spacer(modifier = Modifier.height(16.dp))
      Surface(
        color = Color(0xFFFFF9C4),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = "💡 معلومة الحرف:",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE65100)
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = currentQ.explanation,
            fontSize = 14.sp,
            color = Color(0xFF4E342E),
            lineHeight = 20.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Button(
        onClick = {
          isAnswerSubmitted = false
          selectedOptionIndex = null
          currentQuestionIndex = (currentQuestionIndex + 1) % questions.size
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(54.dp)
          .testTag("next_question_button"),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(containerColor = KidsSecondary)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "السؤال التالي ➡️",
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            color = Color.White
          )
        }
      }
    }
  }

  // Celebration dialog
  if (showCelebration) {
    CelebrationDialog(
      title = "إجابة عبقرية! 🎯",
      message = "أنت ذكي جداً يا بطل! أتقنت معرفة الحرف وموقعه بنجاح!",
      earnedStars = 1,
      onDismiss = { showCelebration = false },
      onContinue = {
        showCelebration = false
        isAnswerSubmitted = false
        selectedOptionIndex = null
        currentQuestionIndex = (currentQuestionIndex + 1) % questions.size
      }
    )
  }
}
