package com.progmobile.lembraplus.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.progmobile.lembraplus.data.db.model.Note
import com.progmobile.lembraplus.data.db.model.NoteWithCategory
import java.time.LocalDate

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun delete(noteId: Int)

    @Transaction
    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): NoteWithCategory?

    @Transaction
    @Query("SELECT * FROM notes ORDER BY created_at DESC")
    suspend fun getLatestNotes(): List<NoteWithCategory>

    @Query("SELECT * FROM notes ORDER BY note_date IS NULL, note_date, note_time")
    suspend fun getAllNotesOrderedByDate(): List<NoteWithCategory>

    @Query("SELECT * FROM notes WHERE note_date = :date ORDER BY note_time")
    suspend fun getAllNotesByDate(date: LocalDate): List<NoteWithCategory>

    @Query("SELECT * FROM notes WHERE is_pinned = 1 ORDER BY note_date, note_time")
    suspend fun getAllPinnedNotes(): List<NoteWithCategory>

    @Transaction
    @Query("SELECT * FROM notes WHERE note_title LIKE :query ORDER BY is_pinned DESC, created_at DESC")
    suspend fun searchNotesByTitle(query: String): List<NoteWithCategory>

}
