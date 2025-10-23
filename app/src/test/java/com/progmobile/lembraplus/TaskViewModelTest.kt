package com.progmobile.lembraplus

import com.progmobile.lembraplus.data.db.model.Note
import com.progmobile.lembraplus.data.repository.NoteRepository
import com.progmobile.lembraplus.ui.vms.NoteViewModel
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
        val t1 = Note(
            id = 1,
            title = "X",
            description = null,
            categoryId = null,
            date = LocalDate.now(),
            time = LocalTime.of(12, 0)
        )

        val fakeRepo = object : NoteRepository(FakeNoteDao()) {
            override suspend fun getAllOrdered(): List<Note> = listOf(t1)
        }

        val vm = NoteViewModel(fakeRepo)
        vm.loadAll()

        advanceUntilIdle()

        assertEquals(1, vm.tasks.value.size)
        assertEquals("X", vm.tasks.value[0].title)
    }

}