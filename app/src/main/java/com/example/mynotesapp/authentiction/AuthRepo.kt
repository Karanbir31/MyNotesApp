package com.example.mynotesapp.authentiction

import android.content.Context
import com.example.mynotesapp.database.FirebaseHelper
import com.example.mynotesapp.database.NotesDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthRepo @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseAuthHelper: FirebaseAuthHelper,
    private val firebaseHelper: FirebaseHelper,
    private val notesDao: NotesDao
) {

    suspend fun signInUserWithEmailPassword(
        email: String, password: String
    ) {
        val isSuccess = firebaseAuthHelper.signInWithEmailPassword(email, password)
        if (isSuccess) {
            syncFirebaseWithRoom()
        }
    }

    suspend fun createUserWithEmailPassword(
        email: String, password: String
    ) {
        val isSuccess = firebaseAuthHelper.createUserInWithEmailPassword(email, password)
    }

    private suspend fun syncFirebaseWithRoom() {
        val notes = firebaseHelper.readAllNotes()

        notes.forEach { note ->
            notesDao.addNoteItem(note)
        }
    }
}