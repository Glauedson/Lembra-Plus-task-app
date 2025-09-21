package com.progmobile.lembraplus.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.progmobile.lembraplus.data.db.dao.CategoryDao
import com.progmobile.lembraplus.data.db.dao.TaskDao
import com.progmobile.lembraplus.data.db.model.Category
import com.progmobile.lembraplus.data.db.model.Task

@Database(entities = [Category::class, Task::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun taskDao(): TaskDao
}
