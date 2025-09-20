package com.progmobile.lembraplus.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "category_name") val name: String,
    @ColumnInfo(name = "color_hex") val colorHex: String
)
