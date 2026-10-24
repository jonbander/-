package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Xml
import com.example.data.model.LetterPosition
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.xmlpull.v1.XmlPullParser
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

/** Local-only profile and learning history stored in the app's private SharedPreferences. */
class UserProgressRepository(context: Context) {

  private val prefs: SharedPreferences =
    context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
  private val lock = Any()

  init {
    // Earlier versions marked a letter complete after practicing just one form. Preserve earned
    // stars, but discard progress that cannot be accurately mapped to individual forms.
    if (!prefs.getBoolean(KEY_FORM_PROGRESS_MIGRATED, false)) {
      prefs.edit()
        .remove(KEY_COMPLETED_LETTERS)
        .remove(KEY_LEGACY_TRACED_FORMS)
        .putBoolean(KEY_FORM_PROGRESS_MIGRATED, true)
        .apply()
    }
  }

  private val practicedForms =
    (prefs.getStringSet(KEY_PRACTICED_FORMS, emptySet()) ?: emptySet()).toMutableSet()
  private val _practicedVowelSections = MutableStateFlow(
    (prefs.getStringSet(KEY_PRACTICED_VOWEL_SECTIONS, emptySet()) ?: emptySet()).toSet()
  )
  private val _childName = MutableStateFlow(prefs.getString(KEY_CHILD_NAME, "").orEmpty())
  private val _totalStars = MutableStateFlow(prefs.getInt(KEY_STARS, INITIAL_STARS))
  private val _completedLetters = MutableStateFlow(completedLettersFrom(practicedForms))
  private val _tracedFormsCount = MutableStateFlow(practicedForms.size)
  private val _lastVowelTab = MutableStateFlow(prefs.getInt(KEY_LAST_VOWEL_TAB, 0).coerceIn(0, 2))
  private val _quizCurrentQuestionIndex = MutableStateFlow(
    prefs.getInt(KEY_QUIZ_CURRENT_INDEX, 0).coerceIn(0, QUIZ_QUESTION_COUNT - 1)
  )
  private val _quizQuestionAnswered = MutableStateFlow(prefs.getBoolean(KEY_QUIZ_ANSWERED, false))
  private val _quizSelectedOptionIndex = MutableStateFlow(
    prefs.getInt(KEY_QUIZ_SELECTED_OPTION, NO_SELECTED_OPTION).takeIf { it >= 0 }
  )
  private val _quizCorrectAnswers = MutableStateFlow(prefs.getInt(KEY_QUIZ_CORRECT_ANSWERS, 0).coerceAtLeast(0))
  private val _quizAnsweredQuestions = MutableStateFlow(prefs.getInt(KEY_QUIZ_ANSWERED_QUESTIONS, 0).coerceAtLeast(0))
  private val _highestQuizQuestion = MutableStateFlow(
    prefs.getInt(KEY_HIGHEST_QUIZ_QUESTION, 0).coerceIn(0, QUIZ_QUESTION_COUNT)
  )

  val childName: StateFlow<String> = _childName.asStateFlow()
  val totalStars: StateFlow<Int> = _totalStars.asStateFlow()
  val completedLetters: StateFlow<Set<String>> = _completedLetters.asStateFlow()
  val tracedFormsCount: StateFlow<Int> = _tracedFormsCount.asStateFlow()
  val practicedVowelSections: StateFlow<Set<String>> = _practicedVowelSections.asStateFlow()
  val lastVowelTab: StateFlow<Int> = _lastVowelTab.asStateFlow()
  val quizCurrentQuestionIndex: StateFlow<Int> = _quizCurrentQuestionIndex.asStateFlow()
  val quizQuestionAnswered: StateFlow<Boolean> = _quizQuestionAnswered.asStateFlow()
  val quizSelectedOptionIndex: StateFlow<Int?> = _quizSelectedOptionIndex.asStateFlow()
  val quizCorrectAnswers: StateFlow<Int> = _quizCorrectAnswers.asStateFlow()
  val quizAnsweredQuestions: StateFlow<Int> = _quizAnsweredQuestions.asStateFlow()
  val highestQuizQuestion: StateFlow<Int> = _highestQuizQuestion.asStateFlow()

