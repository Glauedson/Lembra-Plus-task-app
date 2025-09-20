package com.progmobile.lembraplus.ui.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.progmobile.lembraplus.data.db.model.Category
import com.progmobile.lembraplus.data.repository.CategoryRepository
import com.progmobile.lembraplus.utils.ColorUtils.normalizeHex
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository): ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init {
        viewModelScope.launch { loadAll() }
    }

    suspend fun loadAll(){
        _categories.value = repository.all()
    }

    fun refresh() = viewModelScope.launch { loadAll() }

    fun saveCategory(name: String, colorHex: String, id: Int) = viewModelScope.launch {
        val normalized = normalizeHex(colorHex)
        if (name.isBlank()) return@launch
        val cat = Category(id = id, name = name.trim(), colorHex = normalized)
        repository.save(cat)
        loadAll()
    }

    fun deleteCategory(category: Category) = viewModelScope.launch {
        repository.delete(category)
        loadAll()
    }

    fun updateCategory(name: String, colorHex: String, id: Int) = viewModelScope.launch {
        val normalized = normalizeHex(colorHex)
        if (name.isBlank()) return@launch
        val cat = Category(id = id, name = name.trim(), colorHex = normalized)
        repository.update(cat)
        loadAll()
    }

    fun searchCategory(query: String) = viewModelScope.launch {
        repository.search(query)
        loadAll()
    }

}