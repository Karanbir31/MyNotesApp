package com.example.mynotesapp.notes.ui

import com.example.mynotesapp.notes.data.NotesRepoImp
import com.example.mynotesapp.notes.domain.NotesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class NotesModules {

    @Binds
    abstract fun bindNotesRepo(
        notesRepoImp: NotesRepoImp
    ): NotesRepository

}