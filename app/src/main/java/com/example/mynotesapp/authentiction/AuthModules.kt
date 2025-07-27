package com.example.mynotesapp.authentiction

import android.content.Context
import com.example.mynotesapp.database.FirebaseHelper
import com.example.mynotesapp.database.NotesDao
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModules {

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepo(
        context: Context,
        firebaseAuthHelper: FirebaseAuthHelper,
        firebaseHelper: FirebaseHelper,
        notesDao: NotesDao
    ): AuthRepo {
        return AuthRepo(
            context, firebaseAuthHelper, firebaseHelper, notesDao
        )
    }


}