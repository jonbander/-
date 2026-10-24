package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FirstRunNameScreen(
  onSaveName: (String) -> Unit,
  onRequestImport: () -> Unit,
  importMessage: String?,
  modifier: Modifier = Modifier
) {
  var name by remember { mutableStateOf("") }
  val cleanName = name.trim()

  Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(text = "👋", fontSize = 64.sp)
      Spacer(modifier = Modifier.height(12.dp))
      Text(
        text = "أهلاً بك في حروفي المرحة!",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Black,
        textAlign = TextAlign.Center
      )
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = "ما اسم الطفل؟ سنحفظ الاسم والتقدم على هذا الجهاز فقط.",
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
      Spacer(modifier = Modifier.height(22.dp))
      OutlinedTextField(
        value = name,
        onValueChange = { if (it.length <= 24) name = it },
        label = { Text("اسم الطفل") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        supportingText = { Text("لا نرسل الاسم أو التقدم إلى الإنترنت") }
      )
      TextButton(onClick = onRequestImport) {
        Text("استعادة تقدم النسخة التجريبية")
      }
      if (importMessage != null) {
        Text(
          importMessage,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          textAlign = TextAlign.Center
        )
      }
      Spacer(modifier = Modifier.height(16.dp))
      Button(
        onClick = { onSaveName(cleanName) },
        enabled = cleanName.isNotEmpty(),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text("ابدأ التعلّم", fontWeight = FontWeight.Bold)
      }
    }
  }
}

@Composable
fun AboutScreen(
  childName: String,
  appVersion: String,
  totalStars: Int,
  tracedForms: Int,
  totalForms: Int,
  practicedVowelSections: Int,
  totalVowelSections: Int,
  quizAnsweredQuestions: Int,
  quizCorrectAnswers: Int,
  highestQuizQuestion: Int,
  onClearProgress: (keepChildName: Boolean) -> Unit,
  modifier: Modifier = Modifier
) {
  var showClearConfirmation by remember { mutableStateOf(false) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(horizontal = 20.dp, vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text("حول التطبيق", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Black)
    Card(
      modifier = Modifier.fillMaxWidth(),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Icon(Icons.Default.Favorite, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(8.dp))
        Text("حروفي المرحة", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
        Text("تطبيق تعليمي للأطفال لتعلم الحروف العربية", textAlign = TextAlign.Center)
        Spacer(Modifier.height(12.dp))
        Text("إعداد وتوقيع: أحمد", fontWeight = FontWeight.Bold)
        Text("الإصدار الأول · $appVersion", color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
    }

    Card(
      modifier = Modifier.fillMaxWidth(),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
      Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("ملف المتعلّم", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text("اسم الطفل: $childName")
        Text("النجوم: $totalStars")
        Text("أشكال الحروف التي أتمّها: $tracedForms من $totalForms")
        Text("أقسام الحركات والمدود التي استكشفها: $practicedVowelSections من $totalVowelSections")
        Text("أسئلة التحدي المجاب عنها: $quizAnsweredQuestions، الصحيحة: $quizCorrectAnswers")
        Text("أعلى سؤال وصل إليه: $highestQuizQuestion من 7")
        Text(
          "يُحفظ هذا السجل محليًا على الجهاز ولا يُرسل إلى خادم.",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    Button(
      onClick = { showClearConfirmation = true },
      modifier = Modifier.fillMaxWidth()
    ) {
      Icon(Icons.Default.DeleteForever, contentDescription = null)
      Text("مسح سجل المستويات", modifier = Modifier.padding(start = 8.dp))
    }
  }

  if (showClearConfirmation) {
    AlertDialog(
      onDismissRequest = { showClearConfirmation = false },
      icon = { Icon(Icons.Default.Info, contentDescription = null) },
      title = { Text("مسح سجل التقدم؟", fontWeight = FontWeight.Bold) },
      text = { Text("سيُمسح التقدم والنجوم وإجابات التحدي. هل تريد الاحتفاظ باسم الطفل؟") },
      confirmButton = {
        Column(horizontalAlignment = Alignment.End) {
          TextButton(onClick = {
            showClearConfirmation = false
            onClearProgress(true)
          }) { Text("نعم، احتفظ بالاسم") }
          TextButton(onClick = {
            showClearConfirmation = false
            onClearProgress(false)
          }) { Text("لا، امسح الاسم أيضًا") }
        }
      },
      dismissButton = {
        TextButton(onClick = { showClearConfirmation = false }) { Text("إلغاء") }
      }
    )
  }
}
