package com.progmobile.lembraplus.data.db.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.progmobile.lembraplus.data.db.model.Category

data class CategoryWithNoteCount(
    @Embedded val category: Category,
    val noteCount: Int
)

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category): Long

    @Update
    suspend fun update(category: Category)

    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT c.*, (SELECT COUNT(*) FROM notes WHERE notes.category_id = c.id) as taskCount FROM categories c ORDER BY c.category_name COLLATE NOCASE")
    suspend fun getAllWithNoteCount(): List<CategoryWithNoteCount>

    @Query("SELECT * FROM categories WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Category?

}