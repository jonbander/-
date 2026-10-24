package com.example.ui.components

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import com.example.data.model.LetterPosition
import com.example.data.repository.LetterRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class GlyphTraceMatcherTest {

  @Test
  fun `complete trace close to target is accepted`() {
    val template = lineAndCornerTemplate()
    val trace = listOf(
      listOf(Offset(20f, 30f), Offset(80f, 30f), Offset(80f, 70f))
    )

    val result = GlyphTraceMatcher.evaluate(template, trace)

    assertTrue(result.accepted)
    assertTrue(result.precision > 0.95f)
    assertTrue(result.coverage > 0.95f)
  }

  @Test
  fun `unrelated scribble is rejected`() {
    val template = lineAndCornerTemplate()
    val trace = listOf(listOf(Offset(15f, 88f), Offset(85f, 88f)))

    val result = GlyphTraceMatcher.evaluate(template, trace)

    assertFalse(result.accepted)
  }

  @Test
  fun `partial trace is rejected even when it follows part of the target`() {
    val template = lineAndCornerTemplate()
    val trace = listOf(listOf(Offset(20f, 30f), Offset(80f, 30f)))

    val result = GlyphTraceMatcher.evaluate(template, trace)

    assertFalse(result.accepted)
    assertTrue(result.coverage < 0.68f)
  }

  @Test
  fun `every separate component such as a dot must be traced`() {
    val body = (20..80).map { Offset(it.toFloat(), 30f) } +
      (31..70).map { Offset(80f, it.toFloat()) }
    val dot = listOf(Offset(50f, 12f), Offset(51f, 12f), Offset(52f, 12f))
    val template = template(body + dot, listOf(body, dot))
    val trace = listOf(listOf(Offset(20f, 30f), Offset(80f, 30f), Offset(80f, 70f)))

    val result = GlyphTraceMatcher.evaluate(template, trace)

    assertFalse(result.accepted)
    assertEquals(1, result.missingComponents)
  }

  @Test
  fun `Arabic target is rendered into a trace template`() {
    val template = GlyphTraceMatcher.createTemplate(
      glyph = "ب",
      canvasWidth = 600,
      canvasHeight = 560,
      textSizePx = 420f
    )

    assertNotNull(template)
    assertTrue(template!!.skeleton.size >= 8)
    assertTrue(template.bitmap.width <= 320)
    assertTrue(template.bitmap.height <= 320)
  }

  @Test
  fun `every repository letter form has a nonempty renderable template`() {
    val expectedPositions = listOf(
      LetterPosition.ISOLATED,
      LetterPosition.INITIAL,
      LetterPosition.MEDIAL,
      LetterPosition.FINAL
    )

    LetterRepository.letters.forEach { letter ->
      assertEquals("${letter.char} must define each position exactly once", expectedPositions,
        letter.forms.map { it.position })
      letter.forms.forEach { form ->
        assertTrue("${letter.char} ${form.position} glyph must not be blank", form.glyph.isNotBlank())
        val template = GlyphTraceMatcher.createTemplate(
          glyph = form.glyph,
          canvasWidth = 600,
          canvasHeight = 560,
          textSizePx = 420f
        )
        assertNotNull("Could not render ${letter.char} ${form.position}: ${form.glyph}", template)
        assertTrue("Template is empty for ${letter.char} ${form.position}", template!!.skeleton.size >= 8)
        template.bitmap.recycle()
      }
    }
  }

  private fun lineAndCornerTemplate(): GlyphTraceTemplate {
    val line = (20..80).map { Offset(it.toFloat(), 30f) }
    val down = (31..70).map { Offset(80f, it.toFloat()) }
    return template(line + down, listOf(line + down))
  }

  private fun template(
    skeleton: List<Offset>,
    components: List<List<Offset>>
  ): GlyphTraceTemplate {
    val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    return GlyphTraceTemplate(
      bitmap = bitmap,
      skeleton = skeleton,
      components = components,
      canvasWidth = 100,
      canvasHeight = 100
    )
  }

}
