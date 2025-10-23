package com.progmobile.lembraplus.data.repository

import com.progmobile.lembraplus.data.db.dao.NoteDao
import com.progmobile.lembraplus.data.db.model.Note
import com.progmobile.lembraplus.data.db.model.NoteWithCategory

open class NoteRepository(private val dao: NoteDao) {

    suspend fun save(note: Note): Long = dao.insert(note)

    suspend fun update(note: Note) = dao.update(note)

    suspend fun delete(id: Int) = dao.delete(id)

    suspend fun getNoteById(noteId: Int): NoteWithCategory? = dao.getNoteById(noteId)

    suspend fun searchNotesByTitle(query: String): List<NoteWithCategory> = dao.searchNotesByTitle("%$query%")

    suspend fun getLatest(): List<NoteWithCategory> = dao.getLatestNotes()

    suspend fun getAllPinned(): List<NoteWithCategory> = dao.getAllPinnedNotes()

}