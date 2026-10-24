package com.example.util

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class SoundHelper(context: Context) {

  private var tts: TextToSpeech? = null
  private var isTtsReady = false
  private var toneGen: ToneGenerator? = null
  private val handler = Handler(Looper.getMainLooper())

  init {
    try {
      toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 85)
    } catch (e: Exception) {
      Log.w("SoundHelper", "Failed to initialize ToneGenerator", e)
    }

    try {
      tts = TextToSpeech(context.applicationContext) { status ->
        if (status == TextToSpeech.SUCCESS) {
          configureTtsLanguage()
        }
      }
    } catch (e: Exception) {
      Log.w("SoundHelper", "Failed to initialize TextToSpeech", e)
    }
  }

  private fun configureTtsLanguage() {
    val engine = tts ?: return
    val arabicResult = engine.setLanguage(Locale("ar"))
    val languageResult = if (
      arabicResult == TextToSpeech.LANG_MISSING_DATA ||
      arabicResult == TextToSpeech.LANG_NOT_SUPPORTED
    ) {
      Log.w("SoundHelper", "Arabic TTS is unavailable; trying the device language")
      val deviceLocale = Locale.getDefault().takeIf { it.language != "ar" } ?: Locale.US
      engine.setLanguage(deviceLocale)
    } else {
      arabicResult
    }

    if (languageResult < TextToSpeech.LANG_AVAILABLE) {
      isTtsReady = false
      Log.w("SoundHelper", "No supported TTS language is available")
      return
    }

    engine.setSpeechRate(0.85f) // Slightly slower and clearer for young learners
    engine.setPitch(1.1f) // Friendly, cheerful higher pitch
    isTtsReady = true
  }

  fun speak(text: String) {
    if (isTtsReady) {
      tts?.stop()
      tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts_${System.currentTimeMillis()}")
    }
  }

  fun playClick() {
    try {
      toneGen?.startTone(ToneGenerator.TONE_PROP_BEEP, 70)
    } catch (_: Exception) {}
  }

  fun playSuccess() {
    try {
      toneGen?.startTone(ToneGenerator.TONE_PROP_ACK, 140)
    } catch (_: Exception) {}
  }

  fun playStarChime() {
    try {
      toneGen?.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 180)
    } catch (_: Exception) {}
  }

  fun playCelebration() {
    try {
      toneGen?.startTone(ToneGenerator.TONE_PROP_ACK, 120)
      handler.postDelayed({
        toneGen?.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200)
      }, 130)
    } catch (_: Exception) {}
  }

  fun shutdown() {
    try {
      tts?.stop()
      tts?.shutdown()
      toneGen?.release()
    } catch (_: Exception) {}
  }
}
