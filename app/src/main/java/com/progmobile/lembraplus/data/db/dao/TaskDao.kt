package com.progmobile.lembraplus.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.progmobile.lembraplus.data.db.model.Task
import com.progmobile.lembraplus.data.db.model.TaskWithCategory
import java.time.LocalDate

@Dao
interface TaskDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long

    @Update
    suspend fun update(task: Task)

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun delete(id: Int)

    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): TaskWithCategory?

    @Transaction
    @Query("SELECT * FROM tasks WHERE task_tile LIKE :query ORDER BY is_fixed DESC, created_at DESC")
    suspend fun searchTasks(query: String): List<TaskWithCategory>

    @Transaction
    @Query("SELECT * FROM tasks ORDER BY created_at DESC")
    suspend fun getLatestWithCategory(): List<TaskWithCategory>

    @Transaction
    @Query("SELECT * FROM tasks ORDER BY created_at DESC")
    suspend fun getLatest(): List<TaskWithCategory>

    @Query("SELECT * FROM tasks ORDER BY task_date IS NULL, task_date, task_time")
    suspend fun getAllOrdered(): List<TaskWithCategory>

    @Query("SELECT * FROM tasks WHERE task_date = :date ORDER BY task_time")
    suspend fun getByDate(date: LocalDate): List<TaskWithCategory>

    @Query("SELECT * FROM tasks WHERE category_id = :categoryId ORDER BY task_date, task_time")
    suspend fun getByCategory(categoryId: Int): List<TaskWithCategory>

    @Query("SELECT * FROM tasks WHERE is_fixed = 1 ORDER BY task_date, task_time")
    suspend fun getAllFixed(): List<TaskWithCategory>

}
