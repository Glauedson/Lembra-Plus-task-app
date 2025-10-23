package com.progmobile.lembraplus.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.progmobile.lembraplus.data.db.dao.CategoryDao
import com.progmobile.lembraplus.data.db.dao.NoteDao
import com.progmobile.lembraplus.data.db.model.Category
import com.progmobile.lembraplus.data.db.model.Note

@Database(entities = [Category::class, Note::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun taskDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE tasks ADD COLUMN is_fixed INTEGER NOT NULL DEFAULT 0")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                        CREATE TABLE notes_new (
                            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                            note_title TEXT NOT NULL,
                            note_description TEXT,
                            category_id INTEGER,
                            note_date TEXT,
                            note_time TEXT,
                            is_pinned INTEGER NOT NULL,
                            created_at INTEGER NOT NULL,
                            FOREIGN KEY(category_id) REFERENCES categories(id) ON DELETE SET NULL
                        )
                    """
                )
                db.execSQL(
                    """
                        INSERT INTO notes_new (id, note_title, note_description, category_id, note_date, note_time, is_pinned, created_at)
                        SELECT id, task_tile, task_description, category_id, task_date, task_time, is_fixed, created_at FROM tasks
                    """
                )
                db.execSQL("DROP TABLE tasks")
                db.execSQL("ALTER TABLE notes_new RENAME TO notes")
            }
        }


        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lembraplus.db"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
