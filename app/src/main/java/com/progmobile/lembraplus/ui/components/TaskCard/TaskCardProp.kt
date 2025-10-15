package com.progmobile.lembraplus.ui.components

data class TaskCardProps(
    val title: String,
    val description: String,
    val categoryName: String,
    val categoryColorHex: String,
    val width: Int = 0,
    val isPinned: Boolean = false
)