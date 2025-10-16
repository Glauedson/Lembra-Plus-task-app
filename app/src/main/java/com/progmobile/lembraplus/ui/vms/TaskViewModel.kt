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

    init {
        viewModelScope.launch { loadAll() }
    }

    fun loadAll() = viewModelScope.launch {
        _tasksFixed.value = repository.getLatest()
        _allTasks.value = repository.getLatest()
    }

    fun loadAllOrderedByDate() = viewModelScope.launch {
        _allTasks.value = repository.getAllOrderedByDate()
    }

    fun loadByDate(date: LocalDate) = viewModelScope.launch {
        _allTasks.value = repository.getByDate(date)
    }

    fun loadByCategory(categoryId: Int) = viewModelScope.launch {
        _allTasks.value = repository.getByCategory(categoryId)
    }

    fun loadAllFixed() = viewModelScope.launch {
        _tasksFixed.value = repository.getAllFixed()
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
