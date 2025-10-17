package com.progmobile.lembraplus.ui.components.CategoryCard

data class CategoryCardProps(
    val id: Int,
    val name: String,
    val quant: Int = 0,
    val colorHex: String
)