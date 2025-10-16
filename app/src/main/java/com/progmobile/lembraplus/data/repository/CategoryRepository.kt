package com.progmobile.lembraplus.data.repository

import com.progmobile.lembraplus.data.db.dao.CategoryDao
import com.progmobile.lembraplus.data.db.dao.CategoryWithTaskCount
import com.progmobile.lembraplus.data.db.model.Category

class CategoryRepository(private val dao: CategoryDao) {

    suspend fun allWithTaskCount(): List<CategoryWithTaskCount> = dao.getAllWithTaskCount()
    suspend fun all(): List<Category> = dao.getAll()
    suspend fun get(id: Int): Category? = dao.getById(id)
    suspend fun save(category: Category): Long = dao.insert(category)
    suspend fun update(category: Category) = dao.update(category)
    suspend fun delete(id: Int) = dao.delete(id)
    suspend fun search(query: String): List<Category> = dao.searchByName("%$query%")

}