  fun saveChildName(name: String) {
    val cleanName = name.trim().take(MAX_CHILD_NAME_LENGTH)
    if (cleanName.isEmpty()) return
    synchronized(lock) {
      _childName.value = cleanName
      prefs.edit().putString(KEY_CHILD_NAME, cleanName).apply()
    }
  }

  fun saveLastTab(tabName: String) {
    synchronized(lock) {
      val editor = prefs.edit().putString(KEY_LAST_TAB, tabName)
      if (tabName == TAB_TRACING || tabName == TAB_VOWELS || tabName == TAB_QUIZ) {
        editor.putString(KEY_LAST_LEARNING_TAB, tabName)
      }
      editor.apply()
    }
  }

  fun lastTabName(): String? = prefs.getString(KEY_LAST_TAB, null)

  fun lastLearningTabName(): String =
    prefs.getString(KEY_LAST_LEARNING_TAB, DEFAULT_LEARNING_TAB) ?: DEFAULT_LEARNING_TAB

  fun saveLastLetter(letterChar: String) {
    synchronized(lock) {
      prefs.edit().putString(KEY_LAST_LETTER, letterChar).apply()
    }
  }

  fun lastLetterChar(): String? = prefs.getString(KEY_LAST_LETTER, null)

  fun saveLastTracePosition(position: LetterPosition) {
    synchronized(lock) {
      prefs.edit().putString(KEY_LAST_TRACE_POSITION, position.name).apply()
    }
  }

  fun lastTracePosition(): LetterPosition? =
    prefs.getString(KEY_LAST_TRACE_POSITION, null)?.let { value ->
      LetterPosition.values().firstOrNull { it.name == value }
    }

  fun saveLastVowelTab(tabIndex: Int) {
    val safeIndex = tabIndex.coerceIn(0, 2)
    synchronized(lock) {
      _lastVowelTab.value = safeIndex
      prefs.edit().putInt(KEY_LAST_VOWEL_TAB, safeIndex).apply()
    }
  }

  fun recordVowelSection(letterChar: String, sectionIndex: Int): Boolean {
    if (letterChar.isBlank() || sectionIndex !in 0..2) return false
    synchronized(lock) {
      val sectionId = "$letterChar$FORM_ID_SEPARATOR$sectionIndex"
      val updated = _practicedVowelSections.value + sectionId
      if (updated == _practicedVowelSections.value) return false
      _practicedVowelSections.value = updated
      prefs.edit().putStringSet(KEY_PRACTICED_VOWEL_SECTIONS, updated).apply()
      return true
    }
  }

  fun addStars(amount: Int) {
    if (amount <= 0) return
    synchronized(lock) {
      val newCount = _totalStars.value + amount
      _totalStars.value = newCount
      prefs.edit().putInt(KEY_STARS, newCount).apply()
    }
  }

  /** Records a letter form once. Only the first completion awards a star. */
  fun recordFormTraced(letterChar: String, position: LetterPosition): Boolean = synchronized(lock) {
    val formId = formId(letterChar, position)
    if (!practicedForms.add(formId)) return@synchronized false

    val persistedForms = practicedForms.toSet()
    val completed = completedLettersFrom(persistedForms)
    val newStars = _totalStars.value + 1
    _tracedFormsCount.value = persistedForms.size
    _completedLetters.value = completed
    _totalStars.value = newStars
    prefs.edit()
      .putStringSet(KEY_PRACTICED_FORMS, persistedForms)
      .putInt(KEY_TRACED_FORMS, persistedForms.size)
      .putInt(KEY_STARS, newStars)
      .apply()
    true
  }

