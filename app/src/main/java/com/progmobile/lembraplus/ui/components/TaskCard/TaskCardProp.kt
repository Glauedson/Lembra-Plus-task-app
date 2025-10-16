package com.progmobile.lembraplus.ui.components

import java.time.LocalDate
import java.time.LocalTime

data class TaskCardProps(
    val id: String,
    val title: String,
    val description: String?,
    val categoryName: String?,
    val categoryColorHex: String?,
    val createdAt: Long,
    val date: LocalDate?,
    val time: LocalTime?,
    val width: Int = 0,
    val isPinned: Boolean = false
)