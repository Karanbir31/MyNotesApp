package com.example.mynotesapp.notes.domain


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.text.DateFormat
import java.time.LocalDate
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
    val updatedAt: LocalDateTime = LocalDateTime.now()


){
    fun getLocalDateTimeAsString() : String{
        val dateFormat = DateTimeFormatter.ofPattern("dd MMM")
        val timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss")

        val date = this.updatedAt.toLocalDate()
        val time = this.updatedAt.toLocalTime()

        return if (date.isEqual(LocalDate.now())){
            time.format(timeFormat)
        }else{
            date.format(dateFormat)
        }

    }
}

@TypeConverters
class LocalDateTimeTypeConvertor{

    private val dateTimeFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDateTime(date : LocalDateTime): String{
        return date.format(dateTimeFormat)
    }

    @TypeConverter
    fun toLocalDateTime(date : String) : LocalDateTime{
        return LocalDateTime.parse(date, dateTimeFormat)
    }


}