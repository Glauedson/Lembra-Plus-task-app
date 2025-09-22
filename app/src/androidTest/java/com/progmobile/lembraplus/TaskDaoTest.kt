package com.progmobile.lembraplus

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.progmobile.lembraplus.data.db.AppDatabase
import com.progmobile.lembraplus.data.db.dao.TaskDao
import com.progmobile.lembraplus.data.db.model.Category
import com.progmobile.lembraplus.data.db.model.Task
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class TaskDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: TaskDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries()
            .build()
        dao = db.taskDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndQueryOrderingAndFilters() = runBlocking {

        val categoryDao = db.categoryDao()
        val category = Category(id = 1, name = "Test", colorHex = "#FFFFFF")
        categoryDao.insert(category)

        val t1 = Task(
            id = 0,
            title = "A",
            description = null,
            categoryId = 1,
            date = LocalDate.of(2025, 9, 21),
            time = LocalTime.of(9, 0)
        )
        val t2 = Task(
            id = 0,
            title = "B",
            description = null,
            categoryId = 1,
            date = LocalDate.of(2025, 9, 21),
            time = LocalTime.of(8, 0)
        )
        dao.insert(t1)
        dao.insert(t2)

        val all = dao.getAllOrdered()
        // expect t2 (8:00) before t1 (9:00)
        assertEquals(2, all.size)
        assertEquals("B", all[0].title)
        assertEquals("A", all[1].title)

        val byDate = dao.getByDate(LocalDate.of(2025, 9, 21))
        assertEquals(2, byDate.size)

        val byCategory = dao.getByCategory(1)
        assertEquals(2, byCategory.size)

    }

}