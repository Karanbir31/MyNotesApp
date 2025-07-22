package com.example.mynotesapp

import android.content.Context
import androidx.room.Room
import com.example.mynotesapp.database.NotesDataBase
import com.example.mynotesapp.database.NotesDao
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


}