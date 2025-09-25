package com.progmobile.lembraplus

import com.progmobile.lembraplus.data.db.dao.TaskDao
import com.progmobile.lembraplus.data.db.model.Task
import com.progmobile.lembraplus.data.repository.TaskRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.assertEquals

class FakeTaskDao : TaskDao {
    private val items = mutableListOf<Task>()

    override suspend fun insert(task: Task): Long {
        val nextId = (items.maxOfOrNull { it.id } ?: 0) + 1
        val copy = task.copy(id = nextId)
        items.add(copy)
        return nextId.toLong()
    }

    override suspend fun update(task: Task) {
        val idx = items.indexOfFirst { it.id == task.id }
        if (idx >= 0) items[idx] = task
    }

    override suspend fun delete(task: Task) {
        items.removeIf { it.id == task.id }
    }

    override suspend fun getAllOrdered(): List<Task> = items.sortedWith(
        compareBy<Task> { it.date ?: LocalDate.MAX }
            .thenBy { it.time ?: LocalTime.MAX }
    )

    override suspend fun getByDate(date: LocalDate): List<Task> = items.filter { it.date == date }.sortedBy { it.time }

    override suspend fun getByCategory(categoryId: Int): List<Task> =
        items.filter { it.categoryId == categoryId }
            .sortedWith(
                compareBy<Task> { it.date ?: LocalDate.MAX }
                    .thenBy { it.time ?: LocalTime.MAX }
            )
}

class TaskRepositoryTest {

    private val fakeDao = FakeTaskDao()
    private val repo = TaskRepository(fakeDao)

    @Test
    fun insertAndFetch() = runBlocking {
        val t = Task(id = 0, title = "T", description = null, categoryId = null, date = LocalDate.of(2025,9,22), time = LocalTime.of(10,0))
        repo.save(t)
        val all = repo.getAllOrdered()
        assertEquals(1, all.size)
        assertEquals("T", all[0].title)
    }

}