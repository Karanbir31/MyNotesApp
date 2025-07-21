package com.example.mynotesapp.notes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mynotesapp.notes.domain.NotesItem


@Dao
interface NotesDao {

    @Insert
    suspend fun addNoteItem(notesItem: NotesItem)

    @Query("SELECT * FROM NOTES")
    suspend fun readAllNotes() : List<NotesItem>

    @Update
    suspend fun updateNote(notesItem: NotesItem)

    @Delete
    suspend fun deleteNote(notesItem: NotesItem)

}