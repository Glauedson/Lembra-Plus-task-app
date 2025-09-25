package com.progmobile.lembraplus

import com.progmobile.lembraplus.data.db.Converters
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.assertEquals

class ConvertersTest {

    private val converter = Converters

    @Test
    fun localDateRoundTrip() {
        val date = LocalDate.of(2025, 9, 21)
        val string = converter.fromLocalDate(date)
        val parsed = converter.toLocalDate(string)
        assertEquals(date, parsed)
    }

    @Test
    fun localTimeRoundTrip() {
        val time = LocalTime.of(14, 30)
        val string = converter.fromLocalTime(time)
        val parsed = converter.toLocalTime(string)
        assertEquals(time, parsed)
    }

    @Test
    fun nullRoundTrips() {
        assertEquals(null, converter.fromLocalDate(null))
        assertEquals(null, converter.toLocalDate(null))
        assertEquals(null, converter.fromLocalTime(null))
        assertEquals(null, converter.toLocalTime(null))
    }

}