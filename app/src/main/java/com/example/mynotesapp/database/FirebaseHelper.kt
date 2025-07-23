package com.example.mynotesapp.database

import android.util.Log
import com.example.mynotesapp.notes.domain.NotesItem
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseHelper @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {

    private val notesCollectionName = "notes"

    suspend fun addNoteItem(notesItem: NotesItem ) {
        try {
            firebaseFirestore.collection(notesCollectionName)
                .document(notesItem.id.toString())
                .set(notesItem)
                .await()


        }catch (e : Exception){
            throw FirebaseException("failed to add new note", e)
        }
    }

    suspend fun readAllNotes(): List<NotesItem> {
        return try {
            firebaseFirestore.collection(notesCollectionName)
                .get()
                .await()
                .toObjects(NotesItem::class.java)
        } catch (e: Exception) {
            throw FirebaseException("Failed to read all notes", e)
        }
    }

    suspend fun readNoteWithId(noteId: Long): NotesItem? {
        return try {
            firebaseFirestore.collection(notesCollectionName)
                .document(noteId.toString())
                .get()
                .await()
                .toObject(NotesItem::class.java)
        } catch (e: Exception) {
            throw FirebaseException("Failed to read notes with id", e)
        }
    }

    suspend fun updateNote(notesItem: NotesItem) {
        try {
            firebaseFirestore.collection(notesCollectionName)
                .document(notesItem.id.toString())
                .set(notesItem)
                .await()
        } catch (e: Exception) {
            throw FirebaseException("Failed to update notes", e)
        }
    }

    suspend fun deleteNote(notesItem: NotesItem) {
        try {
            firebaseFirestore.collection(notesCollectionName)
                .document(notesItem.id.toString())
                .delete()
                .await()
        } catch (e: Exception) {
            throw FirebaseException("Failed to delete notes", e)
        }
    }

    suspend fun getAllUncheckedNotes(): List<NotesItem> {
        return try {
            firebaseFirestore.collection(notesCollectionName)
                .whereEqualTo("isChecklist", false)
                .get()
                .await()
                .toObjects(NotesItem::class.java)
        } catch (e: Exception) {
            throw FirebaseException("Failed to get all unchecked notes", e)
        }
    }

    suspend fun getAllCheckedNotes(): List<NotesItem> {
        return try {
            firebaseFirestore.collection(notesCollectionName)
                .whereEqualTo("isChecklist", true)
                .get()
                .await()
                .toObjects(NotesItem::class.java)
        } catch (e: Exception) {
            throw FirebaseException("Failed to get all checked notes", e)
        }
    }

    suspend fun getAllPinnedNotes(): List<NotesItem> {
        return try {
            firebaseFirestore.collection(notesCollectionName)
                .whereEqualTo("isPinned", true)
                .get()
                .await()
                .toObjects(NotesItem::class.java)
        } catch (e: Exception) {
            throw FirebaseException("Failed to get all pinned notes", e)
        }
    }


}