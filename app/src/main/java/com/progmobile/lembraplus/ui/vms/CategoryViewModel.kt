package com.progmobile.lembraplus.ui.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.progmobile.lembraplus.data.db.dao.CategoryWithTaskCount
import com.progmobile.lembraplus.data.db.model.Category
import com.progmobile.lembraplus.data.repository.CategoryRepository
import com.progmobile.lembraplus.utils.ColorUtils.normalizeHex
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoryWithTaskCount>>(emptyList())
    val categories: StateFlow<List<CategoryWithTaskCount>> = _categories

    private val _category = MutableStateFlow<Category?>(null)
    val category: StateFlow<Category?> = _category

    init {
        viewModelScope.launch { loadAll() }
    }

    suspend fun loadAll() {
        _categories.value = repository.allWithTaskCount()
    }

    suspend fun getById(id: Int){
        _category.value = repository.getById(id)
    }

    fun saveCategory(name: String, colorHex: String, id: Int) = viewModelScope.launch {
        val normalized = normalizeHex(colorHex)
        if (name.isBlank()) return@launch
        val cat = Category(id = id, name = name.trim(), colorHex = normalized)
        repository.save(cat)
        loadAll()
    }

    fun deleteCategory(id: Int) = viewModelScope.launch {
        repository.delete(id)
        loadAll()
    }

    fun updateCategory(name: String, colorHex: String, id: Int) = viewModelScope.launch {
        val normalized = normalizeHex(colorHex)
        if (name.isBlank()) return@launch
        val cat = Category(id = id, name = name.trim(), colorHex = normalized)
        repository.update(cat)
        loadAll()
    }

}
