package com.example.mynotesapp.notes.data

import com.example.mynotesapp.database.NotesDao
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

    override suspend fun readNoteWithId(noteId: Long): NotesItem {
        return notesDao.readNoteWithId(noteId)
    }

    override suspend fun updateNote(notesItem: NotesItem) {
       notesDao.updateNote(notesItem)
    }

    override suspend fun deleteNote(notesItem: NotesItem) {
        notesDao.deleteNote(notesItem)
    }

    override suspend fun getAllUncheckedNotes(): List<NotesItem> {
        return notesDao.getAllUncheckedNotes()
    }

    override suspend fun getAllCheckedNotes(): List<NotesItem> {
        return notesDao.getAllCheckedNotes()
    }

    override suspend fun getAllPinnedNotes(): List<NotesItem> {
        return notesDao.getAllPinnedNotes()
    }
}