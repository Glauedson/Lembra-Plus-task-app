package com.progmobile.lembraplus.ui.components

data class TaskCardProps(
    val id: String,
    val title: String,
    val description: String?,
    val categoryName: String?,
    val categoryColorHex: String?,
    val createdAt: String?,
    val date: String?,
    val time: String?,
    val width: Int = 0,
    val isPinned: Boolean = false
)