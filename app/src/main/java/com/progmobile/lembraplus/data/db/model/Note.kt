package com.progmobile.lembraplus.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDate
import java.time.LocalTime

data class NoteWithCategory(
    @Embedded val note: Note,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: Category?
)

@Entity(
    tableName = "notes",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("note_title") val title: String,
    @ColumnInfo("note_description") val description: String? = null,
    @ColumnInfo("category_id") val categoryId: Int? = null,
    @ColumnInfo("note_date") val date: LocalDate? = null,
    @ColumnInfo("note_time") val time: LocalTime? = null,
    @ColumnInfo("is_pinned") val isPinned: Boolean = false,
    @ColumnInfo("created_at") val createdAt: Long = System.currentTimeMillis()
)
