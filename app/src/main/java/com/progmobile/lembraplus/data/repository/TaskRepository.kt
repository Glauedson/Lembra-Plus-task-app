package com.progmobile.lembraplus.data.repository

import com.progmobile.lembraplus.data.db.dao.TaskDao
import com.progmobile.lembraplus.data.db.model.Task
import com.progmobile.lembraplus.data.db.model.TaskWithCategory
import java.time.LocalDate

open class TaskRepository(private val dao: TaskDao){

    suspend fun save(task: Task): Long = dao.insert(task)

    suspend fun update(task: Task) = dao.update(task)

    suspend fun delete(id: Int) = dao.delete(id)

    suspend fun getTaskById(taskId: Int): TaskWithCategory? = dao.getTaskById(taskId)


    suspend fun searchTasks(query: String): List<TaskWithCategory> = dao.searchTasks("%$query%")

    suspend fun  getLatest(): List<TaskWithCategory> = dao.getLatest()

    suspend fun getAllFixed(): List<TaskWithCategory> = dao.getAllFixed()

}