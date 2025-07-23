package com.example.mynotesapp.notes.data

import android.util.Log
import com.example.mynotesapp.database.FirebaseHelper
import com.example.mynotesapp.database.NotesDao
import com.example.mynotesapp.notes.domain.NotesItem
import com.example.mynotesapp.notes.domain.NotesRepository
import java.time.LocalDateTime
import javax.inject.Inject

class NotesRepoImp @Inject constructor(
    private val notesDao: NotesDao,
    private val firebaseDB: FirebaseHelper
) : NotesRepository {

    private val tag = "NotesRepoImp"

    override suspend fun addNoteItem(notesItem: NotesItem) {
        val newNote = notesItem.copy(
            updatedAt = notesItem.createdAt,
            isSynced = false
        )
        notesDao.addNoteItem(newNote)

        addNewNodeOnFirebase(newNote)
    }

    private suspend fun addNewNodeOnFirebase(note: NotesItem) {
        try {
            val updatedNote = note.copy(
                isSynced = true,
                lastSyncedAt = LocalDateTime.now()
            )

            firebaseDB.addNoteItem(updatedNote)

            updateNote(updatedNote)

        } catch (e: Exception) {
            Log.e(tag, e.message, e)
        }
    }

    private suspend fun syncLocalDatabaseWithFirebase() {
        val allNotes = notesDao.readAllNotes()
        allNotes.forEach { note ->
            if (!note.isSynced) {
                updateNoteOnFirebase(note)
            }
        }
    }


    override suspend fun readAllNotes(): List<NotesItem> {
        val allNotes = notesDao.readAllNotes()

        return allNotes
    }

    override suspend fun readNoteWithId(noteId: Long): NotesItem {
        return notesDao.readNoteWithId(noteId)
    }

    override suspend fun updateNote(notesItem: NotesItem) {
        notesDao.updateNote(notesItem)

        if (!notesItem.isSynced) {
            updateNoteOnFirebase(notesItem)
        }

    }

    private suspend fun updateNoteOnFirebase(notesItem: NotesItem) {
        val updatedNote = notesItem.copy(
            isSynced = true,
            lastSyncedAt = LocalDateTime.now()
        )

        try {
            firebaseDB.updateNote(updatedNote)
            updateNote(updatedNote)
        } catch (e: Exception) {
            Log.e(tag, e.message, e)
        }

    }


    override suspend fun deleteNote(notesItem: NotesItem) {
        //notesDao.deleteNote(notesItem)

        updateNote(
            notesItem.copy(
                isSynced = false,
                isDeleted = true,
                deletedAt = LocalDateTime.now()
            )
        )

    }

    private suspend fun deleteNoteOnFirebase(notesItem: NotesItem) {
        try {
            firebaseDB.deleteNote(notesItem)
        } catch (e: Exception) {
            Log.e(tag, e.message, e)
        }
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