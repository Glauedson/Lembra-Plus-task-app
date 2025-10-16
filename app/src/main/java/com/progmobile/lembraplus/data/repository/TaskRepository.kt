package com.progmobile.lembraplus.data.repository

import com.progmobile.lembraplus.data.db.dao.TaskDao
import com.progmobile.lembraplus.data.db.model.Task
import com.progmobile.lembraplus.data.db.model.TaskWithCategory
import java.time.LocalDate

open class TaskRepository(private val dao: TaskDao){

    suspend fun save(task: Task): Long = dao.insert(task)

    suspend fun update(task: Task) = dao.update(task)

    suspend fun delete(task: Int) = dao.delete(task)

    open suspend fun getAllOrderedByDate(): List<TaskWithCategory> = dao.getAllOrdered()

    suspend fun  getLatest(): List<TaskWithCategory> = dao.getLatest()

    suspend fun getByCategory(categoryId: Int): List<TaskWithCategory> = dao.getByCategory(categoryId)

    suspend fun getByDate(date: LocalDate): List<TaskWithCategory> = dao.getByDate(date)

    suspend fun getAllFixed(): List<TaskWithCategory> = dao.getAllFixed()

}