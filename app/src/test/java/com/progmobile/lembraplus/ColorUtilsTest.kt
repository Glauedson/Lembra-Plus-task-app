package com.progmobile.lembraplus

import com.progmobile.lembraplus.utils.ColorUtils
import org.junit.Test
import kotlin.test.assertEquals

class ColorUtilsTest {

    @Test
    fun normalizeHexAddsAlphaWhenMissing(){
        val input = "#2196F3"
        val expected = "#FF2196F3"
        assertEquals(expected, ColorUtils.normalizeHex(input))
    }

    @Test
    fun normalizeHexKeepsAlphaWhenPresent() {
        val input = "#802196F3"
        val expected = "#802196F3"
        assertEquals(expected, ColorUtils.normalizeHex(input))
    }

    @Test
    fun normalizeHexReturnsBlackWhenInvalid() {
        val input = "zzz"
        val expected = "#FF000000"
        assertEquals(expected, ColorUtils.normalizeHex(input))
    }

    @Test
    fun normalizeHexHandlesWithoutHash() {
        val input = "2196F3"
        val expected = "#FF2196F3"
        assertEquals(expected, ColorUtils.normalizeHex("#$input".removePrefix("#"))) // sanity
    }

}