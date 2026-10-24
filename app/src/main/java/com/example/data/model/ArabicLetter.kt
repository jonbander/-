package com.example.data.model

import androidx.compose.ui.geometry.Offset

enum class LetterPosition(val arabicName: String, val description: String) {
  ISOLATED("منفصل", "شكل الحرف بمفرده"),
  INITIAL("أول الكلمة", "شكل الحرف في بداية الكلمة"),
  MEDIAL("وسط الكلمة", "شكل الحرف في وسط الكلمة"),
  FINAL("آخر الكلمة", "شكل الحرف في نهاية الكلمة")
}

data class GuidePoint(
  val x: Float, // Normalized 0.0f .. 1.0f
  val y: Float, // Normalized 0.0f .. 1.0f
  val order: Int = 1,
  val isStart: Boolean = false,
  val isDot: Boolean = false
)

data class LetterFormDetail(
  val position: LetterPosition,
  val glyph: String,
  val exampleWord: String,
  val exampleHighlighted: String,
  val exampleEmoji: String,
  val exampleMeaning: String,
  val guideDescription: String,
  val guidePoints: List<GuidePoint> = emptyList()
)

enum class VowelType(val title: String) {
  SHORT("المد القصير (الحركات)"),
  LONG("المد الطويل (حروف المد)")
}

data class VowelDetail(
  val title: String,
  val vowelGlyph: String,
  val exampleWord: String,
  val exampleEmoji: String,
  val type: VowelType,
  val audioPrompt: String,
  val explanation: String
)

data class VowelComparison(
  val title: String,
  val shortVowel: VowelDetail,
  val longVowel: VowelDetail,
  val contrastHint: String
)

data class ArabicLetter(
  val id: Int,
  val char: String,
  val name: String,
  val phoneticName: String,
  val emoji: String,
  val colorHex: Long,
  val forms: List<LetterFormDetail>,
  val shortVowels: List<VowelDetail>,
  val longVowels: List<VowelDetail>,
  val comparisons: List<VowelComparison>
)

data class StickerItem(
  val id: String,
  val letter: String,
  val name: String,
  val emoji: String,
  val starsRequired: Int,
  val isUnlocked: Boolean = false
)