  /** Saves each submitted quiz answer so leaving the app never loses the current question. */
  fun recordQuizAnswer(questionIndex: Int, selectedOptionIndex: Int, isCorrect: Boolean): Boolean {
    if (questionIndex !in 0 until QUIZ_QUESTION_COUNT || selectedOptionIndex !in 0 until QUIZ_OPTION_COUNT) return false
    synchronized(lock) {
      if (_quizQuestionAnswered.value) return false
      val correct = _quizCorrectAnswers.value + if (isCorrect) 1 else 0
      val answered = _quizAnsweredQuestions.value + 1
      val highest = maxOf(_highestQuizQuestion.value, questionIndex + 1).coerceAtMost(QUIZ_QUESTION_COUNT)
      _quizQuestionAnswered.value = true
      _quizSelectedOptionIndex.value = selectedOptionIndex
      _quizCorrectAnswers.value = correct
      _quizAnsweredQuestions.value = answered
      _highestQuizQuestion.value = highest
      prefs.edit()
        .putBoolean(KEY_QUIZ_ANSWERED, true)
        .putInt(KEY_QUIZ_SELECTED_OPTION, selectedOptionIndex)
        .putInt(KEY_QUIZ_CORRECT_ANSWERS, correct)
        .putInt(KEY_QUIZ_ANSWERED_QUESTIONS, answered)
        .putInt(KEY_HIGHEST_QUIZ_QUESTION, highest)
        .apply()
      return true
    }
  }

  fun advanceQuizTo(questionIndex: Int) {
    val safeIndex = questionIndex.coerceIn(0, QUIZ_QUESTION_COUNT - 1)
    synchronized(lock) {
      _quizCurrentQuestionIndex.value = safeIndex
      _quizQuestionAnswered.value = false
      _quizSelectedOptionIndex.value = null
      prefs.edit()
        .putInt(KEY_QUIZ_CURRENT_INDEX, safeIndex)
        .putBoolean(KEY_QUIZ_ANSWERED, false)
        .remove(KEY_QUIZ_SELECTED_OPTION)
        .apply()
    }
  }

  fun isLetterCompleted(letterChar: String): Boolean =
    _completedLetters.value.contains(letterChar)

  /** Imports only recognized progress fields from an exported Android SharedPreferences XML file. */
  fun importLegacyProgress(input: InputStream): Boolean {
    val values = parseLegacyPreferences(input) ?: return false
    val rawForms = values[KEY_PRACTICED_FORMS] as? Set<*> ?: emptySet<Any>()
    val knownLetters = LetterRepository.letters.mapTo(mutableSetOf()) { it.char }
    val importedForms = rawForms.mapNotNullTo(mutableSetOf()) { raw ->
      val value = raw as? String ?: return@mapNotNullTo null
      val separator = value.lastIndexOf(FORM_ID_SEPARATOR)
      if (separator <= 0) return@mapNotNullTo null
      val letter = value.substring(0, separator)
      val position = value.substring(separator + FORM_ID_SEPARATOR.length)
      value.takeIf {
        letter in knownLetters && LetterPosition.values().any { it.name == position }
      }
    }
    val hasKnownData = values.containsKey(KEY_STARS) ||
      values.containsKey(KEY_PRACTICED_FORMS) ||
      values[KEY_FORM_PROGRESS_MIGRATED] == true
    if (!hasKnownData) return false

    val importedStars = (values[KEY_STARS] as? Int ?: INITIAL_STARS).coerceAtLeast(INITIAL_STARS)
    synchronized(lock) {
      practicedForms.clear()
      practicedForms.addAll(importedForms)
      _totalStars.value = importedStars
      _tracedFormsCount.value = importedForms.size
      _completedLetters.value = completedLettersFrom(importedForms)
      _practicedVowelSections.value = emptySet()
      _lastVowelTab.value = 0
      _quizCurrentQuestionIndex.value = 0
      _quizQuestionAnswered.value = false
      _quizSelectedOptionIndex.value = null
      _quizCorrectAnswers.value = 0
      _quizAnsweredQuestions.value = 0
      _highestQuizQuestion.value = 0
      prefs.edit()
        .putBoolean(KEY_FORM_PROGRESS_MIGRATED, true)
        .putStringSet(KEY_PRACTICED_FORMS, importedForms)
        .putInt(KEY_TRACED_FORMS, importedForms.size)
        .putInt(KEY_STARS, importedStars)
        .putStringSet(KEY_PRACTICED_VOWEL_SECTIONS, emptySet())
        .putInt(KEY_LAST_VOWEL_TAB, 0)
        .putInt(KEY_QUIZ_CURRENT_INDEX, 0)
        .putBoolean(KEY_QUIZ_ANSWERED, false)
        .remove(KEY_QUIZ_SELECTED_OPTION)
        .putInt(KEY_QUIZ_CORRECT_ANSWERS, 0)
        .putInt(KEY_QUIZ_ANSWERED_QUESTIONS, 0)
        .putInt(KEY_HIGHEST_QUIZ_QUESTION, 0)
        .putString(KEY_LAST_TAB, DEFAULT_TAB)
        .putString(KEY_LAST_LEARNING_TAB, DEFAULT_LEARNING_TAB)
        .putString(KEY_LAST_LETTER, DEFAULT_LETTER)
        .putString(KEY_LAST_TRACE_POSITION, LetterPosition.ISOLATED.name)
        .apply()
    }
    return true
  }

