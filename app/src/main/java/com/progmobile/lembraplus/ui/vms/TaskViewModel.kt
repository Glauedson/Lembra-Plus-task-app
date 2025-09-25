package com.progmobile.lembraplus.ui.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.progmobile.lembraplus.data.db.model.Task
import com.progmobile.lembraplus.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(private val repository: TaskRepository): ViewModel(){

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        viewModelScope.launch { loadAll() }
    }

    fun loadAll() = viewModelScope.launch {
        _tasks.value = repository.getAllOrdered()
    }

    fun loadByDate(date: LocalDate) = viewModelScope.launch {
        _tasks.value = repository.getByDate(date)
    }

    fun loadByCategory(categoryId: Int) = viewModelScope.launch {
        _tasks.value = repository.getByCategory(categoryId)
    }

    fun addTask(task: Task) = viewModelScope.launch {
        repository.save(task)
        loadAll()
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        repository.update(task)
        loadAll()
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        repository.delete(task)
        loadAll()
    }

}