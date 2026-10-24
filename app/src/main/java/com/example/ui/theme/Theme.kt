package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
  primary = KidsPrimary,
  onPrimary = Color.White,
  primaryContainer = Color(0xFFE0F7FA),
  onPrimaryContainer = KidsPrimaryDark,
  secondary = KidsSecondary,
  onSecondary = Color.White,
  secondaryContainer = Color(0xFFFFE8D6),
  onSecondaryContainer = Color(0xFFB85D00),
  tertiary = KidsTertiary,
  onTertiary = Color.White,
  tertiaryContainer = Color(0xFFFFE4E6),
  onTertiaryContainer = Color(0xFF9E0038),
  background = KidsBackgroundLight,
  onBackground = Color(0xFF1E293B),
  surface = KidsSurfaceLight,
  onSurface = Color(0xFF1E293B),
  surfaceVariant = Color(0xFFEDF2F7),
  onSurfaceVariant = Color(0xFF475569)
)

private val DarkColorScheme = lightColorScheme(
  // Keep cheerful bright colors even in dark mode for young children
  primary = KidsPrimary,
  onPrimary = Color.White,
  secondary = KidsSecondary,
  onSecondary = Color.White,
  tertiary = KidsTertiary,
  background = Color(0xFF0F172A),
  surface = Color(0xFF1E293B),
  onBackground = Color(0xFFF8FAFC),
  onSurface = Color(0xFFF8FAFC)
)

val KidsShapes = Shapes(
  extraSmall = RoundedCornerShape(8.dp),
  small = RoundedCornerShape(14.dp),
  medium = RoundedCornerShape(20.dp),
  large = RoundedCornerShape(26.dp),
  extraLarge = RoundedCornerShape(32.dp)
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    shapes = KidsShapes,
    typography = Typography,
    content = content
  )
}
