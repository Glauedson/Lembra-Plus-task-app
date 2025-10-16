package com.progmobile.lembraplus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
val LightColorScheme = lightColorScheme(
    primary = Color(0xFF3787EA),           // Primary Color
    secondary = Color(0xFF1666C9),         // Secondary Color
    tertiary = Color(0xFFE3E2E2),          // Tertiary Color
    background = Color(0xFFFFFEFE),        // Background Color
    onSurface = Color(0xFF000000),         // Text Color
)

@Composable
fun LembraPlusTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}