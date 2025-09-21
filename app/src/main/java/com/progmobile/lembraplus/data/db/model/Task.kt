package com.progmobile.lembraplus.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["uid"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("task_tile") val title: String,
    @ColumnInfo("task_description") val description: String? = null,
    @ColumnInfo("category_id") val categoryId: Int? = null,
    @ColumnInfo("task_date") val date: LocalDate? = null,
    @ColumnInfo("task_time") val time: LocalTime? = null,
    @ColumnInfo("created_at") val createdAt: Long = System.currentTimeMillis()
)