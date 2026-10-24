package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.LetterPosition
import com.example.data.repository.LetterRepository
import com.example.data.repository.UserProgressRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context matches arabic app name`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("حروفي المرحة", appName)
  }

  @Test
  fun `letter repository contains all 28 arabic letters with 4 positions and vowels`() {
    val letters = LetterRepository.letters
    assertEquals(28, letters.size)

    val baa = letters.first { it.char == "ب" }
    assertEquals(4, baa.forms.size)
    assertEquals("ب", baa.forms[0].glyph) // isolated
    assertEquals("بـ", baa.forms[1].glyph) // initial
    assertEquals("ـبـ", baa.forms[2].glyph) // medial
    assertEquals("ـب", baa.forms[3].glyph) // final

    // Verify short and long vowels exist
    assertTrue(baa.shortVowels.isNotEmpty())
    assertTrue(baa.longVowels.isNotEmpty())
    assertTrue(baa.comparisons.isNotEmpty())
  }

  @Test
  fun `progress repository tracks stars and completed letters`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    context.getSharedPreferences("kids_arabic_progress", Context.MODE_PRIVATE)
      .edit()
      .clear()
      .commit()
    val repo = UserProgressRepository(context)

    val initialStars = repo.totalStars.value
    repo.addStars(5)
    assertEquals(initialStars + 5, repo.totalStars.value)

    LetterPosition.values().dropLast(1).forEach { position ->
      repo.recordFormTraced("ب", position)
      assertFalse(repo.isLetterCompleted("ب"))
    }
    repo.recordFormTraced("ب", LetterPosition.FINAL)

    assertTrue(repo.isLetterCompleted("ب"))
    assertEquals(initialStars + 9, repo.totalStars.value)
  }
}
