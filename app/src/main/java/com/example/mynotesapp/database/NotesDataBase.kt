package com.example.mynotesapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mynotesapp.notes.domain.TimestampTypeConverter
import com.example.mynotesapp.notes.domain.NotesItem

@Database(entities = [NotesItem::class], version = 2)
@TypeConverters(TimestampTypeConverter::class)
abstract class NotesDataBase() : RoomDatabase() {
    abstract val notesDao : NotesDao

}