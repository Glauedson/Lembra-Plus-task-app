package com.progmobile.lembraplus.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt

object ColorUtils {

    fun colorToHex(color: Color): String = String.format("#%08X", color.toArgb())
    fun normalizeHex(hex: String): String {
        val cleaned = hex.trim().removePrefix("#")
        return when (cleaned.length) {
            6 -> "#FF${cleaned.uppercase()}"
            8 -> "#${cleaned.uppercase()}"
            else -> "#FF000000"
        }
    }

    fun safeParseColor(hex: String?): Color {
        if (hex.isNullOrBlank()) return Color(0xFF9D9D9D)

        val normalized = try {
            ColorUtils.normalizeHex(hex)
        } catch (t: Throwable) {
            null
        } ?: return Color(0xFF9D9D9D)

        return try {
            Color(normalized.toColorInt())
        } catch (t: Throwable) {
            Color(0xFF9D9D9D)
        }
    }

}