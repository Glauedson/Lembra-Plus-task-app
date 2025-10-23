package com.progmobile.lembraplus

import com.progmobile.lembraplus.data.db.dao.NoteDao
import com.progmobile.lembraplus.data.db.model.Note
import com.progmobile.lembraplus.data.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.assertEquals

class FakeNoteDao : NoteDao {
    private val items = mutableListOf<Note>()

    override suspend fun insert(note: Note): Long {
        val nextId = (items.maxOfOrNull { it.id } ?: 0) + 1
        val copy = note.copy(id = nextId)
        items.add(copy)
        return nextId.toLong()
    }

    override suspend fun update(note: Note) {
        val idx = items.indexOfFirst { it.id == note.id }
        if (idx >= 0) items[idx] = note
    }

    override suspend fun delete(task: Int) {
        items.removeIf { it.id == task.id }
    }

    override suspend fun getAllNotesOrderedByDate(): List<Note> = items.sortedWith(
        compareBy<Note> { it.date ?: LocalDate.MAX }
            .thenBy { it.time ?: LocalTime.MAX }
    )

    override suspend fun getAllNotesByDate(date: LocalDate): List<Note> = items.filter { it.date == date }.sortedBy { it.time }

    override suspend fun getByCategory(categoryId: Int): List<Note> =
        items.filter { it.categoryId == categoryId }
            .sortedWith(
                compareBy<Note> { it.date ?: LocalDate.MAX }
                    .thenBy { it.time ?: LocalTime.MAX }
            )
}

class TaskRepositoryTest {

    private val fakeDao = FakeNoteDao()
    private val repo = NoteRepository(fakeDao)

    @Test
    fun insertAndFetch() = runBlocking {
        val t = Note(id = 0, title = "T", description = null, categoryId = null, date = LocalDate.of(2025,9,22), time = LocalTime.of(10,0))
        repo.save(t)
        val all = repo.getAllOrdered()
        assertEquals(1, all.size)
        assertEquals("T", all[0].title)
    }

}