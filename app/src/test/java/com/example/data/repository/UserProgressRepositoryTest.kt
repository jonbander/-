package com.example.data.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.LetterPosition
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserProgressRepositoryTest {

  private lateinit var context: Context

  @Before
  fun setUp() {
    context = ApplicationProvider.getApplicationContext()
    context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
      .edit()
      .clear()
      .commit()
  }

  @Test
  fun recordingSameFormTwiceAwardsOnlyOneStar() {
    val repository = UserProgressRepository(context)

    assertTrue(repository.recordFormTraced("ب", LetterPosition.ISOLATED))
    assertFalse(repository.recordFormTraced("ب", LetterPosition.ISOLATED))

    assertEquals(6, repository.totalStars.value)
    assertEquals(1, repository.tracedFormsCount.value)
    assertFalse(repository.isLetterCompleted("ب"))
  }

  @Test
  fun letterIsCompletedOnlyAfterAllFourPositionsArePracticed() {
    val repository = UserProgressRepository(context)
    val positions = LetterPosition.values()

    positions.dropLast(1).forEach { position ->
      assertTrue(repository.recordFormTraced("ب", position))
      assertFalse(repository.isLetterCompleted("ب"))
    }

    assertTrue(repository.recordFormTraced("ب", positions.last()))
    assertTrue(repository.isLetterCompleted("ب"))
    assertEquals(4, repository.tracedFormsCount.value)
    assertEquals(9, repository.totalStars.value)
  }

  @Test
  fun practicedFormsAndStarsSurviveRepositoryRecreation() {
    val firstRepository = UserProgressRepository(context)
    firstRepository.recordFormTraced("ت", LetterPosition.INITIAL)

    val reopenedRepository = UserProgressRepository(context)

    assertEquals(6, reopenedRepository.totalStars.value)
    assertEquals(1, reopenedRepository.tracedFormsCount.value)
    assertFalse(reopenedRepository.isLetterCompleted("ت"))
    assertFalse(reopenedRepository.recordFormTraced("ت", LetterPosition.INITIAL))
    assertEquals(6, reopenedRepository.totalStars.value)
  }

  @Test
  fun migrationPreservesStarsButClearsUnverifiableLegacyCompletion() {
    context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
      .edit()
      .putInt("key_stars", 12)
      .putInt("key_traced_forms", 19)
      .putStringSet("key_completed_letters", setOf("ب"))
      .commit()

    val repository = UserProgressRepository(context)

    assertEquals(12, repository.totalStars.value)
    assertEquals(0, repository.tracedFormsCount.value)
    assertTrue(repository.completedLetters.value.isEmpty())
    assertFalse(repository.isLetterCompleted("ب"))
  }

  companion object {
    private const val PREFERENCES_NAME = "kids_arabic_progress"
  }
}