  private fun parseLegacyPreferences(input: InputStream): Map<String, Any>? {
    val bytes = ByteArrayOutputStream()
    val buffer = ByteArray(8192)
    var totalBytes = 0
    try {
      while (true) {
        val read = input.read(buffer)
        if (read < 0) break
        totalBytes += read
        if (totalBytes > MAX_IMPORT_BYTES) return null
        bytes.write(buffer, 0, read)
      }

      val parser = Xml.newPullParser()
      parser.setInput(ByteArrayInputStream(bytes.toByteArray()), null)
      if (parser.nextTag() != XmlPullParser.START_TAG || parser.name != "map") return null

      val values = mutableMapOf<String, Any>()
      var event = parser.next()
      while (!(event == XmlPullParser.END_TAG && parser.name == "map")) {
        if (event == XmlPullParser.START_TAG) {
          val key = parser.getAttributeValue(null, "name")
          when (parser.name) {
            "string" -> if (key != null) values[key] = parser.nextText()
            "int" -> if (key != null) {
              parser.getAttributeValue(null, "value")?.toIntOrNull()?.let { values[key] = it }
            }
            "boolean" -> if (key != null) {
              parser.getAttributeValue(null, "value")?.toBooleanStrictOrNull()?.let { values[key] = it }
            }
            "set" -> if (key != null) {
              val strings = mutableSetOf<String>()
              var nestedEvent = parser.next()
              while (!(nestedEvent == XmlPullParser.END_TAG && parser.name == "set")) {
                if (nestedEvent == XmlPullParser.START_TAG && parser.name == "string") {
                  strings.add(parser.nextText())
                }
                nestedEvent = parser.next()
              }
              values[key] = strings
            }
          }
        }
        event = parser.next()
      }
      return values
    } catch (_: Exception) {
      return null
    }
  }

  /** Clears all local learning history; the child's name is kept only when explicitly requested. */
  fun clearProgress(keepChildName: Boolean) {
    synchronized(lock) {
      val savedName = if (keepChildName) _childName.value else ""
      prefs.edit().clear()
        .putBoolean(KEY_FORM_PROGRESS_MIGRATED, true)
        .putInt(KEY_STARS, INITIAL_STARS)
        .putInt(KEY_LAST_VOWEL_TAB, 0)
        .putInt(KEY_QUIZ_CURRENT_INDEX, 0)
        .putBoolean(KEY_QUIZ_ANSWERED, false)
        .putInt(KEY_QUIZ_CORRECT_ANSWERS, 0)
        .putInt(KEY_QUIZ_ANSWERED_QUESTIONS, 0)
        .putInt(KEY_HIGHEST_QUIZ_QUESTION, 0)
        .putString(KEY_LAST_TAB, DEFAULT_TAB)
        .putString(KEY_LAST_LEARNING_TAB, DEFAULT_LEARNING_TAB)
        .putString(KEY_LAST_LETTER, DEFAULT_LETTER)
        .putString(KEY_LAST_TRACE_POSITION, LetterPosition.ISOLATED.name)
        .putString(KEY_CHILD_NAME, savedName)
        .apply()

      practicedForms.clear()
      _childName.value = savedName
      _totalStars.value = INITIAL_STARS
      _completedLetters.value = emptySet()
      _tracedFormsCount.value = 0
      _practicedVowelSections.value = emptySet()
      _lastVowelTab.value = 0
      _quizCurrentQuestionIndex.value = 0
      _quizQuestionAnswered.value = false
      _quizSelectedOptionIndex.value = null
      _quizCorrectAnswers.value = 0
      _quizAnsweredQuestions.value = 0
      _highestQuizQuestion.value = 0
    }
  }

