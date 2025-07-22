package com.example.mynotesapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mynotesapp.notes.domain.LocalDateTimeTypeConvertor
import com.example.mynotesapp.notes.domain.NotesItem

@Database(entities = [NotesItem::class], version = 1)
@TypeConverters(LocalDateTimeTypeConvertor::class)
abstract class NotesDataBase() : RoomDatabase() {
    abstract val notesDao : NotesDao

}