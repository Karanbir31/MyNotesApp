package com.example.mynotesapp.notes.domain

interface NotesRepository {

    suspend fun addNoteItem(notesItem: NotesItem)

    suspend fun readAllNotes() : List<NotesItem>

    suspend fun updateNote(notesItem: NotesItem)

    suspend fun deleteNote(notesItem: NotesItem)

}