  private fun completedLettersFrom(formIds: Set<String>): Set<String> {
    val letters = formIds.mapNotNull { it.substringBefore(FORM_ID_SEPARATOR).takeIf(String::isNotEmpty) }
    return letters.filterTo(mutableSetOf()) { letter ->
      LetterPosition.values().all { position -> formId(letter, position) in formIds }
    }
  }

  private fun formId(letterChar: String, position: LetterPosition): String =
    "$letterChar$FORM_ID_SEPARATOR${position.name}"

  companion object {
    const val INITIAL_STARS = 5
    const val MAX_CHILD_NAME_LENGTH = 24
    private const val PREFERENCES_NAME = "kids_arabic_progress"
    private const val DEFAULT_TAB = "HOME"
    private const val DEFAULT_LEARNING_TAB = "TRACING"
    private const val TAB_TRACING = "TRACING"
    private const val TAB_VOWELS = "VOWELS"
    private const val TAB_QUIZ = "QUIZ"
    private const val DEFAULT_LETTER = "ب"
    private const val QUIZ_QUESTION_COUNT = 7
    private const val QUIZ_OPTION_COUNT = 3
    private const val KEY_CHILD_NAME = "child_name"
    private const val KEY_STARS = "key_stars"
    private const val KEY_COMPLETED_LETTERS = "key_completed_letters"
    private const val KEY_TRACED_FORMS = "key_traced_forms"
    private const val KEY_LEGACY_TRACED_FORMS = KEY_TRACED_FORMS
    private const val KEY_PRACTICED_FORMS = "key_practiced_forms_v2"
    private const val KEY_FORM_PROGRESS_MIGRATED = "key_form_progress_migrated_v2"
    private const val KEY_PRACTICED_VOWEL_SECTIONS = "key_practiced_vowel_sections"
    private const val KEY_LAST_TAB = "key_last_tab"
    private const val KEY_LAST_LEARNING_TAB = "key_last_learning_tab"
    private const val KEY_LAST_LETTER = "key_last_letter"
    private const val KEY_LAST_TRACE_POSITION = "key_last_trace_position"
    private const val KEY_LAST_VOWEL_TAB = "key_last_vowel_tab"
    private const val KEY_QUIZ_CURRENT_INDEX = "key_quiz_current_index"
    private const val KEY_QUIZ_ANSWERED = "key_quiz_question_answered"
    private const val KEY_QUIZ_SELECTED_OPTION = "key_quiz_selected_option"
    private const val KEY_QUIZ_CORRECT_ANSWERS = "key_quiz_correct_answers"
    private const val KEY_QUIZ_ANSWERED_QUESTIONS = "key_quiz_answered_questions"
    private const val KEY_HIGHEST_QUIZ_QUESTION = "key_highest_quiz_question"
    private const val FORM_ID_SEPARATOR = "|"
    private const val NO_SELECTED_OPTION = -1
    private const val MAX_IMPORT_BYTES = 512 * 1024

    @Volatile
    private var INSTANCE: UserProgressRepository? = null

    fun getInstance(context: Context): UserProgressRepository {
      return INSTANCE ?: synchronized(this) {
        INSTANCE ?: UserProgressRepository(context.applicationContext).also { INSTANCE = it }
      }
    }
  }
}
