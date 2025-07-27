package com.example.mynotesapp

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.mynotesapp.database.NotesDao
import com.example.mynotesapp.database.NotesDataBase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModules {

    @Provides
    @Singleton
    fun provideApplicationContext(
        @ApplicationContext context: Context
    ): Context {
        return context
    }


    @Provides
    @Singleton
    fun providerRoomDataBase(
        @ApplicationContext context: Context
    ): NotesDataBase {
        return Room.databaseBuilder(
            context,
            NotesDataBase::class.java,
            "Notes_DataBase"
        ).build()
    }


    @Provides
    @Singleton
    fun providesNotesDao(
        notesDataBase: NotesDataBase
    ): NotesDao {
        return notesDataBase.notesDao
    }


    @Provides
    @Singleton
    fun providesFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun providesSharedPref(
        @ApplicationContext context : Context
    ): SharedPreferences {
        val sharedPerfName = "notes_pref"
        return context.getSharedPreferences(sharedPerfName, Context.MODE_PRIVATE )
    }


}