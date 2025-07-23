package com.example.mynotesapp.notedetails.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotesapp.notes.domain.NotesItem
import com.example.mynotesapp.notes.domain.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject constructor (private val repository: NotesRepository) : ViewModel() {
    private val logTag = "NoteDetailsViewModel"

    private val _note = MutableStateFlow<NotesItem>(NotesItem())
    val note: StateFlow<NotesItem> get() = _note

    private val _triggerSpeechRecognizer  = MutableSharedFlow<Unit>()
    val triggerSpeechRecognizer = _triggerSpeechRecognizer.asSharedFlow()


    fun readNoteWithId(noteId: Long) {
        viewModelScope.launch {
            try {
                _note.value = repository.readNoteWithId(noteId)
            } catch (e: Exception) {
                Log.e(logTag, e.message, e)
            }
        }
    }


    fun saveNewNote() {
        viewModelScope.launch {
            try {
                repository.addNoteItem(_note.value)
            } catch (e: Exception) {
                Log.e(logTag, e.message, e)
            }
        }
    }

    fun updateNoteOnDataBase() {
        viewModelScope.launch {
            try {
                repository.updateNote(_note.value)
            } catch (e: Exception) {
                Log.e(logTag, e.message, e)
            }
        }
    }

    fun updateLocalTitle(title: String) {
        _note.value = _note.value.copy(title = title, updatedAt = LocalDateTime.now())
    }

    fun updateLocalContent(title: String) {
        _note.value = _note.value.copy(content = title, updatedAt = LocalDateTime.now())
    }

    fun onClickMicButton(){
        viewModelScope.launch {
            _triggerSpeechRecognizer.emit(Unit)
        }
    }

    fun updateNoteContentWithSpokenText(spokenText : String){
        var updatedContent = _note.value.content
        updatedContent = "$updatedContent $spokenText"
        _note.value = _note.value.copy(content =updatedContent, updatedAt = LocalDateTime.now())
    }


}