package com.example.mynotesapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mynotesapp.notes.domain.NotesItem


@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNoteItem(notesItem: NotesItem)

    @Query("SELECT * FROM notes WHERE isDeleted = 0 ORDER BY updatedAt DESC")
    suspend fun readAllNotes() : List<NotesItem>

    @Query("SELECT * FROM notes WHERE id = :noteId  ORDER BY updatedAt DESC")
    suspend fun readNoteWithId(noteId : Long) : NotesItem

    @Update
    suspend fun updateNote(notesItem: NotesItem)

    @Delete
    suspend fun deleteNote(notesItem: NotesItem)

    @Query("SELECT * FROM notes WHERE isChecklist = 0")
    suspend fun getAllUncheckedNotes() : List<NotesItem>

    @Query("SELECT * FROM notes WHERE isChecklist = 1")
    suspend fun getAllCheckedNotes() : List<NotesItem>

    @Query("SELECT * FROM notes WHERE isPinned = 1")
    suspend fun getAllPinnedNotes() : List<NotesItem>
}