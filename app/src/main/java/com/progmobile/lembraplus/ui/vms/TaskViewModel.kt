package com.progmobile.lembraplus.ui.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.progmobile.lembraplus.data.db.model.Task
import com.progmobile.lembraplus.data.db.model.TaskWithCategory
import com.progmobile.lembraplus.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(private val repository: TaskRepository): ViewModel(){

    private val _tasksFixed = MutableStateFlow<List<TaskWithCategory>>(emptyList())
    val tasksFixed: StateFlow<List<TaskWithCategory>> = _tasksFixed

    private val _allTasks = MutableStateFlow<List<TaskWithCategory>>(emptyList())
    val allTasks: StateFlow<List<TaskWithCategory>> = _allTasks

    private val _searchResults = MutableStateFlow<List<TaskWithCategory>>(emptyList())
    val searchResults: StateFlow<List<TaskWithCategory>> = _searchResults

    init {
        viewModelScope.launch { loadAllTasks(); loadAllFixed() }
    }

    suspend fun getTaskById(taskId: Int): TaskWithCategory? {
        return repository.getTaskById(taskId)
    }

    fun searchTasks(query: String) = viewModelScope.launch {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
        } else {
            _searchResults.value = repository.searchTasks(query)
        }
    }

    fun loadAllTasks() = viewModelScope.launch {
        _allTasks.value = repository.getLatest()
    }
    fun loadAllFixed() = viewModelScope.launch {
        _tasksFixed.value = repository.getAllFixed()
    }

    fun addTask(task: Task) = viewModelScope.launch {
        repository.save(task)
        loadAllTasks()
        loadAllFixed()
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        repository.update(task)
        loadAllTasks()
        loadAllFixed()
    }

    fun deleteTask(task: Int) = viewModelScope.launch {
        repository.delete(task)
        loadAllTasks()
        loadAllFixed()
    }

}
