package com.progmobile.lembraplus.ui.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.progmobile.lembraplus.data.repository.NoteRepository

class NoteViewModelFactory(
    private val repo: NoteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}