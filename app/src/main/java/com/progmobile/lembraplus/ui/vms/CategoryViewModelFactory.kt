package com.progmobile.lembraplus.ui.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.progmobile.lembraplus.data.repository.CategoryRepository

class CategoryViewModelFactory(
    private val repository: CategoryRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}