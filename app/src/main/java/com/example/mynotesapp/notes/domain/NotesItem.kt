package com.example.mynotesapp.notes.domain


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Entity(tableName = "notes")
data class NotesItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String = "",
    val content: String = "",
    val isPinned: Boolean = false,
    val isArchived: Boolean = false,
    val isChecklist: Boolean = false,
    val colorHex: String = "#FFFFFF",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),

)

@TypeConverters
class LocalDateTimeTypeConvertor(){

    val dateTimeFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME


    @TypeConverter
    fun fromLocalDateTime(date : LocalDateTime): String{
        return date.format(dateTimeFormat)
    }

    @TypeConverter
    fun toLocalDateTime(date : String) : LocalDateTime{
        return LocalDateTime.parse(date, dateTimeFormat)
    }


}