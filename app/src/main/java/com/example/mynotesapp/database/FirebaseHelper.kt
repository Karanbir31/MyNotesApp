package com.example.mynotesapp.database

import com.example.mynotesapp.notes.domain.NotesItem
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseHelper @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {

    private fun getNotesSubCollection(): CollectionReference {
        val userId = firebaseAuth.currentUser?.uid
            ?: throw IllegalStateException("User not authenticated")

        return firebaseFirestore.collection("notes")
            .document(userId)
            .collection("users_notes")
    }

    suspend fun addNoteItem(notesItem: NotesItem) {
        try {
            getNotesSubCollection()
                .document(notesItem.id.toString())
                .set(notesItem)
                .await()
        } catch (e: Exception) {
            throw Exception("Failed to add new note", e)
        }
    }

    suspend fun readAllNotes(): List<NotesItem> {
        return try {
            getNotesSubCollection()
                .get()
                .await()
                .toObjects(NotesItem::class.java)
        } catch (e: Exception) {
            throw Exception("Failed to read all notes", e)
        }
    }

    suspend fun readNoteWithId(noteId: Long): NotesItem? {
        return try {
            getNotesSubCollection()
                .document(noteId.toString())
                .get()
                .await()
                .toObject(NotesItem::class.java)
        } catch (e: Exception) {
            throw Exception("Failed to read note with id", e)
        }
    }

    suspend fun updateNote(notesItem: NotesItem) {
        try {
            getNotesSubCollection()
                .document(notesItem.id.toString())
                .set(notesItem)
                .await()
        } catch (e: Exception) {
            throw Exception("Failed to update note", e)
        }
    }

    suspend fun deleteNote(notesItem: NotesItem) {
        try {
            getNotesSubCollection()
                .document(notesItem.id.toString())
                .delete()
                .await()
        } catch (e: Exception) {
            throw Exception("Failed to delete note", e)
        }
    }

    suspend fun getAllUncheckedNotes(): List<NotesItem> {
        return try {
            getNotesSubCollection()
                .whereEqualTo("isChecklist", false)
                .get()
                .await()
                .toObjects(NotesItem::class.java)
        } catch (e: Exception) {
            throw Exception("Failed to get unchecked notes", e)
        }
    }

    suspend fun getAllCheckedNotes(): List<NotesItem> {
        return try {
            getNotesSubCollection()
                .whereEqualTo("isChecklist", true)
                .get()
                .await()
                .toObjects(NotesItem::class.java)
        } catch (e: Exception) {
            throw Exception("Failed to get checked notes", e)
        }
    }

    suspend fun getAllPinnedNotes(): List<NotesItem> {
        return try {
            getNotesSubCollection()
                .whereEqualTo("isPinned", true)
                .get()
                .await()
                .toObjects(NotesItem::class.java)
        } catch (e: Exception) {
            throw Exception("Failed to get pinned notes", e)
        }
    }
}
