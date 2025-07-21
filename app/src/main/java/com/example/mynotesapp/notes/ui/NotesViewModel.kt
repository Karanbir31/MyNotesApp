package com.example.mynotesapp.notes.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotesapp.notes.domain.NotesItem
import com.example.mynotesapp.notes.domain.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class NotesItemState() {
    object Loading : NotesItemState()
    class Success(val data: NotesItem) : NotesItemState()
    class Error(val errorMsg: String) : NotesItemState()
}

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {
    private val logTag = "NotesViewModel"

    private val _allNotes = MutableStateFlow<List<NotesItem>>(emptyList())
    val allNotes: StateFlow<List<NotesItem>> get() = _allNotes


    fun addNoteItem(notesItem: NotesItem) {
        viewModelScope.launch {
            try {
                repository.addNoteItem(notesItem)
            } catch (e: Exception) {
                Log.e(logTag, e.message, e)
            }
        }
    }

    fun readAllNotes() {
        viewModelScope.launch {
            try {
                _allNotes.value = repository.readAllNotes()
            } catch (e: Exception) {
                Log.e(logTag, e.message, e)
            }
        }
    }

    fun updateNote(notesItem: NotesItem) {
        viewModelScope.launch {
            try {
                repository.updateNote(notesItem)
            } catch (e: Exception) {
                Log.e(logTag, e.message, e)
            }
        }
    }

    fun deleteNote(notesItem: NotesItem) {
        viewModelScope.launch {
            try {
                repository.updateNote(notesItem)
            } catch (e: Exception) {
                Log.e(logTag, e.message, e)
            }
        }
    }


}