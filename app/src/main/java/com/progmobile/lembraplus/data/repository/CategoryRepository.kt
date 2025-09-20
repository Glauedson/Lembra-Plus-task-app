package com.progmobile.lembraplus.data.repository

import com.progmobile.lembraplus.data.db.dao.CategoryDao
import com.progmobile.lembraplus.data.db.model.Category

class CategoryRepository(private val dao: CategoryDao) {

    suspend fun all(): List<Category> = dao.getAll()
    suspend fun get(id: Int): Category? = dao.getById(id)
    suspend fun save(category: Category): Long = dao.insert(category)
    suspend fun update(category: Category) = dao.update(category)
    suspend fun delete(category: Category) = dao.delete(category)
    suspend fun search(query: String): List<Category> = dao.searchByName("%$query%")

}