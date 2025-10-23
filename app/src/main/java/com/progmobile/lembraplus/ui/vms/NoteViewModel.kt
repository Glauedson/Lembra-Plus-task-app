package com.progmobile.lembraplus.ui.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.progmobile.lembraplus.data.db.model.Note
import com.progmobile.lembraplus.data.db.model.NoteWithCategory
import com.progmobile.lembraplus.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    private val _pinnedNotes = MutableStateFlow<List<NoteWithCategory>>(emptyList())
    val pinnedNotes: StateFlow<List<NoteWithCategory>> = _pinnedNotes

    private val _allNotes = MutableStateFlow<List<NoteWithCategory>>(emptyList())
    val allNotes: StateFlow<List<NoteWithCategory>> = _allNotes

    private val _searchResults = MutableStateFlow<List<NoteWithCategory>>(emptyList())
    val searchResults: StateFlow<List<NoteWithCategory>> = _searchResults

    init {
        viewModelScope.launch { loadAllNotes(); loadAllPinned() }
    }

    fun loadAllNotes() = viewModelScope.launch {
        _allNotes.value = repository.getLatest()
    }

    fun loadAllPinned() = viewModelScope.launch {
        _pinnedNotes.value = repository.getAllPinned()
    }

    fun saveNote(note: Note) = viewModelScope.launch {
        repository.save(note)
        loadAllNotes()
        loadAllPinned()
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.update(note)
        loadAllNotes()
        loadAllPinned()
    }

    fun deleteNote(id: Int) = viewModelScope.launch {
        repository.delete(id)
        loadAllNotes()
        loadAllPinned()
    }

    fun searchNotes(query: String) = viewModelScope.launch {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
        } else {
            _searchResults.value = repository.searchNotesByTitle(query)
        }
    }

    suspend fun getNoteById(noteId: Int): NoteWithCategory? {
        return repository.getNoteById(noteId)
    }

}
