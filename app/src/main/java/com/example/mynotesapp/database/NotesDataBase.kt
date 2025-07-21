package com.example.mynotesapp.database

import androidx.room.Database
import com.example.mynotesapp.notes.data.NotesDao
import com.example.mynotesapp.notes.domain.NotesItem

@Database(entities = [NotesItem::class], version = 1)
abstract class NotesDataBase {
    abstract val notesDao : NotesDao

}