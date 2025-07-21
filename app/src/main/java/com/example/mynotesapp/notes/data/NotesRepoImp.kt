package com.example.mynotesapp.notes.data

import com.example.mynotesapp.notes.domain.NotesItem
import com.example.mynotesapp.notes.domain.NotesRepository
import javax.inject.Inject

class NotesRepoImp @Inject constructor(private val notesDao: NotesDao) : NotesRepository {

    override suspend fun addNoteItem(notesItem: NotesItem) {
        notesDao.addNoteItem(notesItem)
    }

    override suspend fun readAllNotes(): List<NotesItem> {
       return notesDao.readAllNotes()
    }

    override suspend fun updateNote(notesItem: NotesItem) {
       notesDao.updateNote(notesItem)
    }

    override suspend fun deleteNote(notesItem: NotesItem) {
        notesDao.deleteNote(notesItem)
    }
}