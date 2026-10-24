package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.LetterPosition
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserProgressRepository(context: Context) {

  private val prefs: SharedPreferences =
    context.getSharedPreferences("kids_arabic_progress", Context.MODE_PRIVATE)
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

  private val _totalStars = MutableStateFlow(prefs.getInt(KEY_STARS, 5))
  val totalStars: StateFlow<Int> = _totalStars.asStateFlow()

  private val _completedLetters = MutableStateFlow(completedLettersFrom(practicedForms))
  val completedLetters: StateFlow<Set<String>> = _completedLetters.asStateFlow()

  private val _tracedFormsCount = MutableStateFlow(practicedForms.size)
  val tracedFormsCount: StateFlow<Int> = _tracedFormsCount.asStateFlow()

  fun addStars(amount: Int) {
    if (amount <= 0) return
    synchronized(lock) {
      val newCount = _totalStars.value + amount
      _totalStars.value = newCount
      prefs.edit().putInt(KEY_STARS, newCount).apply()
    }
  }

  /**
   * Records a letter form once. Returns true only for a newly practiced form; only that first
   * completion awards a star. A letter is complete only after all four positions are practiced.
   */
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

  fun isLetterCompleted(letterChar: String): Boolean =
    _completedLetters.value.contains(letterChar)

  private fun completedLettersFrom(formIds: Set<String>): Set<String> {
    val letters = formIds.mapNotNull { it.substringBefore(FORM_ID_SEPARATOR).takeIf(String::isNotEmpty) }
    return letters.filterTo(mutableSetOf()) { letter ->
      LetterPosition.values().all { position -> formId(letter, position) in formIds }
    }
  }

  private fun formId(letterChar: String, position: LetterPosition): String =
    "$letterChar$FORM_ID_SEPARATOR${position.name}"

  companion object {
    private const val KEY_STARS = "key_stars"
    private const val KEY_COMPLETED_LETTERS = "key_completed_letters"
    private const val KEY_TRACED_FORMS = "key_traced_forms"
    private const val KEY_LEGACY_TRACED_FORMS = KEY_TRACED_FORMS
    private const val KEY_PRACTICED_FORMS = "key_practiced_forms_v2"
    private const val KEY_FORM_PROGRESS_MIGRATED = "key_form_progress_migrated_v2"
    private const val FORM_ID_SEPARATOR = "|"

    @Volatile
    private var INSTANCE: UserProgressRepository? = null

    fun getInstance(context: Context): UserProgressRepository {
      return INSTANCE ?: synchronized(this) {
        INSTANCE ?: UserProgressRepository(context.applicationContext).also { INSTANCE = it }
      }
    }
  }
}
