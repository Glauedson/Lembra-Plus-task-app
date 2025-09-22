package com.progmobile.lembraplus

import com.progmobile.lembraplus.data.db.model.Task
import com.progmobile.lembraplus.data.repository.TaskRepository
import com.progmobile.lembraplus.ui.vms.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadAll_updatesStateFlow() = runTest {
        val t1 = Task(
            id = 1,
            title = "X",
            description = null,
            categoryId = null,
            date = LocalDate.now(),
            time = LocalTime.of(12, 0)
        )

        val fakeRepo = object : TaskRepository(FakeTaskDao()) {
            override suspend fun getAllOrdered(): List<Task> = listOf(t1)
        }

        val vm = TaskViewModel(fakeRepo)
        vm.loadAll()

        advanceUntilIdle()

        assertEquals(1, vm.tasks.value.size)
        assertEquals("X", vm.tasks.value[0].title)
    }

}