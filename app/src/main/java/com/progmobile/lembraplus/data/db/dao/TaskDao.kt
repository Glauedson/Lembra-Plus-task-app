package com.progmobile.lembraplus.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.progmobile.lembraplus.data.db.model.Task
import java.time.LocalDate

@Dao
interface TaskDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM tasks ORDER BY task_date IS NULL, task_date, task_time")
    suspend fun getAllOrdered(): List<Task>

    @Query("SELECT * FROM tasks WHERE task_date = :date ORDER BY task_time")
    suspend fun getByDate(date: LocalDate): List<Task>

    @Query("SELECT * FROM tasks WHERE category_id = :categoryId ORDER BY task_date, task_time")
    suspend fun getByCategory(categoryId: Int): List<Task>

}
