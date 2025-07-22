package com.example.mynotesapp.notes.domain

interface NotesRepository {

    suspend fun addNoteItem(notesItem: NotesItem)

    suspend fun readAllNotes() : List<NotesItem>

    suspend fun readNoteWithId(noteId : Long) : NotesItem

    suspend fun updateNote(notesItem: NotesItem)

    suspend fun deleteNote(notesItem: NotesItem)

    suspend fun getAllUncheckedNotes() : List<NotesItem>

    suspend fun getAllCheckedNotes() : List<NotesItem>

    suspend fun getAllPinnedNotes() : List<NotesItem>

}