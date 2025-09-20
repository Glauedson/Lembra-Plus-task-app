package com.progmobile.lembraplus.utils

object ColorUtils {

    fun normalizeHex(hex: String): String {
        val cleaned = hex.trim().replace("#", "")
        return when (cleaned.length) {
            6 -> "#FF$cleaned"
            8 -> "#$cleaned"
            else -> "#FF000000"
        }
    }

}