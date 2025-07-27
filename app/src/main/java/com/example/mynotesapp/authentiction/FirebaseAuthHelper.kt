package com.example.mynotesapp.authentiction

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FirebaseAuthHelper @Inject constructor(
    private val auth: FirebaseAuth
) {

    // Sign In user
    suspend fun signInWithEmailPassword(
        email: String,
        password: String
    ): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {

            false
        }
    }

    // Create user
    suspend fun createUserInWithEmailPassword(
        email: String,
        password: String
    ): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
