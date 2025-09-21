package com.progmobile.lembraplus.data.db

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object Converters {
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME

    @TypeConverter
    @JvmStatic
    fun fromLocalDate(date: LocalDate?): String? = date?.format(dateFormatter)

    @TypeConverter
    @JvmStatic
    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it, dateFormatter) }

    @TypeConverter
    @JvmStatic
    fun fromLocalTime(time: LocalTime?): String? = time?.format(timeFormatter)

    @TypeConverter
    @JvmStatic
    fun toLocalTime(value: String?): LocalTime? = value?.let { LocalTime.parse(it, timeFormatter) }

}