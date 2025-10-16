package com.progmobile.lembraplus.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object Formatters {

    fun dateInMillis(date: LocalDate?): Long? {
        return date?.atStartOfDay(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
    }

    fun Long?.toFormattedDateString(): String {
        val millis = this ?: System.currentTimeMillis()
        val instant = Instant.ofEpochMilli(millis)
        val localDate = instant.atZone(ZoneOffset.UTC).toLocalDate()
        return localDate.format(DateTimeFormatter.ofPattern("dd / MMM / yyyy"))
    }

    fun formattedTime(hour: Int, minute: Int): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val time = LocalTime.of(hour, minute).format(formatter)
        return time
    }
}