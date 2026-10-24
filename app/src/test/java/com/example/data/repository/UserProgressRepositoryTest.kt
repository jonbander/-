package com.example.data.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.LetterPosition
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.ByteArrayInputStream

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

  @Test
  fun childNameAndLearningLocationSurviveRepositoryRecreation() {
    val repository = UserProgressRepository(context)
    repository.saveChildName("  أحمد  ")
    repository.saveLastTab("VOWELS")
    repository.saveLastLetter("م")
    repository.saveLastTracePosition(LetterPosition.MEDIAL)
    repository.saveLastVowelTab(2)
    repository.recordVowelSection("م", 2)
    repository.advanceQuizTo(3)
    assertTrue(repository.recordQuizAnswer(3, 1, false))

    val reopened = UserProgressRepository(context)

    assertEquals("أحمد", reopened.childName.value)
    assertEquals("VOWELS", reopened.lastTabName())
    assertEquals("VOWELS", reopened.lastLearningTabName())
    assertEquals("م", reopened.lastLetterChar())
    assertEquals(LetterPosition.MEDIAL, reopened.lastTracePosition())
    assertEquals(2, reopened.lastVowelTab.value)
    assertTrue(reopened.practicedVowelSections.value.contains("م|2"))
    assertEquals(3, reopened.quizCurrentQuestionIndex.value)
    assertTrue(reopened.quizQuestionAnswered.value)
    assertEquals(1, reopened.quizSelectedOptionIndex.value)
    assertEquals(1, reopened.quizAnsweredQuestions.value)
    assertEquals(4, reopened.highestQuizQuestion.value)
  }

  @Test
  fun clearingProgressCanKeepTheChildNameAndResetAllLearningRecords() {
    val repository = UserProgressRepository(context)
    repository.saveChildName("ليان")
    repository.recordFormTraced("ب", LetterPosition.ISOLATED)
    repository.recordVowelSection("ب", 1)
    repository.advanceQuizTo(2)
    repository.recordQuizAnswer(2, 0, true)
    repository.addStars(4)

    repository.clearProgress(keepChildName = true)

    assertEquals("ليان", repository.childName.value)
    assertEquals(UserProgressRepository.INITIAL_STARS, repository.totalStars.value)
    assertEquals(0, repository.tracedFormsCount.value)
    assertTrue(repository.completedLetters.value.isEmpty())
    assertTrue(repository.practicedVowelSections.value.isEmpty())
    assertEquals(0, repository.quizCurrentQuestionIndex.value)
    assertFalse(repository.quizQuestionAnswered.value)
    assertEquals(0, repository.quizCorrectAnswers.value)
    assertEquals("HOME", repository.lastTabName())
    assertEquals("TRACING", repository.lastLearningTabName())
    assertEquals("ب", repository.lastLetterChar())
  }

  @Test
  fun clearingProgressCanAlsoRemoveTheChildName() {
    val repository = UserProgressRepository(context)
    repository.saveChildName("ليان")

    repository.clearProgress(keepChildName = false)

    assertEquals("", repository.childName.value)
    assertNull(repository.quizSelectedOptionIndex.value)
  }

  @Test
  fun quizAnswerCannotBeRecordedTwiceAndRestartsAtTheSavedQuestion() {
    val repository = UserProgressRepository(context)
    repository.advanceQuizTo(4)

    assertTrue(repository.recordQuizAnswer(4, 2, true))
    assertFalse(repository.recordQuizAnswer(4, 2, true))
    assertEquals(1, repository.quizCorrectAnswers.value)
    assertEquals(1, repository.quizAnsweredQuestions.value)

    repository.advanceQuizTo(5)
    assertEquals(5, repository.quizCurrentQuestionIndex.value)
    assertFalse(repository.quizQuestionAnswered.value)
    assertNull(repository.quizSelectedOptionIndex.value)
  }

  @Test
  fun importsStarsAndPracticedLetterFormsFromOldSharedPreferencesXml() {
    val repository = UserProgressRepository(context)
    val backup = """
      <map>
        <boolean name="key_form_progress_migrated_v2" value="true" />
        <int name="key_stars" value="17" />
        <set name="key_practiced_forms_v2">
          <string>ب|ISOLATED</string>
          <string>ب|INITIAL</string>
          <string>ب|MEDIAL</string>
          <string>ب|FINAL</string>
          <string>not-a-form</string>
          <string>س|ISOLATED</string>
        </set>
      </map>
    """.trimIndent()

    assertTrue(repository.importLegacyProgress(ByteArrayInputStream(backup.toByteArray(Charsets.UTF_8))))

    assertEquals(17, repository.totalStars.value)
    assertEquals(4, repository.tracedFormsCount.value)
    assertTrue(repository.isLetterCompleted("ب"))
    assertFalse(repository.isLetterCompleted("س"))
    assertEquals(17, UserProgressRepository(context).totalStars.value)
  }

  @Test
  fun invalidLegacyBackupDoesNotChangeProgress() {
    val repository = UserProgressRepository(context)
    repository.addStars(2)

    assertFalse(repository.importLegacyProgress(ByteArrayInputStream("not xml".toByteArray())))
    assertEquals(7, repository.totalStars.value)
  }

  companion object {
    private const val PREFERENCES_NAME = "kids_arabic_progress"
  }
}
