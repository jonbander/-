package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.data.repository.UserProgressRepository
import com.example.ui.MainScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.util.SoundHelper

class MainActivity : ComponentActivity() {

  private lateinit var soundHelper: SoundHelper
  private lateinit var progressRepository: UserProgressRepository

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    soundHelper = SoundHelper(this)
    progressRepository = UserProgressRepository.getInstance(this)

    setContent {
      MyApplicationTheme {
        MainScreen(
          progressRepository = progressRepository,
          soundHelper = soundHelper
        )
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    soundHelper.shutdown()
  }
}
