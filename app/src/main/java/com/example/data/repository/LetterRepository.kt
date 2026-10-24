package com.example.data.repository

import com.example.data.model.ArabicLetter
import com.example.data.model.GuidePoint
import com.example.data.model.LetterFormDetail
import com.example.data.model.LetterPosition
import com.example.data.model.StickerItem
import com.example.data.model.VowelComparison
import com.example.data.model.VowelDetail
import com.example.data.model.VowelType

object LetterRepository {

  val letters: List<ArabicLetter> = listOf(
    createLetter(
      id = 1,
      char = "أ",
      name = "أَلِف",
      phonetic = "ألف",
      emoji = "🦁",
      colorHex = 0xFFFF6B6B,
      forms = listOf(
        form(LetterPosition.ISOLATED, "أ", "أَرْنَب", "أَرْنَب", "🐰", "أرنب يقفز في المزرعة", "عصا مستقيمة من أعلى لأسفل وعليها همزة", listOf(GuidePoint(0.5f, 0.2f, 1, isStart = true), GuidePoint(0.5f, 0.45f, 2), GuidePoint(0.5f, 0.75f, 3))),
        form(LetterPosition.INITIAL, "أَ", "أَسَد", "أَسَد", "🦁", "أسد شجاع ملك الغابة", "خط مستقيم للأسفل مع همزة بالفتحة", listOf(GuidePoint(0.5f, 0.2f, 1, isStart = true), GuidePoint(0.5f, 0.75f, 2))),
        form(LetterPosition.MEDIAL, "ـأ", "فَأْس", "فَـأْس", "🪓", "فأس الفلاح النشيط", "خط على السطر ثم نصعد للأعلى للهمزة", listOf(GuidePoint(0.8f, 0.75f, 1, isStart = true), GuidePoint(0.5f, 0.75f, 2), GuidePoint(0.5f, 0.3f, 3))),
        form(LetterPosition.FINAL, "ـأ", "نَبَأ", "نَبَـأ", "📜", "نبأ وخبر جميل", "يتصل من اليمين ويصعد للأعلى", listOf(GuidePoint(0.8f, 0.75f, 1, isStart = true), GuidePoint(0.5f, 0.75f, 2), GuidePoint(0.5f, 0.25f, 3)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "أَ", "أَسَد", "🦁", VowelType.SHORT, "أَ.. أسد", "صوت قصير نفتح فيه الشفتين"),
        vowel("الضمة", "أُ", "أُذُن", "👂", VowelType.SHORT, "أُ.. أذن", "صوت قصير نضم فيه الشفتين"),
        vowel("الكسرة", "إِ", "إِبْرِيق", "🫖", VowelType.SHORT, "إِ.. إبريق", "صوت قصير نبتسم ونخفض الشفتين"),
        vowel("السكون", "أْ", "رَأْس", "👤", VowelType.SHORT, "أْ.. رأس", "صوت ساكن هادئ وثابت")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "آ", "آمَال", "✨", VowelType.LONG, "آ.. آمال", "مد طويل نمد الصوت بالألف"),
        vowel("مد بالواو", "أُو", "أُولَى", "🥇", VowelType.LONG, "أُو.. أولى", "مد طويل مع الواو نضم ونمد"),
        vowel("مد بالياء", "إِي", "إِيمَان", "🌟", VowelType.LONG, "إِي.. إيمان", "مد طويل مع الياء نكسر ونمد")
      )
    ),
    createLetter(
      id = 2,
      char = "ب",
      name = "بَاء",
      phonetic = "باء",
      emoji = "🦆",
      colorHex = 0xFF4ECDC4,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ب", "كِتَاب", "كِتَا ب", "📚", "كتاب مفيد نتعلم منه", "صحن جميل مع نقطة واحدة تحته", listOf(GuidePoint(0.8f, 0.5f, 1, isStart = true), GuidePoint(0.8f, 0.7f, 2), GuidePoint(0.2f, 0.7f, 3), GuidePoint(0.2f, 0.5f, 4), GuidePoint(0.5f, 0.85f, 5, isDot = true))),
        form(LetterPosition.INITIAL, "بـ", "بَطَّة", "بَـطَّة", "🦆", "بطة صفراء تسبح في البحيرة", "سن صغير على السطر يمد يده لليسار", listOf(GuidePoint(0.75f, 0.5f, 1, isStart = true), GuidePoint(0.75f, 0.75f, 2), GuidePoint(0.25f, 0.75f, 3), GuidePoint(0.5f, 0.9f, 4, isDot = true))),
        form(LetterPosition.MEDIAL, "ـبـ", "حَبْل", "حَـبْـل", "🪢", "حبل قوي للقفز واللعب", "يمد يده من اليمين بسن صغير لليسار", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.5f, 0.75f, 2), GuidePoint(0.5f, 0.5f, 3), GuidePoint(0.5f, 0.75f, 4), GuidePoint(0.15f, 0.75f, 5), GuidePoint(0.5f, 0.9f, 6, isDot = true))),
        form(LetterPosition.FINAL, "ـب", "عِنَب", "عِنَـب", "🍇", "عنب لذيذ وحلو المذاق", "يمد يده ويقفل الصحن مع النقطة", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.7f, 0.75f, 2), GuidePoint(0.25f, 0.75f, 3), GuidePoint(0.25f, 0.5f, 4), GuidePoint(0.5f, 0.9f, 5, isDot = true)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "بَ", "بَقَرَة", "🐮", VowelType.SHORT, "بَ.. بقرة", "صوت الفتحة بَ"),
        vowel("الضمة", "بُ", "بُرْتُقَال", "🍊", VowelType.SHORT, "بُ.. برتقال", "صوت الضمة بُ"),
        vowel("الكسرة", "بِ", "بِنْت", "👧", VowelType.SHORT, "بِ.. بنت", "صوت الكسرة بِ"),
        vowel("السكون", "بْ", "خُبْز", "🍞", VowelType.SHORT, "بْ.. خبز", "صوت السكون بْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "بَا", "بَاب", "🚪", VowelType.LONG, "بَا.. باب", "نمد الفتحة ليصبح بَا طويل"),
        vowel("مد بالواو", "بُو", "بُومَة", "🦉", VowelType.LONG, "بُو.. بومة", "نمد الضمة ليصبح بُو طويل"),
        vowel("مد بالياء", "بِي", "طَبِيب", "🩺", VowelType.LONG, "بِي.. طبيب", "نمد الكسرة ليصبح بِي طويل")
      )
    ),
    createLetter(
      id = 3,
      char = "ت",
      name = "تَاء",
      phonetic = "تاء",
      emoji = "🍎",
      colorHex = 0xFFFFBE0B,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ت", "نَبَات", "نَبَا ت", "🌱", "نبات أخضر ينمو بالشمس", "صحن جميل وله نقطتان في الأعلى", listOf(GuidePoint(0.8f, 0.5f, 1, isStart = true), GuidePoint(0.8f, 0.75f, 2), GuidePoint(0.2f, 0.75f, 3), GuidePoint(0.2f, 0.5f, 4), GuidePoint(0.43f, 0.4f, 5, isDot = true), GuidePoint(0.57f, 0.4f, 6, isDot = true))),
        form(LetterPosition.INITIAL, "تـ", "تُفَّاح", "تُـفَّاح", "🍎", "تفاح أحمر لذيذ ومقرمش", "سن على السطر يمد يده ونقطتان فوق", listOf(GuidePoint(0.75f, 0.5f, 1, isStart = true), GuidePoint(0.75f, 0.75f, 2), GuidePoint(0.25f, 0.75f, 3), GuidePoint(0.65f, 0.38f, 4, isDot = true), GuidePoint(0.78f, 0.38f, 5, isDot = true))),
        form(LetterPosition.MEDIAL, "ـتـ", "دَفْتَر", "دَفْـتَـر", "📓", "دفتر الرسم والكتابة", "يمد يديه الاثنتين ونقطتان فوقه", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.5f, 0.75f, 2), GuidePoint(0.5f, 0.5f, 3), GuidePoint(0.5f, 0.75f, 4), GuidePoint(0.15f, 0.75f, 5), GuidePoint(0.45f, 0.38f, 6, isDot = true), GuidePoint(0.55f, 0.38f, 7, isDot = true))),
        form(LetterPosition.FINAL, "ـت", "بَيْت", "بَيْـت", "🏠", "بيت جميل دافئ مع العائلة", "يمد يده ويغلق الصحن ونقطتان فوق", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.75f, 0.75f, 2), GuidePoint(0.25f, 0.75f, 3), GuidePoint(0.25f, 0.5f, 4), GuidePoint(0.45f, 0.4f, 5, isDot = true), GuidePoint(0.55f, 0.4f, 6, isDot = true)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "تَ", "تَمْر", "🌴", VowelType.SHORT, "تَ.. تمر", "صوت الفتحة تَ"),
        vowel("الضمة", "تُ", "تُفَّاح", "🍎", VowelType.SHORT, "تُ.. تفاح", "صوت الضمة تُ"),
        vowel("الكسرة", "تِ", "تِمْسَاح", "🐊", VowelType.SHORT, "تِ.. تمساح", "صوت الكسرة تِ"),
        vowel("السكون", "تْ", "كَتْكُوت", "🐥", VowelType.SHORT, "تْ.. كتكوت", "صوت السكون تْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "تَا", "تَاج", "👑", VowelType.LONG, "تَا.. تاج", "مد بالألف نمد الصوت تَـا"),
        vowel("مد بالواو", "تُو", "تُوت", "🫐", VowelType.LONG, "تُو.. توت", "مد بالواو نمد الصوت تُـو"),
        vowel("مد بالياء", "تِي", "تِين", "🍈", VowelType.LONG, "تِي.. تين", "مد بالياء نمد الصوت تِـي")
      )
    ),
    createLetter(
      id = 4,
      char = "ث",
      name = "ثَاء",
      phonetic = "ثاء",
      emoji = "🦊",
      colorHex = 0xFFFB5607,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ث", "أَثَاث", "أَثَا ث", "🛋️", "أثاث البيت المرتب", "صحن جميل مع ثلاث نقاط كالمثلث فوق", listOf(GuidePoint(0.8f, 0.5f, 1, isStart = true), GuidePoint(0.8f, 0.75f, 2), GuidePoint(0.2f, 0.75f, 3), GuidePoint(0.2f, 0.5f, 4))),
        form(LetterPosition.INITIAL, "ثـ", "ثَعْلَب", "ثَـعْلَب", "🦊", "ثعلب ذكي وسريع", "سن على السطر مع ثلاث نقاط بأعلاه", listOf(GuidePoint(0.75f, 0.5f, 1, isStart = true), GuidePoint(0.75f, 0.75f, 2), GuidePoint(0.25f, 0.75f, 3))),
        form(LetterPosition.MEDIAL, "ـثـ", "مُثَلَّث", "مُـثَـلَّث", "🔺", "مثلث ذو ثلاثة أضلاع", "يمد يديه لليمين واليسار مع ثلاث نقاط", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.5f, 0.75f, 2), GuidePoint(0.5f, 0.5f, 3), GuidePoint(0.15f, 0.75f, 4))),
        form(LetterPosition.FINAL, "ـث", "غَيْث", "غَيْـث", "🌧️", "غيث ومطر يروي الأرض", "يتصل ويغلق الصحن ومعه ثلاث نقاط", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.75f, 0.75f, 2), GuidePoint(0.25f, 0.75f, 3), GuidePoint(0.25f, 0.5f, 4)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "ثَ", "ثَوْب", "👗", VowelType.SHORT, "ثَ.. ثوب", "صوت الفتحة ثَ مخرج اللسان بلطف"),
        vowel("الضمة", "ثُ", "ثُعْبَان", "🐍", VowelType.SHORT, "ثُ.. ثعبان", "صوت الضمة ثُ"),
        vowel("الكسرة", "ثِ", "ثِيَاب", "👕", VowelType.SHORT, "ثِ.. ثياب", "صوت الكسرة ثِ"),
        vowel("السكون", "ثْ", "عُثْمَان", "👦", VowelType.SHORT, "ثْ.. عثمان", "صوت السكون ثْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "ثَا", "ثَابِت", "⚓", VowelType.LONG, "ثَا.. ثابت", "مد بالألف ثَـا"),
        vowel("مد بالواو", "ثُو", "ثُوم", "🧄", VowelType.LONG, "ثُو.. ثوم", "مد بالواو ثُـو"),
        vowel("مد بالياء", "ثِي", "ثِيرَان", "🐂", VowelType.LONG, "ثِي.. ثيران", "مد بالياء ثِـي")
      )
    ),
    createLetter(
      id = 5,
      char = "ج",
      name = "جِيم",
      phonetic = "جيم",
      emoji = "🐪",
      colorHex = 0xFF3A86FF,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ج", "بُرْج", "بُرْ ج", "🗼", "برج عال في السماء", "رأس متموج وبطن كبير بداخله نقطة", listOf(GuidePoint(0.4f, 0.35f, 1, isStart = true), GuidePoint(0.7f, 0.35f, 2), GuidePoint(0.5f, 0.5f, 3), GuidePoint(0.25f, 0.75f, 4), GuidePoint(0.6f, 0.85f, 5), GuidePoint(0.5f, 0.65f, 6, isDot = true))),
        form(LetterPosition.INITIAL, "جـ", "جَمَل", "جَـمَل", "🐪", "جمل صبور سفينة الصحراء", "رأس الجيم مائل ثم يمد يده على السطر مع نقطة تحته", listOf(GuidePoint(0.5f, 0.45f, 1, isStart = true), GuidePoint(0.8f, 0.45f, 2), GuidePoint(0.65f, 0.75f, 3), GuidePoint(0.25f, 0.75f, 4), GuidePoint(0.5f, 0.9f, 5, isDot = true))),
        form(LetterPosition.MEDIAL, "ـجـ", "شَجَرَة", "شَـجَـرَة", "🌳", "شجرة خضراء وارفة الظلال", "يتصل من اليمين ويرتفع لرأس الجيم ويمتد لليسار", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.65f, 0.75f, 2), GuidePoint(0.75f, 0.5f, 3), GuidePoint(0.5f, 0.5f, 4), GuidePoint(0.2f, 0.75f, 5), GuidePoint(0.5f, 0.9f, 6, isDot = true))),
        form(LetterPosition.FINAL, "ـج", "تَاج", "تَا ج", "👑", "تاج مرصع بالجواهر", "يتصل من اليمين ثم ينزل بالبطن الكبير", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.6f, 0.75f, 2), GuidePoint(0.7f, 0.45f, 3), GuidePoint(0.3f, 0.75f, 4), GuidePoint(0.6f, 0.85f, 5), GuidePoint(0.5f, 0.68f, 6, isDot = true)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "جَ", "جَمَل", "🐪", VowelType.SHORT, "جَ.. جمل", "صوت الفتحة جَ"),
        vowel("الضمة", "جُ", "جُبْن", "🧀", VowelType.SHORT, "جُ.. جبن", "صوت الضمة جُ"),
        vowel("الكسرة", "جِ", "جِسْر", "🌉", VowelType.SHORT, "جِ.. جسر", "صوت الكسرة جِ"),
        vowel("السكون", "جْ", "نَجْم", "⭐", VowelType.SHORT, "جْ.. نجم", "صوت السكون جْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "جَا", "جَار", "🏡", VowelType.LONG, "جَا.. جار", "مد بالألف جَـا"),
        vowel("مد بالواو", "جُو", "جُودِي", "👧", VowelType.LONG, "جُو.. جودي", "مد بالواو جُـو"),
        vowel("مد بالياء", "جِي", "جِيرَان", "🤝", VowelType.LONG, "جِي.. جيران", "مد بالياء جِـي")
      )
    ),
    createLetter(
      id = 6,
      char = "ح",
      name = "حَاء",
      phonetic = "حاء",
      emoji = "🐴",
      colorHex = 0xFF8338EC,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ح", "تِمْسَاح", "تِمْسَا ح", "🐊", "تمساح في النهر", "رأس متموج وبطن كبير بدون أي نقاط", listOf(GuidePoint(0.4f, 0.35f, 1, isStart = true), GuidePoint(0.7f, 0.35f, 2), GuidePoint(0.5f, 0.5f, 3), GuidePoint(0.25f, 0.75f, 4), GuidePoint(0.6f, 0.85f, 5))),
        form(LetterPosition.INITIAL, "حـ", "حِصَان", "حِـصَان", "🐴", "حصان رشيق وأصيل", "رأس الحاء يمد يده على السطر", listOf(GuidePoint(0.5f, 0.45f, 1, isStart = true), GuidePoint(0.8f, 0.45f, 2), GuidePoint(0.65f, 0.75f, 3), GuidePoint(0.25f, 0.75f, 4))),
        form(LetterPosition.MEDIAL, "ـحـ", "بَحْر", "بَـحْـر", "🌊", "بحر أزرق وأمواج هادئة", "يتصل من اليمين واليسار بدون نقاط", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.65f, 0.75f, 2), GuidePoint(0.75f, 0.5f, 3), GuidePoint(0.5f, 0.5f, 4), GuidePoint(0.2f, 0.75f, 5))),
        form(LetterPosition.FINAL, "ـح", "مِلْح", "مِلْـح", "🧂", "ملح الطعام الأبيض", "يتصل ويختم بالبطن الكبير الجميل", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.6f, 0.75f, 2), GuidePoint(0.7f, 0.45f, 3), GuidePoint(0.3f, 0.75f, 4), GuidePoint(0.6f, 0.85f, 5)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "حَ", "حَلِيب", "🥛", VowelType.SHORT, "حَ.. حليب", "صوت الفتحة حَ هواء دافئ"),
        vowel("الضمة", "حُ", "حُوت", "🐋", VowelType.SHORT, "حُ.. حوت", "صوت الضمة حُ"),
        vowel("الكسرة", "حِ", "حِصَان", "🐴", VowelType.SHORT, "حِ.. حصان", "صوت الكسرة حِ"),
        vowel("السكون", "حْ", "لَحْم", "🥩", VowelType.SHORT, "حْ.. لحم", "صوت السكون حْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "حَا", "حَافِلَة", "🚌", VowelType.LONG, "حَا.. حافلة", "مد بالألف حَـا"),
        vowel("مد بالواو", "حُو", "حُوت", "🐋", VowelType.LONG, "حُو.. حوت", "مد بالواو حُـو"),
        vowel("مد بالياء", "حِي", "حَدِيقَة", "🏡", VowelType.LONG, "حِي.. حديقة", "مد بالياء حِـي")
      )
    ),
    createLetter(
      id = 7,
      char = "خ",
      name = "خَاء",
      phonetic = "خاء",
      emoji = "🐑",
      colorHex = 0xFF06D6A0,
      forms = listOf(
        form(LetterPosition.ISOLATED, "خ", "صَارُوخ", "صَارُو خ", "🚀", "صاروخ ينطلق نحو الفضاء", "مثل الحاء مع نقطة واحدة فوق الرأس", listOf(GuidePoint(0.4f, 0.35f, 1, isStart = true), GuidePoint(0.7f, 0.35f, 2), GuidePoint(0.5f, 0.5f, 3), GuidePoint(0.6f, 0.85f, 4), GuidePoint(0.55f, 0.2f, 5, isDot = true))),
        form(LetterPosition.INITIAL, "خـ", "خَرُوف", "خَـرُوف", "🐑", "خروف أبيض لطيف وفروه ناعم", "يمد يده على السطر والنقطة فوق الرأس", listOf(GuidePoint(0.5f, 0.45f, 1, isStart = true), GuidePoint(0.8f, 0.45f, 2), GuidePoint(0.65f, 0.75f, 3), GuidePoint(0.25f, 0.75f, 4), GuidePoint(0.65f, 0.28f, 5, isDot = true))),
        form(LetterPosition.MEDIAL, "ـخـ", "نَخْلَة", "نَـخْـلَة", "🌴", "نخلة تعطينا أطيب التمر", "يتصل من الجهتين ونقطة تعلو رأسه", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.65f, 0.75f, 2), GuidePoint(0.75f, 0.5f, 3), GuidePoint(0.2f, 0.75f, 4), GuidePoint(0.6f, 0.3f, 5, isDot = true))),
        form(LetterPosition.FINAL, "ـخ", "بَطِّيخ", "بَطِّيـخ", "🍉", "بطيخ بارد ومنعش في الصيف", "يتصل بالكلمة وينتهي ببطنه الكبير والنقطة فوق", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.6f, 0.75f, 2), GuidePoint(0.7f, 0.45f, 3), GuidePoint(0.6f, 0.85f, 4), GuidePoint(0.6f, 0.25f, 5, isDot = true)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "خَ", "خَرُوف", "🐑", VowelType.SHORT, "خَ.. خروف", "صوت الفتحة خَ"),
        vowel("الضمة", "خُ", "خُبْز", "🍞", VowelType.SHORT, "خُ.. خبز", "صوت الضمة خُ"),
        vowel("الكسرة", "خِ", "خِيَار", "🥒", VowelType.SHORT, "خِ.. خيار", "صوت الكسرة خِ"),
        vowel("السكون", "خْ", "نَخْل", "🌴", VowelType.SHORT, "خْ.. نخل", "صوت السكون خْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "خَا", "خَاتَم", "💍", VowelType.LONG, "خَا.. خاتم", "مد بالألف خَـا"),
        vowel("مد بالواو", "خُو", "خُوخ", "🍑", VowelType.LONG, "خُو.. خوخ", "مد بالواو خُـو"),
        vowel("مد بالياء", "خِي", "نَخِيل", "🌴", VowelType.LONG, "خِي.. نخيل", "مد بالياء خِـي")
      )
    ),
    createLetter(
      id = 8,
      char = "د",
      name = "دَال",
      phonetic = "دال",
      emoji = "🐻",
      colorHex = 0xFFFF7A00,
      forms = listOf(
        form(LetterPosition.ISOLATED, "د", "وَرْد", "وَرْ د", "🌹", "ورد أحمر يفوح بالعطر", "قوس صغير يبدأ من أعلى وينتهي على السطر", listOf(GuidePoint(0.65f, 0.45f, 1, isStart = true), GuidePoint(0.65f, 0.75f, 2), GuidePoint(0.35f, 0.75f, 3))),
        form(LetterPosition.INITIAL, "د", "دُرَّاق", "دُرَّاق", "🍑", "دراق طازج ومغذي", "في أول الكلمة لا يتصل بما بعده", listOf(GuidePoint(0.65f, 0.45f, 1, isStart = true), GuidePoint(0.65f, 0.75f, 2), GuidePoint(0.35f, 0.75f, 3))),
        form(LetterPosition.MEDIAL, "ـد", "هُدْهُد", "هُـدْ هُد", "🐦", "هدهد ملون جميل الصوت", "يمد يده من اليمين ويرتفع سنه الصغير", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.55f, 0.75f, 2), GuidePoint(0.55f, 0.45f, 3), GuidePoint(0.35f, 0.75f, 4))),
        form(LetterPosition.FINAL, "ـد", "يَد", "يَـد", "✋", "يد نظيفة نكتب ونرسم بها", "يتصل بما قبله فقط", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.55f, 0.75f, 2), GuidePoint(0.55f, 0.45f, 3), GuidePoint(0.35f, 0.75f, 4)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "دَ", "دَرَاجَة", "🚲", VowelType.SHORT, "دَ.. دراجة", "صوت الفتحة دَ"),
        vowel("الضمة", "دُ", "دُبّ", "🐻", VowelType.SHORT, "دُ.. دب", "صوت الضمة دُ"),
        vowel("الكسرة", "دِ", "دِرْع", "🛡️", VowelType.SHORT, "دِ.. درع", "صوت الكسرة دِ"),
        vowel("السكون", "دْ", "بَدْر", "🌕", VowelType.SHORT, "دْ.. بدر", "صوت السكون دْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "دَا", "دَار", "🏡", VowelType.LONG, "دَا.. دار", "مد بالألف دَا"),
        vowel("مد بالواو", "دُو", "دُولاب", "🚪", VowelType.LONG, "دُو.. دولاب", "مد بالواو دُو"),
        vowel("مد بالياء", "دِي", "دِيك", "🐓", VowelType.LONG, "دِي.. ديك", "مد بالياء دِي")
      )
    ),
    createLetter(
      id = 9,
      char = "ذ",
      name = "ذَال",
      phonetic = "ذال",
      emoji = "🌽",
      colorHex = 0xFF2EC4B6,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ذ", "رَذَاذ", "رَذَا ذ", "🌧️", "رذاذ المطر اللطيف", "مثل الدال تماماً مع نقطة واحدة فوقه", listOf(GuidePoint(0.65f, 0.45f, 1, isStart = true), GuidePoint(0.65f, 0.75f, 2), GuidePoint(0.35f, 0.75f, 3), GuidePoint(0.55f, 0.3f, 4, isDot = true))),
        form(LetterPosition.INITIAL, "ذ", "ذِئْب", "ذِئْب", "🐺", "ذئب يعيش في البرية", "في أول الكلمة لا يمسك بالحرف الذي بعده", listOf(GuidePoint(0.65f, 0.45f, 1, isStart = true), GuidePoint(0.65f, 0.75f, 2), GuidePoint(0.35f, 0.75f, 3), GuidePoint(0.55f, 0.3f, 4, isDot = true))),
        form(LetterPosition.MEDIAL, "ـذ", "جَذْر", "جَـذْ ر", "🥕", "جذر النبتة تحت التراب", "يمد يده من اليمين والنقطة فوق رأسه", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.55f, 0.75f, 2), GuidePoint(0.55f, 0.45f, 3), GuidePoint(0.35f, 0.75f, 4), GuidePoint(0.55f, 0.3f, 5, isDot = true))),
        form(LetterPosition.FINAL, "ـذ", "قُنْفُذ", "قُنْفُـذ", "🦔", "قنفذ صغير وشوكه يحميه", "يتصل بالحرف السابق والنقطة تضيء فوقه", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.55f, 0.75f, 2), GuidePoint(0.55f, 0.45f, 3), GuidePoint(0.35f, 0.75f, 4), GuidePoint(0.55f, 0.3f, 5, isDot = true)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "ذَ", "ذَهَب", "✨", VowelType.SHORT, "ذَ.. ذهب", "صوت الفتحة ذَ مع إخراج اللسان رقيقاً"),
        vowel("الضمة", "ذُ", "ذُرَة", "🌽", VowelType.SHORT, "ذُ.. ذرة", "صوت الضمة ذُ"),
        vowel("الكسرة", "ذِ", "ذِئْب", "🐺", VowelType.SHORT, "ذِ.. ذئب", "صوت الكسرة ذِ"),
        vowel("السكون", "ذْ", "جَذْر", "🥕", VowelType.SHORT, "ذْ.. جذر", "صوت السكون ذْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "ذَا", "ذَاكِر", "🧠", VowelType.LONG, "ذَا.. ذاكر", "مد بالألف ذَا"),
        vowel("مد بالواو", "ذُو", "بُذُور", "🌱", VowelType.LONG, "ذُو.. بذور", "مد بالواو ذُو"),
        vowel("مد بالياء", "ذِي", "مُذِيع", "🎙️", VowelType.LONG, "ذِي.. مذيع", "مد بالياء ذِي")
      )
    ),
    createLetter(
      id = 10,
      char = "ر",
      name = "رَاء",
      phonetic = "راء",
      emoji = "🚀",
      colorHex = 0xFFFF4081,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ر", "نَار", "نَا ر", "🔥", "نار دافئة في المخيم", "سن صغير ينزلق كهلال تحت السطر", listOf(GuidePoint(0.6f, 0.45f, 1, isStart = true), GuidePoint(0.55f, 0.65f, 2), GuidePoint(0.3f, 0.85f, 3))),
        form(LetterPosition.INITIAL, "ر", "رَمْل", "رَمْل", "🏖️", "رمل ناعم نبني به القلاع", "في أول الكلمة لا يمسك بما بعده", listOf(GuidePoint(0.6f, 0.45f, 1, isStart = true), GuidePoint(0.55f, 0.65f, 2), GuidePoint(0.3f, 0.85f, 3))),
        form(LetterPosition.MEDIAL, "ـر", "مَرْكَب", "مَـرْ كَب", "⛵", "مركب يبحر في البحر", "يتصل من اليمين ثم ينزلق تحت السطر", listOf(GuidePoint(0.85f, 0.65f, 1, isStart = true), GuidePoint(0.55f, 0.65f, 2), GuidePoint(0.3f, 0.85f, 3))),
        form(LetterPosition.FINAL, "ـر", "قَمَر", "قَمَـر", "🌙", "قمر مضيء ينير الليل", "يتصل بالحرف قبله ويزحلق كالمزلقة", listOf(GuidePoint(0.85f, 0.65f, 1, isStart = true), GuidePoint(0.55f, 0.65f, 2), GuidePoint(0.3f, 0.85f, 3)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "رَ", "رَجُل", "👨", VowelType.SHORT, "رَ.. رجل", "صوت الفتحة رَ"),
        vowel("الضمة", "رُ", "رُمَّان", "🍎", VowelType.SHORT, "رُ.. رمان", "صوت الضمة رُ"),
        vowel("الكسرة", "رِ", "رِيشَة", "🪶", VowelType.SHORT, "رِ.. ريشة", "صوت الكسرة رِ"),
        vowel("السكون", "رْ", "بَرْد", "❄️", VowelType.SHORT, "رْ.. برد", "صوت السكون رْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "رَا", "رَامِي", "🏹", VowelType.LONG, "رَا.. رامي", "مد بالألف رَا"),
        vowel("مد بالواو", "رُو", "خَرُوف", "🐑", VowelType.LONG, "رُو.. خروف", "مد بالواو رُو"),
        vowel("مد بالياء", "رِي", "رِيع", "🌾", VowelType.LONG, "رِي.. ريع", "مد بالياء رِي")
      )
    ),
    createLetter(
      id = 11,
      char = "ز",
      name = "زَاي",
      phonetic = "زاي",
      emoji = "🦒",
      colorHex = 0xFFFFD166,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ز", "كَنْز", "كَنْ ز", "🪙", "كنز ثمين مليء بالذهب", "مثل الراء مع نقطة تلمع فوق رأسه", listOf(GuidePoint(0.6f, 0.45f, 1, isStart = true), GuidePoint(0.55f, 0.65f, 2), GuidePoint(0.3f, 0.85f, 3), GuidePoint(0.58f, 0.3f, 4, isDot = true))),
        form(LetterPosition.INITIAL, "ز", "زَرَافَة", "زَرَافَة", "🦒", "زرافة طويلة رقبتها تصل الأشجار", "يقف بالبداية ونقطة واحدة تعلوه", listOf(GuidePoint(0.6f, 0.45f, 1, isStart = true), GuidePoint(0.55f, 0.65f, 2), GuidePoint(0.3f, 0.85f, 3), GuidePoint(0.58f, 0.3f, 4, isDot = true))),
        form(LetterPosition.MEDIAL, "ـز", "مَزْرَعَة", "مَـزْ رَعَة", "🚜", "مزرعة الحيوانات والزرع", "يمد يده من اليمين وينزلق مع نقطته", listOf(GuidePoint(0.85f, 0.65f, 1, isStart = true), GuidePoint(0.55f, 0.65f, 2), GuidePoint(0.3f, 0.85f, 3), GuidePoint(0.55f, 0.3f, 4, isDot = true))),
        form(LetterPosition.FINAL, "ـز", "مَوْز", "مَوْ ز", "🍌", "موز أصفر ومفيد للصحة", "يتصل بالحرف قبله وتتوج النقطة فوقه", listOf(GuidePoint(0.85f, 0.65f, 1, isStart = true), GuidePoint(0.55f, 0.65f, 2), GuidePoint(0.3f, 0.85f, 3), GuidePoint(0.55f, 0.3f, 4, isDot = true)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "زَ", "زَرَافَة", "🦒", VowelType.SHORT, "زَ.. زرافة", "صوت الفتحة زَ مثل رنين النحلة"),
        vowel("الضمة", "زُ", "زُهُور", "💐", VowelType.SHORT, "زُ.. زهور", "صوت الضمة زُ"),
        vowel("الكسرة", "زِ", "زِرّ", "🔘", VowelType.SHORT, "زِ.. زر", "صوت الكسرة زِ"),
        vowel("السكون", "زْ", "مَزْرَعَة", "🚜", VowelType.SHORT, "زْ.. مزرعة", "صوت السكون زْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "زَا", "زَائِر", "🙋", VowelType.LONG, "زَا.. زائر", "مد بالألف زَا"),
        vowel("مد بالواو", "زُو", "زُورَق", "🛶", VowelType.LONG, "زُو.. زورق", "مد بالواو زُو"),
        vowel("مد بالياء", "زِي", "زِينَة", "🎉", VowelType.LONG, "زِي.. زينة", "مد بالياء زِي")
      )
    ),
    createLetter(
      id = 12,
      char = "س",
      name = "سِين",
      phonetic = "سين",
      emoji = "🐟",
      colorHex = 0xFF00B4D8,
      forms = listOf(
        form(LetterPosition.ISOLATED, "س", "جَرَس", "جَرَ س", "🔔", "جرس المدرسة يدق بمرح", "ثلاثة أسنان صغيرة ثم صحن كبير", listOf(GuidePoint(0.85f, 0.5f, 1, isStart = true), GuidePoint(0.75f, 0.65f, 2), GuidePoint(0.65f, 0.5f, 3), GuidePoint(0.55f, 0.65f, 4), GuidePoint(0.45f, 0.5f, 5), GuidePoint(0.35f, 0.85f, 6), GuidePoint(0.2f, 0.65f, 7))),
        form(LetterPosition.INITIAL, "سـ", "سَمَكَة", "سَـمَكَة", "🐟", "سمكة تسبح في الماء العذب", "ثلاثة أسنان على السطر تمتد لليسار", listOf(GuidePoint(0.85f, 0.55f, 1, isStart = true), GuidePoint(0.75f, 0.75f, 2), GuidePoint(0.65f, 0.55f, 3), GuidePoint(0.55f, 0.75f, 4), GuidePoint(0.45f, 0.55f, 5), GuidePoint(0.2f, 0.75f, 6))),
        form(LetterPosition.MEDIAL, "ـسـ", "مَسْجِد", "مَـسْـجِد", "🕌", "مسجد نصلي فيه بخشوع", "يمد يده من اليمين بثلاثة أسنان لليسار", listOf(GuidePoint(0.9f, 0.75f, 1, isStart = true), GuidePoint(0.7f, 0.75f, 2), GuidePoint(0.6f, 0.55f, 3), GuidePoint(0.5f, 0.75f, 4), GuidePoint(0.4f, 0.55f, 5), GuidePoint(0.15f, 0.75f, 6))),
        form(LetterPosition.FINAL, "ـس", "شَمْس", "شَمْـس", "☀️", "شمس ساطعة تدفئنا", "يمد يده مع ثلاثة أسنان وصحن السين الكبير", listOf(GuidePoint(0.9f, 0.75f, 1, isStart = true), GuidePoint(0.65f, 0.75f, 2), GuidePoint(0.55f, 0.55f, 3), GuidePoint(0.35f, 0.85f, 4), GuidePoint(0.2f, 0.65f, 5)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "سَ", "سَمَكَة", "🐟", VowelType.SHORT, "سَ.. سمكة", "صوت الفتحة سَ هادئ ولطيف"),
        vowel("الضمة", "سُ", "سُلَحْفَاة", "🐢", VowelType.SHORT, "سُ.. سلحفاة", "صوت الضمة سُ"),
        vowel("الكسرة", "سِ", "سِتَار", "🪟", VowelType.SHORT, "سِ.. ستار", "صوت الكسرة سِ"),
        vowel("السكون", "سْ", "مَسْكَن", "🏡", VowelType.SHORT, "سْ.. مسكن", "صوت السكون سْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "سَا", "سَاعَة", "⏰", VowelType.LONG, "سَا.. ساعة", "مد بالألف سَا"),
        vowel("مد بالواو", "سُو", "سُور", "🧱", VowelType.LONG, "سُو.. سور", "مد بالواو سُو"),
        vowel("مد بالياء", "سِي", "سِينَمَا", "🎬", VowelType.LONG, "سِي.. سينما", "مد بالياء سِي")
      )
    ),
    createLetter(
      id = 13,
      char = "ش",
      name = "شِين",
      phonetic = "شين",
      emoji = "☀️",
      colorHex = 0xFFFF7A00,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ش", "فَرَاش", "فَرَا ش", "🦋", "فراش يطير بين الزهور", "ثلاثة أسنان وصحن وثلاث نقاط فوق", listOf(GuidePoint(0.85f, 0.5f, 1, isStart = true), GuidePoint(0.65f, 0.5f, 2), GuidePoint(0.35f, 0.85f, 3), GuidePoint(0.6f, 0.3f, 4, isDot = true))),
        form(LetterPosition.INITIAL, "شـ", "شَمْس", "شَـمْس", "☀️", "شمس ذهبية تملأ الكون نوراً", "ثلاثة أسنان تمتد وثلاث نقاط فوقها", listOf(GuidePoint(0.85f, 0.55f, 1, isStart = true), GuidePoint(0.55f, 0.75f, 2), GuidePoint(0.2f, 0.75f, 3), GuidePoint(0.65f, 0.35f, 4, isDot = true))),
        form(LetterPosition.MEDIAL, "ـشـ", "عُشْب", "عُـشْـب", "🌱", "عشب أخضر رطب", "يمد يديه وثلاث نقاط تعلو أسنانه", listOf(GuidePoint(0.9f, 0.75f, 1, isStart = true), GuidePoint(0.5f, 0.75f, 2), GuidePoint(0.15f, 0.75f, 3), GuidePoint(0.55f, 0.35f, 4, isDot = true))),
        form(LetterPosition.FINAL, "ـش", "رِيش", "رِيـش", "🪶", "ريش الطاووس البديع", "يتصل بصحن الشين مع النقاط الثلاث", listOf(GuidePoint(0.9f, 0.75f, 1, isStart = true), GuidePoint(0.55f, 0.55f, 2), GuidePoint(0.35f, 0.85f, 3), GuidePoint(0.55f, 0.35f, 4, isDot = true)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "شَ", "شَمْس", "☀️", VowelType.SHORT, "شَ.. شمس", "صوت الفتحة شَ"),
        vowel("الضمة", "شُ", "شُرْطِيّ", "👮", VowelType.SHORT, "شُ.. شرطي", "صوت الضمة شُ"),
        vowel("الكسرة", "شِ", "شِرَاع", "⛵", VowelType.SHORT, "شِ.. شراع", "صوت الكسرة شِ"),
        vowel("السكون", "شْ", "مِشْمِش", "🍑", VowelType.SHORT, "شْ.. مشمش", "صوت السكون شْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "شَا", "شَاي", "🫖", VowelType.LONG, "شَا.. شاي", "مد بالألف شَا"),
        vowel("مد بالواو", "شُو", "شُورَبَة", "🍲", VowelType.LONG, "شُو.. شوربة", "مد بالواو شُو"),
        vowel("مد بالياء", "شِي", "نَشِيد", "🎶", VowelType.LONG, "شِي.. نشيد", "مد بالياء شِي")
      )
    ),
    createLetter(
      id = 14,
      char = "ص",
      name = "صَاد",
      phonetic = "صاد",
      emoji = "🦅",
      colorHex = 0xFF7209B7,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ص", "غَوَّاص", "غَوَّا ص", "🤿", "غواص يستكشف أعماق البحر", "قبة الصاد وسن صغير وصحن كبير", listOf(GuidePoint(0.5f, 0.75f, 1, isStart = true), GuidePoint(0.8f, 0.6f, 2), GuidePoint(0.5f, 0.6f, 3), GuidePoint(0.35f, 0.85f, 4))),
        form(LetterPosition.INITIAL, "صـ", "صَقْر", "صَـقْر", "🦅", "صقر قوي يحلق عالياً", "قبة الصاد وسن يمد يده لليسار", listOf(GuidePoint(0.5f, 0.75f, 1, isStart = true), GuidePoint(0.8f, 0.6f, 2), GuidePoint(0.4f, 0.75f, 3), GuidePoint(0.2f, 0.75f, 4))),
        form(LetterPosition.MEDIAL, "ـصـ", "عُصْفُور", "عُـصْـفُور", "🐦", "عصفور يغرد بأجمل الألحان", "يمد يده من اليمين لقبة الصاد واليسار", listOf(GuidePoint(0.9f, 0.75f, 1, isStart = true), GuidePoint(0.7f, 0.6f, 2), GuidePoint(0.4f, 0.75f, 3), GuidePoint(0.15f, 0.75f, 4))),
        form(LetterPosition.FINAL, "ـص", "مَقَصّ", "مَقَـصّ", "✂️", "مقص نقص به الورق الملون", "يتصل بصحن الصاد النازل تحت السطر", listOf(GuidePoint(0.9f, 0.75f, 1, isStart = true), GuidePoint(0.6f, 0.6f, 2), GuidePoint(0.35f, 0.85f, 3)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "صَ", "صَقْر", "🦅", VowelType.SHORT, "صَ.. صقر", "صوت الصاد مفخم قوي صَ"),
        vowel("الضمة", "صُ", "صُنْدُوق", "📦", VowelType.SHORT, "صُ.. صندوق", "صوت الضمة صُ"),
        vowel("الكسرة", "صِ", "صِنَّارَة", "🎣", VowelType.SHORT, "صِ.. صنارة", "صوت الكسرة صِ"),
        vowel("السكون", "صْ", "قَصْر", "🏰", VowelType.SHORT, "صْ.. قصر", "صوت السكون صْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "صَا", "صَابُون", "🧼", VowelType.LONG, "صَا.. صابون", "مد بالألف صَا"),
        vowel("مد بالواو", "صُو", "صُورَة", "🖼️", VowelType.LONG, "صُو.. صورة", "مد بالواو صُو"),
        vowel("مد بالياء", "صِي", "عَصِير", "🧃", VowelType.LONG, "صِي.. عصير", "مد بالياء صِي")
      )
    ),
    createLetter(
      id = 15,
      char = "ض",
      name = "ضَاد",
      phonetic = "ضاد",
      emoji = "🐸",
      colorHex = 0xFF2EC4B6,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ض", "حَوْض", "حَوْ ض", "🛁", "حوض ماء منعش ونظيف", "مثل الصاد مع نقطة مضيئة فوق القبة", listOf(GuidePoint(0.5f, 0.75f, 1, isStart = true), GuidePoint(0.8f, 0.6f, 2), GuidePoint(0.35f, 0.85f, 3), GuidePoint(0.65f, 0.45f, 4, isDot = true))),
        form(LetterPosition.INITIAL, "ضـ", "ضِفْدَع", "ضِـفْدَع", "🐸", "ضفدع أخضر يقفز بالبركة", "قبة وسن على السطر ونقطة فوقه", listOf(GuidePoint(0.5f, 0.75f, 1, isStart = true), GuidePoint(0.8f, 0.6f, 2), GuidePoint(0.2f, 0.75f, 3), GuidePoint(0.65f, 0.45f, 4, isDot = true))),
        form(LetterPosition.MEDIAL, "ـضـ", "خُضَار", "خُـضَـار", "🥕", "خضار طازجة مليئة بالفيتامينات", "يمد يديه والنقطة فوق القبة", listOf(GuidePoint(0.9f, 0.75f, 1, isStart = true), GuidePoint(0.7f, 0.6f, 2), GuidePoint(0.15f, 0.75f, 3), GuidePoint(0.6f, 0.45f, 4, isDot = true))),
        form(LetterPosition.FINAL, "ـض", "بَيْض", "بَيْـض", "🥚", "بيض لذيذ ومفيد للإفطار", "يتصل بصحن الضاد والنقطة فوقه", listOf(GuidePoint(0.9f, 0.75f, 1, isStart = true), GuidePoint(0.6f, 0.6f, 2), GuidePoint(0.35f, 0.85f, 3), GuidePoint(0.6f, 0.45f, 4, isDot = true)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "ضَ", "ضَوْء", "💡", VowelType.SHORT, "ضَ.. ضوء", "حرف الضاد لغة الضاد ضَ"),
        vowel("الضمة", "ضُ", "ضُفْدَع", "🐸", VowelType.SHORT, "ضُ.. ضفدع", "صوت الضمة ضُ"),
        vowel("الكسرة", "ضِ", "ضِرْس", "🦷", VowelType.SHORT, "ضِ.. ضرس", "صوت الكسرة ضِ"),
        vowel("السكون", "ضْ", "أَرْض", "🌍", VowelType.SHORT, "ضْ.. أرض", "صوت السكون ضْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "ضَا", "ضَابِط", "👮", VowelType.LONG, "ضَا.. ضابط", "مد بالألف ضَا"),
        vowel("مد بالواو", "ضُو", "وُضُوء", "💧", VowelType.LONG, "ضُو.. وضوء", "مد بالواو ضُو"),
        vowel("مد بالياء", "ضِي", "مَضِيق", "⛵", VowelType.LONG, "ضِي.. مضيق", "مد بالياء ضِي")
      )
    ),
    createLetter(
      id = 16,
      char = "ط",
      name = "طَاء",
      phonetic = "طاء",
      emoji = "✈️",
      colorHex = 0xFFFB5607,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ط", "خَيْط", "خَيْ ط", "🧵", "خيط حرير ناعم", "قبة بيضاوية وعصا طويلة تنزل عليها", listOf(GuidePoint(0.5f, 0.75f, 1, isStart = true), GuidePoint(0.8f, 0.6f, 2), GuidePoint(0.35f, 0.75f, 3), GuidePoint(0.65f, 0.3f, 4), GuidePoint(0.65f, 0.75f, 5))),
        form(LetterPosition.INITIAL, "طـ", "طَيَّارَة", "طَـيَّارَة", "✈️", "طيارة تحلق فوق السحاب", "قبة وعصا تمد يدها على السطر", listOf(GuidePoint(0.5f, 0.75f, 1, isStart = true), GuidePoint(0.8f, 0.6f, 2), GuidePoint(0.2f, 0.75f, 3), GuidePoint(0.65f, 0.3f, 4))),
        form(LetterPosition.MEDIAL, "ـطـ", "مَطَر", "مَـطَـر", "🌧️", "مطر يسقي الورد والشجر", "يمد يديه من الجهتين مع العصا", listOf(GuidePoint(0.9f, 0.75f, 1, isStart = true), GuidePoint(0.7f, 0.6f, 2), GuidePoint(0.15f, 0.75f, 3), GuidePoint(0.6f, 0.3f, 4))),
        form(LetterPosition.FINAL, "ـط", "بَطّ", "بَـطّ", "🦆", "بط يسبح في البحيرة", "يتصل بالحرف قبله مع عصاه الطويلة", listOf(GuidePoint(0.9f, 0.75f, 1, isStart = true), GuidePoint(0.6f, 0.6f, 2), GuidePoint(0.35f, 0.75f, 3), GuidePoint(0.6f, 0.3f, 4)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "طَ", "طَيْر", "🕊️", VowelType.SHORT, "طَ.. طير", "صوت الفتحة طَ قوي ومفخم"),
        vowel("الضمة", "طُ", "طُيُور", "🦜", VowelType.SHORT, "طُ.. طيور", "صوت الضمة طُ"),
        vowel("الكسرة", "طِ", "طِفْل", "👶", VowelType.SHORT, "طِ.. طفل", "صوت الكسرة طِ"),
        vowel("السكون", "طْ", "مَطْبَخ", "🍳", VowelType.SHORT, "طْ.. مطبخ", "صوت السكون طْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "طَا", "طَاوِلَة", "🪵", VowelType.LONG, "طَا.. طاولة", "مد بالألف طَا"),
        vowel("مد بالواو", "طُو", "طُوب", "🧱", VowelType.LONG, "طُو.. طوب", "مد بالواو طُو"),
        vowel("مد بالياء", "طِي", "طِين", "🏺", VowelType.LONG, "طِي.. طين", "مد بالياء طِي")
      )
    ),
    createLetter(
      id = 17,
      char = "ظ",
      name = "ظَاء",
      phonetic = "ظاء",
      emoji = "✉️",
      colorHex = 0xFF4ECDC4,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ظ", "مَحْفَظَة", "مَحْفَ ظَة", "🎒", "محفظة المدرسة الجميلة", "مثل الطاء مع نقطة بجانب العصا", listOf(GuidePoint(0.5f, 0.75f, 1, isStart = true), GuidePoint(0.8f, 0.6f, 2), GuidePoint(0.65f, 0.3f, 3), GuidePoint(0.5f, 0.35f, 4, isDot = true))),
        form(LetterPosition.INITIAL, "ظـ", "ظَرْف", "ظَـرْف", "✉️", "ظرف رسالة يحمل مفاجأة", "في أول الكلمة يمد يده مع النقطة", listOf(GuidePoint(0.5f, 0.75f, 1, isStart = true), GuidePoint(0.8f, 0.6f, 2), GuidePoint(0.2f, 0.75f, 3), GuidePoint(0.5f, 0.35f, 4, isDot = true))),
        form(LetterPosition.MEDIAL, "ـظـ", "نَظَّارَة", "نَـظَّـارَة", "👓", "نظارة شمسية أنيقة", "يمد يديه الاثنتين مع العصا والنقطة", listOf(GuidePoint(0.9f, 0.75f, 1, isStart = true), GuidePoint(0.7f, 0.6f, 2), GuidePoint(0.15f, 0.75f, 3), GuidePoint(0.5f, 0.35f, 4, isDot = true))),
        form(LetterPosition.FINAL, "ـظ", "حَافِظ", "حَافِـظ", "🛡️", "حافظ الأمين المخلص", "يتصل بالحرف قبله والنقطة تضيء", listOf(GuidePoint(0.9f, 0.75f, 1, isStart = true), GuidePoint(0.6f, 0.6f, 2), GuidePoint(0.35f, 0.75f, 3), GuidePoint(0.5f, 0.35f, 4, isDot = true)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "ظَ", "ظَبْي", "🦌", VowelType.SHORT, "ظَ.. ظبي", "صوت الظاء ظَ مفخم مع إخراج اللسان"),
        vowel("الضمة", "ظُ", "ظُفْر", "💅", VowelType.SHORT, "ظُ.. ظفر", "صوت الضمة ظُ"),
        vowel("الكسرة", "ظِ", "ظِلّ", "👥", VowelType.SHORT, "ظِ.. ظل", "صوت الكسرة ظِ"),
        vowel("السكون", "ظْ", "عَظْم", "🦴", VowelType.SHORT, "ظْ.. عظم", "صوت السكون ظْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "ظَا", "ظَاهِر", "👁️", VowelType.LONG, "ظَا.. ظاهر", "مد بالألف ظَا"),
        vowel("مد بالواو", "ظُو", "مَحْظُوظ", "🍀", VowelType.LONG, "ظُو.. محظوظ", "مد بالواو ظُو"),
        vowel("مد بالياء", "ظِي", "حَظِيرَة", "🏡", VowelType.LONG, "ظِي.. حظيرة", "مد بالياء ظِي")
      )
    ),
    createLetter(
      id = 18,
      char = "ع",
      name = "عَيْن",
      phonetic = "عين",
      emoji = "👁️",
      colorHex = 0xFFFF6B6B,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ع", "ذِرَاع", "ذِرَا ع", "💪", "ذراع قوية للعمل والرياضة", "نصف دائرة صغيرة فوق السطر وبطن كبير تحته", listOf(GuidePoint(0.65f, 0.4f, 1, isStart = true), GuidePoint(0.45f, 0.45f, 2), GuidePoint(0.6f, 0.6f, 3), GuidePoint(0.3f, 0.85f, 4))),
        form(LetterPosition.INITIAL, "عـ", "عَيْن", "عَـيْن", "👁️", "عين نبصر بها جمال الدنيا", "نصف دائرة فوق السطر تمد يدها لليسار", listOf(GuidePoint(0.75f, 0.45f, 1, isStart = true), GuidePoint(0.55f, 0.55f, 2), GuidePoint(0.65f, 0.75f, 3), GuidePoint(0.2f, 0.75f, 4))),
        form(LetterPosition.MEDIAL, "ـعـ", "ثَعْلَب", "ثَـعْـلَب", "🦊", "ثعلب ذكي في البرية", "مثلث مغلق مقلوب على السطر يمد يديه", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.6f, 0.5f, 2), GuidePoint(0.45f, 0.5f, 3), GuidePoint(0.15f, 0.75f, 4))),
        form(LetterPosition.FINAL, "ـع", "شَارِع", "شَارِ ع", "🛣️", "شارع نظيف وواسع", "يتصل بالحرف قبله ثم ينزل بالبطن الكبير", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.55f, 0.55f, 2), GuidePoint(0.3f, 0.85f, 3)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "عَ", "عَيْن", "👁️", VowelType.SHORT, "عَ.. عين", "صوت العين عَ من وسط الحلق"),
        vowel("الضمة", "عُ", "عُشّ", "🪺", VowelType.SHORT, "عُ.. عش", "صوت الضمة عُ"),
        vowel("الكسرة", "عِ", "عِنَب", "🍇", VowelType.SHORT, "عِ.. عنب", "صوت الكسرة عِ"),
        vowel("السكون", "عْ", "شَعْر", "💇", VowelType.SHORT, "عْ.. شعر", "صوت السكون عْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "عَا", "عَالِم", "🧑‍🔬", VowelType.LONG, "عَا.. عالم", "مد بالألف عَا"),
        vowel("مد بالواو", "عُو", "عُود", "🪕", VowelType.LONG, "عُو.. عود", "مد بالواو عُو"),
        vowel("مد بالياء", "عِي", "عِيد", "🎈", VowelType.LONG, "عِي.. عيد", "مد بالياء عِي")
      )
    ),
    createLetter(
      id = 19,
      char = "غ",
      name = "غَيْن",
      phonetic = "غين",
      emoji = "🦌",
      colorHex = 0xFF3A86FF,
      forms = listOf(
        form(LetterPosition.ISOLATED, "غ", "دِمَاغ", "دِمَا غ", "🧠", "دماغ ذكي يفكر ويبدع", "مثل العين مع نقطة فوق الرأس", listOf(GuidePoint(0.65f, 0.4f, 1, isStart = true), GuidePoint(0.3f, 0.85f, 2), GuidePoint(0.6f, 0.25f, 3, isDot = true))),
        form(LetterPosition.INITIAL, "غـ", "غَزَال", "غَـزَال", "🦌", "غزال سريع وجميل في الحقول", "نصف دائرة تمتد والنقطة فوقها", listOf(GuidePoint(0.75f, 0.45f, 1, isStart = true), GuidePoint(0.2f, 0.75f, 2), GuidePoint(0.65f, 0.3f, 3, isDot = true))),
        form(LetterPosition.MEDIAL, "ـغـ", "مَغْسَلَة", "مَـغْـسَلَة", "🫧", "مغسلة نغسل بها أيدينا", "مثلث مطموس على السطر والنقطة فوقه", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.5f, 0.5f, 2), GuidePoint(0.15f, 0.75f, 3), GuidePoint(0.5f, 0.3f, 4, isDot = true))),
        form(LetterPosition.FINAL, "ـغ", "صَمْغ", "صَمْـغ", "🧴", "صمغ نلصق به اللوحات", "يتصل بالحرف قبله والبطن الكبير والنقطة", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.55f, 0.55f, 2), GuidePoint(0.3f, 0.85f, 3), GuidePoint(0.55f, 0.3f, 4, isDot = true)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "غَ", "غَزَال", "🦌", VowelType.SHORT, "غَ.. غزال", "صوت الغين غَ"),
        vowel("الضمة", "غُ", "غُرَاب", "🦅", VowelType.SHORT, "غُ.. غراب", "صوت الضمة غُ"),
        vowel("الكسرة", "غِ", "غِطَاء", "🛏️", VowelType.SHORT, "غِ.. غطاء", "صوت الكسرة غِ"),
        vowel("السكون", "غْ", "مَغْرِب", "🌅", VowelType.SHORT, "غْ.. مغرب", "صوت السكون غْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "غَا", "غَابَة", "🌲", VowelType.LONG, "غَا.. غابة", "مد بالألف غَا"),
        vowel("مد بالواو", "غُو", "غُول", "👾", VowelType.LONG, "غُو.. غول", "مد بالواو غُو"),
        vowel("مد بالياء", "غِي", "رَغِيف", "🫓", VowelType.LONG, "غِي.. رغيف", "مد بالياء غِي")
      )
    ),
    createLetter(
      id = 20,
      char = "ف",
      name = "فَاء",
      phonetic = "فاء",
      emoji = "🦋",
      colorHex = 0xFFFF4081,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ف", "خَرُوف", "خَرُو ف", "🐑", "خروف أبيض جميل", "دائرة صغيرة برقبة قصيرة وصحن ونقطة فوقه", listOf(GuidePoint(0.6f, 0.5f, 1, isStart = true), GuidePoint(0.75f, 0.75f, 2), GuidePoint(0.2f, 0.75f, 3), GuidePoint(0.2f, 0.55f, 4), GuidePoint(0.65f, 0.35f, 5, isDot = true))),
        form(LetterPosition.INITIAL, "فـ", "فَرَاشَة", "فَـرَاشَة", "🦋", "فراشة ملونة تطير بخفة", "دائرة صغيرة تمتد لليسار ونقطة واحدة فوقها", listOf(GuidePoint(0.65f, 0.55f, 1, isStart = true), GuidePoint(0.7f, 0.75f, 2), GuidePoint(0.2f, 0.75f, 3), GuidePoint(0.65f, 0.35f, 4, isDot = true))),
        form(LetterPosition.MEDIAL, "ـفـ", "ضِفْدَع", "ضِـفْـدَع", "🐸", "ضفدع لطيف", "يمد يديه ودائرة في الوسط تعلوها نقطة", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.5f, 0.55f, 2), GuidePoint(0.15f, 0.75f, 3), GuidePoint(0.5f, 0.35f, 4, isDot = true))),
        form(LetterPosition.FINAL, "ـف", "صَيْف", "صَيْـف", "🏖️", "صيف مشمس وسعيد", "يتصل ويقفل الصحن مع النقطة", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.55f, 0.55f, 2), GuidePoint(0.25f, 0.75f, 3), GuidePoint(0.55f, 0.35f, 4, isDot = true)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "فَ", "فَرَاشَة", "🦋", VowelType.SHORT, "فَ.. فراشة", "صوت الفاء فَ مع هواء الأسنان"),
        vowel("الضمة", "فُ", "فُرْشَاة", "🪥", VowelType.SHORT, "فُ.. فرشاة", "صوت الضمة فُ"),
        vowel("الكسرة", "فِ", "فِيل", "🐘", VowelType.SHORT, "فِ.. فيل", "صوت الكسرة فِ"),
        vowel("السكون", "فْ", "طِفْل", "👶", VowelType.SHORT, "فْ.. طفل", "صوت السكون فْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "فَا", "فَارِس", "🏇", VowelType.LONG, "فَا.. فارس", "مد بالألف فَا"),
        vowel("مد بالواو", "فُو", "فُول", "🫘", VowelType.LONG, "فُو.. فول", "مد بالواو فُو"),
        vowel("مد بالياء", "فِي", "فِيل", "🐘", VowelType.LONG, "فِي.. فيل", "مد بالياء فِي")
      )
    ),
    createLetter(
      id = 21,
      char = "ق",
      name = "قَاف",
      phonetic = "قاف",
      emoji = "✏️",
      colorHex = 0xFF8338EC,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ق", "طَوْق", "طَوْ ق", "⭕", "طوق النجاة الملون", "دائرة وصحن ينزل تحت السطر مع نقطتين فوق", listOf(GuidePoint(0.6f, 0.5f, 1, isStart = true), GuidePoint(0.7f, 0.85f, 2), GuidePoint(0.25f, 0.7f, 3), GuidePoint(0.5f, 0.3f, 4, isDot = true), GuidePoint(0.65f, 0.3f, 5, isDot = true))),
        form(LetterPosition.INITIAL, "قـ", "قَلَم", "قَـلَم", "✏️", "قلم رصاص نكتب به الحروف", "دائرة على السطر تمتد لليسار مع نقطتين", listOf(GuidePoint(0.65f, 0.55f, 1, isStart = true), GuidePoint(0.2f, 0.75f, 2), GuidePoint(0.55f, 0.3f, 3, isDot = true), GuidePoint(0.7f, 0.3f, 4, isDot = true))),
        form(LetterPosition.MEDIAL, "ـقـ", "صَقْر", "صَـقْـر", "🦅", "صقر قوي يحلق", "يمد يديه ودائرة في الوسط تعلوها نقطتان", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.5f, 0.55f, 2), GuidePoint(0.15f, 0.75f, 3), GuidePoint(0.45f, 0.3f, 4, isDot = true), GuidePoint(0.55f, 0.3f, 5, isDot = true))),
        form(LetterPosition.FINAL, "ـق", "وَرَق", "وَرَ ق", "🍃", "ورق الشجر الأخضر", "يتصل وينزل بصحنه الدائري مع النقطتين", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.6f, 0.55f, 2), GuidePoint(0.3f, 0.85f, 3), GuidePoint(0.5f, 0.3f, 4, isDot = true), GuidePoint(0.65f, 0.3f, 5, isDot = true)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "قَ", "قَلَم", "✏️", VowelType.SHORT, "قَ.. قلم", "صوت القاف قَ مفخم وقوي"),
        vowel("الضمة", "قُ", "قُبَّعَة", "👒", VowelType.SHORT, "قُ.. قبعة", "صوت الضمة قُ"),
        vowel("الكسرة", "قِ", "قِطَّة", "🐱", VowelType.SHORT, "قِ.. قطة", "صوت الكسرة قِ"),
        vowel("السكون", "قْ", "عَقْل", "🧠", VowelType.SHORT, "قْ.. عقل", "صوت السكون قْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "قَا", "قَارِب", "⛵", VowelType.LONG, "قَا.. قارب", "مد بالألف قَا"),
        vowel("مد بالواو", "قُو", "قُوَّة", "💪", VowelType.LONG, "قُو.. قوة", "مد بالواو قُو"),
        vowel("مد بالياء", "قِي", "حَقِيبَة", "🎒", VowelType.LONG, "قِي.. حقيبة", "مد بالياء قِي")
      )
    ),
    createLetter(
      id = 22,
      char = "ك",
      name = "كَاف",
      phonetic = "كاف",
      emoji = "🐶",
      colorHex = 0xFFFFBE0B,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ك", "شُبَّاك", "شُبَّا ك", "🪟", "شباك الغرفة يدخل النور", "عصا وصحن عريض وبداخله كاف صغيرة تشبه الهمزة", listOf(GuidePoint(0.75f, 0.25f, 1, isStart = true), GuidePoint(0.75f, 0.75f, 2), GuidePoint(0.25f, 0.75f, 3), GuidePoint(0.5f, 0.5f, 4))),
        form(LetterPosition.INITIAL, "كـ", "كَلْب", "كَـلْب", "🐶", "كلب وفي يحرس البيت", "خط مائل ثم خط مائل آخر يمد يده على السطر", listOf(GuidePoint(0.55f, 0.3f, 1, isStart = true), GuidePoint(0.75f, 0.55f, 2), GuidePoint(0.55f, 0.75f, 3), GuidePoint(0.2f, 0.75f, 4))),
        form(LetterPosition.MEDIAL, "ـكـ", "سَمَكَة", "سَـمَـكَـة", "🐟", "سمكة ذهبية في الحوض", "يتصل من اليمين بخط مائل ويمتد لليسار", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.65f, 0.45f, 2), GuidePoint(0.5f, 0.75f, 3), GuidePoint(0.15f, 0.75f, 4))),
        form(LetterPosition.FINAL, "ـك", "دِيك", "دِيـك", "🐓", "ديك يوقظنا في الصباح", "يتصل بالحرف قبله ويصعد بالعصا مع الشارة داخله", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.65f, 0.75f, 2), GuidePoint(0.65f, 0.25f, 3), GuidePoint(0.25f, 0.75f, 4)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "كَ", "كَلْب", "🐶", VowelType.SHORT, "كَ.. كلب", "صوت الكاف كَ مرقق"),
        vowel("الضمة", "كُ", "كُرَة", "⚽", VowelType.SHORT, "كُ.. كرة", "صوت الضمة كُ"),
        vowel("الكسرة", "كِ", "كِتَاب", "📖", VowelType.SHORT, "كِ.. كتاب", "صوت الكسرة كِ"),
        vowel("السكون", "كْ", "مَكْتَب", "💼", VowelType.SHORT, "كْ.. مكتب", "صوت السكون كْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "كَا", "كَاتِب", "✍️", VowelType.LONG, "كَا.. كاتب", "مد بالألف كَا"),
        vowel("مد بالواو", "كُو", "كُوب", "🥛", VowelType.LONG, "كُو.. كوب", "مد بالواو كُو"),
        vowel("مد بالياء", "كِي", "كِيس", "🛍️", VowelType.LONG, "كِي.. كيس", "مد بالياء كِي")
      )
    ),
    createLetter(
      id = 23,
      char = "ل",
      name = "لاَم",
      phonetic = "لام",
      emoji = "🍋",
      colorHex = 0xFF06D6A0,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ل", "غَزَال", "غَزَا ل", "🦌", "غزال يرعى في المرج", "عصا مستقيمة طويلة تنزل بقوس جميل تحت السطر", listOf(GuidePoint(0.65f, 0.2f, 1, isStart = true), GuidePoint(0.65f, 0.75f, 2), GuidePoint(0.35f, 0.85f, 3), GuidePoint(0.35f, 0.65f, 4))),
        form(LetterPosition.INITIAL, "لـ", "لَيْمُون", "لَـيْمُون", "🍋", "ليمون أصفر حامض ولذيذ", "عصا طويلة تنزل للسطر وتمد يدها لليسار", listOf(GuidePoint(0.7f, 0.2f, 1, isStart = true), GuidePoint(0.7f, 0.75f, 2), GuidePoint(0.2f, 0.75f, 3))),
        form(LetterPosition.MEDIAL, "ـلـ", "قَلَم", "قَـلَـم", "✏️", "قلم نكتب به الدروس", "يتصل من اليمين ويصعد كالسارية ثم يمد يده لليسار", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.5f, 0.75f, 2), GuidePoint(0.5f, 0.2f, 3), GuidePoint(0.5f, 0.75f, 4), GuidePoint(0.15f, 0.75f, 5))),
        form(LetterPosition.FINAL, "ـل", "جَمَل", "جَمَـل", "🐪", "جمل الصحراء الصبور", "يتصل بالكلمة وينزل بالقوس تحت السطر", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.6f, 0.75f, 2), GuidePoint(0.6f, 0.2f, 3), GuidePoint(0.35f, 0.85f, 4)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "لَ", "لَحْم", "🥩", VowelType.SHORT, "لَ.. لحم", "صوت اللام لَ خفيف وسهل"),
        vowel("الضمة", "لُ", "لُعْبَة", "🧸", VowelType.SHORT, "لُ.. لعبة", "صوت الضمة لُ"),
        vowel("الكسرة", "لِ", "لِسَان", "👅", VowelType.SHORT, "لِ.. لسان", "صوت الكسرة لِ"),
        vowel("السكون", "لْ", "ثَلْج", "❄️", VowelType.SHORT, "لْ.. ثلج", "صوت السكون لْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "لاَ", "لاَعِب", "⚽", VowelType.LONG, "لاَ.. لاعب", "مد بالألف لاَ"),
        vowel("مد بالواو", "لُو", "لُوبِيَا", "🌱", VowelType.LONG, "لُو.. لوبيا", "مد بالواو لُو"),
        vowel("مد بالياء", "لِي", "لَيْمُونِي", "🍋", VowelType.LONG, "لِي.. ليموني", "مد بالياء لِي")
      )
    ),
    createLetter(
      id = 24,
      char = "م",
      name = "مِيم",
      phonetic = "ميم",
      emoji = "🍌",
      colorHex = 0xFFFF7A00,
      forms = listOf(
        form(LetterPosition.ISOLATED, "م", "هَرَم", "هَرَ م", "🔺", "هرم تاريخي كبير وعريق", "دائرة صغيرة وعصا تنزل مستقيمة للأسفل", listOf(GuidePoint(0.55f, 0.5f, 1, isStart = true), GuidePoint(0.4f, 0.45f, 2), GuidePoint(0.4f, 0.6f, 3), GuidePoint(0.4f, 0.9f, 4))),
        form(LetterPosition.INITIAL, "مـ", "مَوْز", "مَـوْز", "🍌", "موز حلو ومفيد نأكله كل يوم", "دائرة صغيرة على السطر تمد يدها لليسار", listOf(GuidePoint(0.65f, 0.65f, 1, isStart = true), GuidePoint(0.75f, 0.55f, 2), GuidePoint(0.55f, 0.75f, 3), GuidePoint(0.2f, 0.75f, 4))),
        form(LetterPosition.MEDIAL, "ـمـ", "شَمْس", "شَـمْـس", "☀️", "شمس تنشر الدفء والضياء", "يمد يديه ودائرة الميم في المنتصف", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.55f, 0.6f, 2), GuidePoint(0.45f, 0.75f, 3), GuidePoint(0.15f, 0.75f, 4))),
        form(LetterPosition.FINAL, "ـم", "قَلَم", "قَلَـم", "✏️", "قلم نكتب به الدروس الجميلة", "يتصل بالحرف قبله وتنزل عصاه للأسفل", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.55f, 0.6f, 2), GuidePoint(0.4f, 0.9f, 3)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "مَ", "مَوْز", "🍌", VowelType.SHORT, "مَ.. موز", "صوت الميم مَ الشفتان مغلقتان بلطف"),
        vowel("الضمة", "مُ", "مُهَرِّج", "🤡", VowelType.SHORT, "مُ.. مهرج", "صوت الضمة مُ"),
        vowel("الكسرة", "مِ", "مِقَصّ", "✂️", VowelType.SHORT, "مِ.. مقص", "صوت الكسرة مِ"),
        vowel("السكون", "مْ", "شَمْس", "☀️", VowelType.SHORT, "مْ.. شمس", "صوت السكون مْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "مَا", "مَاء", "💧", VowelType.LONG, "مَا.. ماء", "مد بالألف مَا"),
        vowel("مد بالواو", "مُو", "مُوسِيقَى", "🎵", VowelType.LONG, "مُو.. موسيقى", "مد بالواو مُو"),
        vowel("مد بالياء", "مِي", "مِيزَان", "⚖️", VowelType.LONG, "مِي.. ميزان", "مد بالياء مِي")
      )
    ),
    createLetter(
      id = 25,
      char = "ن",
      name = "نُون",
      phonetic = "نون",
      emoji = "🐝",
      colorHex = 0xFF2EC4B6,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ن", "لَيْمُون", "لَيْمُو ن", "🍋", "ليمون ناضج ولذيذ", "صحن عميق ينزل تحت السطر ونقطة في قلبه", listOf(GuidePoint(0.75f, 0.5f, 1, isStart = true), GuidePoint(0.65f, 0.85f, 2), GuidePoint(0.25f, 0.85f, 3), GuidePoint(0.25f, 0.5f, 4), GuidePoint(0.5f, 0.6f, 5, isDot = true))),
        form(LetterPosition.INITIAL, "نـ", "نَحْلَة", "نَـحْلَة", "🐝", "نحلة نشيطة تصنع العسل اللذيذ", "سن على السطر يمد يده ونقطة فوقه", listOf(GuidePoint(0.75f, 0.5f, 1, isStart = true), GuidePoint(0.75f, 0.75f, 2), GuidePoint(0.2f, 0.75f, 3), GuidePoint(0.75f, 0.35f, 4, isDot = true))),
        form(LetterPosition.MEDIAL, "ـنـ", "عِنَب", "عِـنَـب", "🍇", "عنب عناقيده شهية", "يمد يديه وسن صغير تعلوه نقطة", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.5f, 0.75f, 2), GuidePoint(0.5f, 0.5f, 3), GuidePoint(0.15f, 0.75f, 4), GuidePoint(0.5f, 0.35f, 5, isDot = true))),
        form(LetterPosition.FINAL, "ـن", "عَيْن", "عَيْـن", "👁️", "عين نبصر بها", "يتصل وينزل بصحن النون العميق مع نقطته", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.65f, 0.75f, 2), GuidePoint(0.5f, 0.85f, 3), GuidePoint(0.25f, 0.5f, 4), GuidePoint(0.5f, 0.6f, 5, isDot = true)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "نَ", "نَمْلَة", "🐜", VowelType.SHORT, "نَ.. نملة", "صوت الفتحة نَ غنة خفيفة"),
        vowel("الضمة", "نُ", "نُجُوم", "⭐", VowelType.SHORT, "نُ.. نجوم", "صوت الضمة نُ"),
        vowel("الكسرة", "نِ", "نِسْر", "🦅", VowelType.SHORT, "نِ.. نسر", "صوت الكسرة نِ"),
        vowel("السكون", "نْ", "بِنْت", "👧", VowelType.SHORT, "نْ.. بنت", "صوت السكون نْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "نَا", "نَار", "🔥", VowelType.LONG, "نَا.. نار", "مد بالألف نَا"),
        vowel("مد بالواو", "نُو", "نُور", "💡", VowelType.LONG, "نُو.. نور", "مد بالواو نُو"),
        vowel("مد بالياء", "نِي", "نِيل", "🌊", VowelType.LONG, "نِي.. نيل", "مد بالياء نِي")
      )
    ),
    createLetter(
      id = 26,
      char = "هـ",
      name = "هَاء",
      phonetic = "هاء",
      emoji = "🎁",
      colorHex = 0xFFFF4081,
      forms = listOf(
        form(LetterPosition.ISOLATED, "هـ", "مِيَاه", "مِيَا ه", "💧", "مياه عذبة نشربها كل يوم", "دائرة لطيفة تشبه الحلقة أو الكعكة", listOf(GuidePoint(0.5f, 0.4f, 1, isStart = true), GuidePoint(0.7f, 0.6f, 2), GuidePoint(0.5f, 0.8f, 3), GuidePoint(0.3f, 0.6f, 4))),
        form(LetterPosition.INITIAL, "هـ", "هَدِيَّة", "هَـدِيَّة", "🎁", "هدية جميلة في عيد الميلاد", "قوس كبير بداخله دائرة صغيرة تمتد لليسار", listOf(GuidePoint(0.55f, 0.75f, 1, isStart = true), GuidePoint(0.75f, 0.45f, 2), GuidePoint(0.6f, 0.55f, 3), GuidePoint(0.2f, 0.75f, 4))),
        form(LetterPosition.MEDIAL, "ـهـ", "فَهْد", "فَـهْـد", "🐆", "فهد سريع جداً", "كفيونكة جميلة حلقة للأعلى وحلقة للأسفل", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.5f, 0.45f, 2), GuidePoint(0.5f, 0.9f, 3), GuidePoint(0.15f, 0.75f, 4))),
        form(LetterPosition.FINAL, "ـه", "وَجْه", "وَجْـه", "😊", "وجه مبتسم يشرق بالسعادة", "يتصل بالحرف قبله ويرتفع كرقبة صغيرة مع حلقة", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.55f, 0.75f, 2), GuidePoint(0.55f, 0.45f, 3), GuidePoint(0.35f, 0.6f, 4)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "هَـ", "هَدِيَّة", "🎁", VowelType.SHORT, "هَـ.. هدية", "صوت الهاء هَـ خروج هواء رقيق"),
        vowel("الضمة", "هُـ", "هُدْهُد", "🐦", VowelType.SHORT, "هُـ.. هدهد", "صوت الضمة هُـ"),
        vowel("الكسرة", "هِـ", "هِلال", "🌙", VowelType.SHORT, "هِـ.. هلال", "صوت الكسرة هِـ"),
        vowel("السكون", "هْـ", "فَهْد", "🐆", VowelType.SHORT, "هْـ.. فهد", "صوت السكون هْـ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "هَا", "هَاتِف", "📱", VowelType.LONG, "هَا.. هاتف", "مد بالألف هَا"),
        vowel("مد بالواو", "هُو", "زُهُور", "💐", VowelType.LONG, "هُو.. زهور", "مد بالواو هُو"),
        vowel("مد بالياء", "هِي", "شَهِيق", "🫁", VowelType.LONG, "هِي.. شهيق", "مد بالياء هِي")
      )
    ),
    createLetter(
      id = 27,
      char = "و",
      name = "وَاو",
      phonetic = "واو",
      emoji = "🌹",
      colorHex = 0xFFFF6B6B,
      forms = listOf(
        form(LetterPosition.ISOLATED, "و", "جَرْو", "جَرْ و", "🐶", "جرو صغير يلعب بمرح", "دائرة صغيرة فوق السطر وزحليقة تحته", listOf(GuidePoint(0.6f, 0.55f, 1, isStart = true), GuidePoint(0.7f, 0.45f, 2), GuidePoint(0.5f, 0.65f, 3), GuidePoint(0.3f, 0.85f, 4))),
        form(LetterPosition.INITIAL, "و", "وَرْدَة", "وَرْدَة", "🌹", "وردة حمراء عطرها فواح", "في أول الكلمة لا يتصل بما بعده", listOf(GuidePoint(0.6f, 0.55f, 1, isStart = true), GuidePoint(0.7f, 0.45f, 2), GuidePoint(0.5f, 0.65f, 3), GuidePoint(0.3f, 0.85f, 4))),
        form(LetterPosition.MEDIAL, "ـو", "لَوْز", "لَـوْ ز", "🥜", "لوز صحي ومقرمش", "يتصل من اليمين بدائرة الواو ويزحلق", listOf(GuidePoint(0.85f, 0.65f, 1, isStart = true), GuidePoint(0.6f, 0.55f, 2), GuidePoint(0.3f, 0.85f, 3))),
        form(LetterPosition.FINAL, "ـو", "دَلْو", "دَلْـو", "🪣", "دلو نملؤه بالماء", "يتصل بالحرف قبله ويزحلق تحت السطر", listOf(GuidePoint(0.85f, 0.65f, 1, isStart = true), GuidePoint(0.6f, 0.55f, 2), GuidePoint(0.3f, 0.85f, 3)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", "وَ", "وَرْدَة", "🌹", VowelType.SHORT, "وَ.. وردة", "صوت الواو وَ خفيف"),
        vowel("الضمة", "وُ", "وُجُوه", "😃", VowelType.SHORT, "وُ.. وجوه", "صوت الضمة وُ"),
        vowel("الكسرة", "وِ", "وِسَادَة", "🛏️", VowelType.SHORT, "وِ.. وسادة", "صوت الكسرة وِ"),
        vowel("السكون", "وْ", "لَوْز", "🥜", VowelType.SHORT, "وْ.. لوز", "صوت السكون وْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "وَا", "وَادِي", "🏞️", VowelType.LONG, "وَا.. وادي", "مد بالألف وَا"),
        vowel("مد بالواو", "وُو", "طَاوُوس", "🦚", VowelType.LONG, "وُو.. طاووس", "مد بالواو وُو"),
        vowel("مد بالياء", "وِي", "وِسَام", "🎖️", VowelType.LONG, "وِي.. وسام", "مد بالياء وِي")
      )
    ),
    createLetter(
      id = 28,
      char = "ي",
      name = "يَاء",
      phonetic = "ياء",
      emoji = "✋",
      colorHex = 0xFF00B4D8,
      forms = listOf(
        form(LetterPosition.ISOLATED, "ي", "شَاي", "شَا ي", "🍵", "شاي دافئ لذيذ", "رأس يشبه البطة وبطن تحت السطر ونقطتان تحته", listOf(GuidePoint(0.7f, 0.45f, 1, isStart = true), GuidePoint(0.55f, 0.55f, 2), GuidePoint(0.4f, 0.85f, 3), GuidePoint(0.2f, 0.7f, 4), GuidePoint(0.4f, 0.95f, 5, isDot = true), GuidePoint(0.55f, 0.95f, 6, isDot = true))),
        form(LetterPosition.INITIAL, "يـ", "يَد", "يَـد", "✋", "يد نلوح بها تحية للأصدقاء", "سن على السطر يمد يده ونقطتان تحته", listOf(GuidePoint(0.75f, 0.5f, 1, isStart = true), GuidePoint(0.75f, 0.75f, 2), GuidePoint(0.2f, 0.75f, 3), GuidePoint(0.65f, 0.9f, 4, isDot = true), GuidePoint(0.8f, 0.9f, 5, isDot = true))),
        form(LetterPosition.MEDIAL, "ـيـ", "طَيْر", "طَـيْـر", "🕊️", "طير يغرد بأعذب صوت", "يمد يديه ونقطتان تحته", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.5f, 0.75f, 2), GuidePoint(0.5f, 0.5f, 3), GuidePoint(0.15f, 0.75f, 4), GuidePoint(0.45f, 0.9f, 5, isDot = true), GuidePoint(0.55f, 0.9f, 6, isDot = true))),
        form(LetterPosition.FINAL, "ـي", "كُرْسِيّ", "كُرْسِـيّ", "🪑", "كرسي مريح نجلس عليه", "يتصل بالحرف قبله وبطن الياء مع النقطتين", listOf(GuidePoint(0.85f, 0.75f, 1, isStart = true), GuidePoint(0.6f, 0.65f, 2), GuidePoint(0.4f, 0.85f, 3), GuidePoint(0.4f, 0.95f, 4, isDot = true), GuidePoint(0.55f, 0.95f, 5, isDot = true)))
      ),
      shortVowels = listOf(
        vowel("الفتحة", " يَ", "يَد", "✋", VowelType.SHORT, "يَ.. يد", "صوت الياء يَ"),
        vowel("الضمة", "يُ", "يُوسُفِيّ", "🍊", VowelType.SHORT, "يُ.. يوسفي", "صوت الضمة يُ"),
        vowel("الكسرة", "يِ", "يَسَار", "⬅️", VowelType.SHORT, "يِ.. يسار", "صوت الكسرة يِ"),
        vowel("السكون", "يْ", "بَيْت", "🏠", VowelType.SHORT, "يْ.. بيت", "صوت السكون يْ")
      ),
      longVowels = listOf(
        vowel("مد بالألف", "يَا", "يَاسَمِين", "🌸", VowelType.LONG, "يَا.. ياسمين", "مد بالألف يَا"),
        vowel("مد بالواو", "يُو", "يُويُو", "🪀", VowelType.LONG, "يُو.. يويو", "مد بالواو يُو"),
        vowel("مد بالياء", "يِي", "طَبِيبِي", "🩺", VowelType.LONG, "يِي.. طبيبي", "مد بالياء يِي")
      )
    )
  )

  val stickers: List<StickerItem> = listOf(
    StickerItem("s1", "أ", "شبل الأسد الشجاع", "🦁", 2),
    StickerItem("s2", "ب", "البطة الذهبية", "🦆", 4),
    StickerItem("s3", "ت", "تفاحة السعادة", "🍎", 6),
    StickerItem("s4", "ث", "الثعلب البطل", "🦊", 8),
    StickerItem("s5", "ج", "الجمل الصغير ظريف", "🐪", 10),
    StickerItem("s6", "ح", "الحصان الطائر", "🦄", 12),
    StickerItem("s7", "خ", "خروف الصوف الناعم", "🐑", 14),
    StickerItem("s8", "د", "الدب العسول", "🐻", 16),
    StickerItem("s9", "ر", "صاروخ النجوم", "🚀", 18),
    StickerItem("s10", "ز", "الزرافة الأنيقة", "🦒", 20),
    StickerItem("s11", "س", "السمكة السحرية", "🐠", 22),
    StickerItem("s12", "ش", "شمس الضحى المشرقة", "☀️", 24),
    StickerItem("s13", "ص", "الصقر الجريء", "🦅", 26),
    StickerItem("s14", "ف", "الفراشة الملونة", "🦋", 28),
    StickerItem("s15", "ق", "قلم الإبداع الذهبي", "✏️", 30),
    StickerItem("s16", "ك", "الكلب الوفي", "🐶", 32),
    StickerItem("s17", "ن", "نحلة العسل الماهرة", "🐝", 35),
    StickerItem("s18", "و", "وردة الأبطال الفائقة", "🌹", 40)
  )

  fun getLetterById(id: Int): ArabicLetter = letters.firstOrNull { it.id == id } ?: letters.first()

  private fun createLetter(
    id: Int,
    char: String,
    name: String,
    phonetic: String,
    emoji: String,
    colorHex: Long,
    forms: List<LetterFormDetail>,
    shortVowels: List<VowelDetail>,
    longVowels: List<VowelDetail>
  ): ArabicLetter {
    val comparisons = mutableListOf<VowelComparison>()
    if (shortVowels.isNotEmpty() && longVowels.isNotEmpty()) {
      comparisons.add(
        VowelComparison(
          title = "الفتحة مقابل مد الألف",
          shortVowel = shortVowels[0],
          longVowel = longVowels[0],
          contrastHint = "الفتحة صوت قصير وخاطف (بَ)، بينما مد الألف صوت طويل وممدود للأعلى (بَا)"
        )
      )
      if (shortVowels.size > 1 && longVowels.size > 1) {
        comparisons.add(
          VowelComparison(
            title = "الضمة مقابل مد الواو",
            shortVowel = shortVowels[1],
            longVowel = longVowels[1],
            contrastHint = "الضمة نضم الشفتين بسرعة (بُ)، بينما مد الواو نضم ونمد النفس طويلاً (بُو)"
          )
        )
      }
      if (shortVowels.size > 2 && longVowels.size > 2) {
        comparisons.add(
          VowelComparison(
            title = "الكسرة مقابل مد الياء",
            shortVowel = shortVowels[2],
            longVowel = longVowels[2],
            contrastHint = "الكسرة نبتسم بصوت قصير (بِ)، بينما مد الياء نبتسم ونمد الصوت براحة (بِي)"
          )
        )
      }
    }
    return ArabicLetter(
      id = id,
      char = char,
      name = name,
      phoneticName = phonetic,
      emoji = emoji,
      colorHex = colorHex,
      forms = forms,
      shortVowels = shortVowels,
      longVowels = longVowels,
      comparisons = comparisons
    )
  }

  private fun form(
    position: LetterPosition,
    glyph: String,
    exampleWord: String,
    exampleHighlighted: String,
    exampleEmoji: String,
    exampleMeaning: String,
    guideDescription: String,
    guidePoints: List<GuidePoint>
  ) = LetterFormDetail(
    position = position,
    glyph = glyph,
    exampleWord = exampleWord,
    exampleHighlighted = exampleHighlighted,
    exampleEmoji = exampleEmoji,
    exampleMeaning = exampleMeaning,
    guideDescription = guideDescription,
    guidePoints = guidePoints
  )

  private fun vowel(
    title: String,
    glyph: String,
    exampleWord: String,
    exampleEmoji: String,
    type: VowelType,
    audioPrompt: String,
    explanation: String
  ) = VowelDetail(
    title = title,
    vowelGlyph = glyph,
    exampleWord = exampleWord,
    exampleEmoji = exampleEmoji,
    type = type,
    audioPrompt = audioPrompt,
    explanation = explanation
  )
}
