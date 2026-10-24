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
          val result = tts?.setLanguage(Locale("ar"))
          if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.w("SoundHelper", "Arabic TTS not supported or missing data, falling back to default")
            tts?.language = Locale.getDefault()
          } else {
            tts?.setSpeechRate(0.85f) // Slightly slower and clearer for 5-year-olds
            tts?.setPitch(1.1f)      // Friendly, cheerful higher pitch
            isTtsReady = true
          }
        }
      }
    } catch (e: Exception) {
      Log.w("SoundHelper", "Failed to initialize TextToSpeech", e)
    }
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
