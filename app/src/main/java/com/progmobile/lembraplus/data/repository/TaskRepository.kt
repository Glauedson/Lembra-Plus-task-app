package com.progmobile.lembraplus.data.repository

import com.progmobile.lembraplus.data.db.dao.TaskDao
import com.progmobile.lembraplus.data.db.model.Task
import java.sql.Date
import java.time.LocalDate

open class TaskRepository(private val dao: TaskDao){

    suspend fun save(task: Task): Long = dao.insert(task)

    suspend fun update(task: Task) = dao.update(task)

    suspend fun delete(task: Task) = dao.delete(task)

    open suspend fun getAllOrdered(): List<Task> = dao.getAllOrdered()

    suspend fun getByCategory(categoryId: Int): List<Task> = dao.getByCategory(categoryId)

    suspend fun getByDate(date: LocalDate): List<Task> = dao.getByDate(date)

}