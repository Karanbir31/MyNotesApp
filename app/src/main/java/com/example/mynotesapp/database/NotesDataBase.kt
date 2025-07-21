package com.example.mynotesapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mynotesapp.notes.data.NotesDao
import com.example.mynotesapp.notes.domain.NotesItem

@Database(entities = [NotesItem::class], version = 1)
abstract class NotesDataBase() : RoomDatabase() {
    abstract val notesDao : NotesDao

}