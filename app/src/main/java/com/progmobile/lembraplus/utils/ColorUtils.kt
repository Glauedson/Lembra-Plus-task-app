package com.progmobile.lembraplus.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

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

}