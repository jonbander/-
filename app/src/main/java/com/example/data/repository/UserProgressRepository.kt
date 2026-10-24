package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserProgressRepository(context: Context) {

  private val prefs: SharedPreferences =
    context.getSharedPreferences("kids_arabic_progress", Context.MODE_PRIVATE)

  private val _totalStars = MutableStateFlow(prefs.getInt(KEY_STARS, 5)) // Start with 5 welcome stars!
  val totalStars: StateFlow<Int> = _totalStars.asStateFlow()

  private val _completedLetters = MutableStateFlow(
    prefs.getStringSet(KEY_COMPLETED_LETTERS, emptySet()) ?: emptySet()
  )
  val completedLetters: StateFlow<Set<String>> = _completedLetters.asStateFlow()

  private val _tracedFormsCount = MutableStateFlow(prefs.getInt(KEY_TRACED_FORMS, 0))
  val tracedFormsCount: StateFlow<Int> = _tracedFormsCount.asStateFlow()

  fun addStars(amount: Int) {
    val newCount = _totalStars.value + amount
    _totalStars.value = newCount
    prefs.edit().putInt(KEY_STARS, newCount).apply()
  }

  fun recordFormTraced(letterChar: String, positionName: String) {
    val newCount = _tracedFormsCount.value + 1
    _tracedFormsCount.value = newCount
    prefs.edit().putInt(KEY_TRACED_FORMS, newCount).apply()

    // Award star
    addStars(1)

    // Mark letter completed if all forms are practiced
    val currentSet = _completedLetters.value.toMutableSet()
    currentSet.add(letterChar)
    _completedLetters.value = currentSet
    prefs.edit().putStringSet(KEY_COMPLETED_LETTERS, currentSet).apply()
  }

  fun isLetterCompleted(letterChar: String): Boolean {
    return _completedLetters.value.contains(letterChar)
  }

  companion object {
    private const val KEY_STARS = "key_stars"
    private const val KEY_COMPLETED_LETTERS = "key_completed_letters"
    private const val KEY_TRACED_FORMS = "key_traced_forms"

    @Volatile
    private var INSTANCE: UserProgressRepository? = null

    fun getInstance(context: Context): UserProgressRepository {
      return INSTANCE ?: synchronized(this) {
        INSTANCE ?: UserProgressRepository(context.applicationContext).also { INSTANCE = it }
      }
    }
  }
}
