package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.BuildConfig
import com.example.R
import com.example.data.model.ArabicLetter
import com.example.data.repository.LetterRepository
import com.example.data.repository.UserProgressRepository
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.FirstRunNameScreen
import com.example.ui.screens.QuizGameScreen
import com.example.ui.screens.StickersScreen
import com.example.ui.screens.TracingScreen
import com.example.ui.screens.VowelsScreen
import com.example.ui.theme.KidsAccentGreen
import com.example.ui.theme.KidsAccentYellow
import com.example.ui.theme.KidsPrimary
import com.example.ui.theme.KidsSecondary
import com.example.ui.theme.KidsTertiary
import com.example.util.SoundHelper

enum class AppTab(val title: String, val icon: ImageVector, val tag: String) {
  HOME("الرئيسية", Icons.Default.Home, "tab_home"),
  TRACING("كتابة الحرف", Icons.Default.Brush, "tab_tracing"),
  VOWELS("المدود", Icons.Default.MusicNote, "tab_vowels"),
  QUIZ("لعبة التحدي", Icons.Default.Casino, "tab_quiz"),
  STICKERS("الملصقات", Icons.Default.Collections, "tab_stickers")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
  progressRepository: UserProgressRepository,
  soundHelper: SoundHelper,
  modifier: Modifier = Modifier
) {
  // Force RTL layout direction for authentic Arabic experience
  CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
    val context = LocalContext.current
    val childName by progressRepository.childName.collectAsState()
    val totalStars by progressRepository.totalStars.collectAsState()
    val completedLetters by progressRepository.completedLetters.collectAsState()
    val tracedFormsCount by progressRepository.tracedFormsCount.collectAsState()
    val practicedVowelSections by progressRepository.practicedVowelSections.collectAsState()
    val lastVowelTab by progressRepository.lastVowelTab.collectAsState()
    val quizCurrentQuestion by progressRepository.quizCurrentQuestionIndex.collectAsState()
    val quizAnswered by progressRepository.quizQuestionAnswered.collectAsState()
    val quizSelectedOption by progressRepository.quizSelectedOptionIndex.collectAsState()
    val quizCorrectAnswers by progressRepository.quizCorrectAnswers.collectAsState()
    val quizAnsweredQuestions by progressRepository.quizAnsweredQuestions.collectAsState()
    val highestQuizQuestion by progressRepository.highestQuizQuestion.collectAsState()
    var importMessage by remember { mutableStateOf<String?>(null) }
    val importProgressLauncher = rememberLauncherForActivityResult(
      contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
      if (uri != null) {
        val imported = runCatching {
          context.contentResolver.openInputStream(uri)?.use {
            progressRepository.importLegacyProgress(it)
          } == true
        }.getOrDefault(false)
        importMessage = if (imported) {
          "تم استيراد النجوم وأشكال الحروف. أدخل اسم الطفل للمتابعة؛ سجل المدود والاختبار سيبدأ في النسخة الرسمية."
        } else {
          "تعذرت قراءة الملف. اختر ملف kids_arabic_progress.xml الذي استخرجته عبر ADB."
        }
      }
    }

    var currentTab by remember {
      mutableStateOf(
        progressRepository.lastTabName()?.let { saved ->
          AppTab.values().firstOrNull { it.name == saved }
        } ?: AppTab.HOME
      )
    }
    var selectedLetter by remember {
      mutableStateOf(
        LetterRepository.letters.firstOrNull { it.char == progressRepository.lastLetterChar() }
          ?: LetterRepository.letters[1]
      )
    }
    var showAboutScreen by remember { mutableStateOf(false) }
    var isMuted by remember { mutableStateOf(false) }
    val resumeTab = AppTab.values().firstOrNull {
      it.name == progressRepository.lastLearningTabName()
    } ?: AppTab.TRACING
    val resumeActivityName = when (resumeTab) {
      AppTab.TRACING -> "كتابة الحروف"
      AppTab.VOWELS -> "المدود والحركات"
      AppTab.QUIZ -> "لعبة التحدي"
      else -> "كتابة الحروف"
    }

    LaunchedEffect(currentTab) { progressRepository.saveLastTab(currentTab.name) }
    LaunchedEffect(selectedLetter.char) { progressRepository.saveLastLetter(selectedLetter.char) }

    fun playSound(text: String) {
      if (!isMuted) {
        soundHelper.speak(text)
      }
    }

    fun playChime() {
      if (!isMuted) {
        soundHelper.playStarChime()
      }
    }

    fun playSuccess() {
      if (!isMuted) {
        soundHelper.playCelebration()
      }
    }

    // Handle back button from About or return to Home from another tab.
    BackHandler(enabled = childName.isNotBlank() && (showAboutScreen || currentTab != AppTab.HOME)) {
      if (showAboutScreen) showAboutScreen = false else currentTab = AppTab.HOME
    }

    if (childName.isBlank()) {
      FirstRunNameScreen(
        onSaveName = progressRepository::saveChildName,
        onRequestImport = {
          importProgressLauncher.launch(arrayOf("text/xml", "application/xml", "*/*"))
        },
        importMessage = importMessage
      )
    } else Scaffold(
      modifier = modifier.fillMaxSize(),
      containerColor = MaterialTheme.colorScheme.background,
      contentWindowInsets = WindowInsets(0, 0, 0, 0),
      topBar = {
        TopAppBar(
          modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .shadow(2.dp),
          colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
          ),
          title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(38.dp)
                  .clip(CircleShape)
                  .border(2.dp, KidsSecondary, CircleShape)
              ) {
                Image(
                  painter = painterResource(id = R.drawable.ic_kids_arabic_letters_1792832477145),
                  contentDescription = "أيقونة اللعبة",
                  modifier = Modifier.fillMaxSize()
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = if (showAboutScreen) "حول التطبيق" else "حروفي المرحة 🎈",
                  fontSize = 18.sp,
                  fontWeight = FontWeight.Black,
                  color = KidsPrimary
                )
                Text(
                  text = if (showAboutScreen) "معلومات التطبيق والتقدم المحفوظ" else when (currentTab) {
                    AppTab.HOME -> "تعلم الحروف العربية للأطفال"
                    AppTab.TRACING -> "أشكال الحرف: منفصل، أول، وسط، آخر"
                    AppTab.VOWELS -> "المد القصير والمد الطويل"
                    AppTab.QUIZ -> "اختبر معلوماتك واربح نجوماً"
                    AppTab.STICKERS -> "جوائزك وملصقاتك الجميلة"
                  },
                  fontSize = 11.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          },
          actions = {
            IconButton(
              onClick = { showAboutScreen = !showAboutScreen },
              modifier = Modifier.size(40.dp)
            ) {
              Icon(
                imageVector = if (showAboutScreen) Icons.AutoMirrored.Filled.ArrowBack else Icons.Default.Info,
                contentDescription = if (showAboutScreen) "العودة" else "حول التطبيق",
                tint = KidsSecondary
              )
            }

            if (!showAboutScreen) {
            // Sound toggle
            IconButton(
              onClick = {
                isMuted = !isMuted
                if (!isMuted) {
                  soundHelper.speak("تم تفعيل الصوت!")
                }
              },
              modifier = Modifier.size(40.dp)
            ) {
              Icon(
                imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                contentDescription = if (isMuted) "تشغيل الصوت" else "كتم الصوت",
                tint = if (isMuted) Color.Gray else KidsSecondary
              )
            }

            // Total Stars Counter Pill
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = Color(0xFFFFF9C4),
              border = androidx.compose.foundation.BorderStroke(1.5.dp, KidsAccentYellow),
              modifier = Modifier
                .padding(end = 12.dp)
                .clickable {
                  currentTab = AppTab.STICKERS
                  playSound("لديك $totalStars نجوم ذهبية في دفتر الملصقات!")
                }
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.Star,
                  contentDescription = null,
                  tint = Color(0xFFFFA000),
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "$totalStars",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Black,
                  color = Color(0xFFE65100)
                )
              }
            }
            }
          }
        )
      },
      bottomBar = {
        if (!showAboutScreen) {
        NavigationBar(
          modifier = Modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .shadow(8.dp),
          containerColor = Color.White,
          tonalElevation = 6.dp
        ) {
          AppTab.values().forEach { tab ->
            val isSelected = currentTab == tab
            NavigationBarItem(
              selected = isSelected,
              onClick = {
                currentTab = tab
                soundHelper.playClick()
                when (tab) {
                  AppTab.HOME -> playSound("قطار الحروف")
                  AppTab.TRACING -> playSound("كتابة الحرف بأشكاله")
                  AppTab.VOWELS -> playSound("عالم المدود والحركات")
                  AppTab.QUIZ -> playSound("لعبة التحدي")
                  AppTab.STICKERS -> playSound("دفتر الملصقات والجوائز")
                }
              },
              icon = {
                Icon(
                  imageVector = tab.icon,
                  contentDescription = tab.title,
                  modifier = Modifier.size(24.dp)
                )
              },
              label = {
                Text(
                  text = tab.title,
                  fontSize = 11.sp,
                  fontWeight = if (isSelected) FontWeight.Black else FontWeight.Normal
                )
              },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = KidsPrimary,
                indicatorColor = KidsPrimary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
              ),
              modifier = Modifier.testTag(tab.tag)
            )
          }
        }
        }
      }
    ) { innerPadding ->
      if (showAboutScreen) {
        AboutScreen(
          childName = childName,
          appVersion = BuildConfig.VERSION_NAME,
          totalStars = totalStars,
          tracedForms = tracedFormsCount,
          totalForms = LetterRepository.letters.sumOf { it.forms.size },
          practicedVowelSections = practicedVowelSections.size,
          totalVowelSections = LetterRepository.letters.size * 3,
          quizAnsweredQuestions = quizAnsweredQuestions,
          quizCorrectAnswers = quizCorrectAnswers,
          highestQuizQuestion = highestQuizQuestion,
          modifier = Modifier.padding(innerPadding),
          onClearProgress = { keepName ->
            progressRepository.clearProgress(keepName)
            selectedLetter = LetterRepository.letters[1]
            currentTab = AppTab.HOME
            showAboutScreen = false
          }
        )
      } else Crossfade(
        targetState = currentTab,
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) { tab ->
        when (tab) {
          AppTab.HOME -> HomeScreen(
            letters = LetterRepository.letters,
            childName = childName,
            resumeActivityName = resumeActivityName,
            onResumeLearning = { currentTab = resumeTab },
            completedLetters = completedLetters,
            totalStars = totalStars,
            onSelectLetter = { letter ->
              selectedLetter = letter
              currentTab = AppTab.TRACING
            },
            onQuickStartTracing = {
              currentTab = AppTab.TRACING
            },
            onQuickStartVowels = {
              currentTab = AppTab.VOWELS
            },
            onQuickStartQuiz = {
              currentTab = AppTab.QUIZ
            },
            onPlaySound = { text -> playSound(text) }
          )

          AppTab.TRACING -> TracingScreen(
            currentLetter = selectedLetter,
            allLetters = LetterRepository.letters,
            onSelectLetter = { letter -> selectedLetter = letter },
            initialPosition = progressRepository.lastTracePosition(),
            onPositionSelected = progressRepository::saveLastTracePosition,
            onFormCompleted = { letter, position ->
              progressRepository.recordFormTraced(letter.char, position)
            },
            onPlaySound = { text -> playSound(text) },
            onPlaySuccessChime = { playSuccess() }
          )

          AppTab.VOWELS -> VowelsScreen(
            currentLetter = selectedLetter,
            allLetters = LetterRepository.letters,
            onSelectLetter = { letter -> selectedLetter = letter },
            onPlaySound = { text -> playSound(text) },
            initialSelectedTab = lastVowelTab,
            onVowelSectionOpened = { letter, section ->
              progressRepository.recordVowelSection(letter.char, section)
            },
            onSelectedTabChanged = progressRepository::saveLastVowelTab
          )

          AppTab.QUIZ -> QuizGameScreen(
            letters = LetterRepository.letters,
            onAddStars = { stars -> progressRepository.addStars(stars) },
            onPlaySound = { text -> playSound(text) },
            onPlaySuccessChime = { playSuccess() },
            initialQuestionIndex = quizCurrentQuestion,
            initialAnswered = quizAnswered,
            initialSelectedOption = quizSelectedOption,
            initialCorrectCount = quizCorrectAnswers,
            onAnswerSubmitted = { questionIndex, selectedOption, isCorrect ->
              progressRepository.recordQuizAnswer(questionIndex, selectedOption, isCorrect)
            },
            onQuestionAdvanced = progressRepository::advanceQuizTo
          )

          AppTab.STICKERS -> StickersScreen(
            totalStars = totalStars,
            onPlaySound = { text -> playSound(text) },
            onPlayChime = { playChime() }
          )
        }
      }
    }
  }
}
