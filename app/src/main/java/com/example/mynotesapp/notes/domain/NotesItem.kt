package com.example.mynotesapp.notes.domain


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.firebase.firestore.PropertyName
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Entity(tableName = "notes")
data class NotesItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String = "",
    val content: String = "",

    @PropertyName("isPinned")
    @get:PropertyName("isPinned")
    @set:PropertyName("isPinned")
    var isPinned: Boolean = false,

    @PropertyName("isArchived")
    @get:PropertyName("isArchived")
    @set:PropertyName("isArchived")
    var isArchived: Boolean = false,

    @PropertyName("isChecklist")
    @get:PropertyName("isChecklist")
    @set:PropertyName("isChecklist")
    var isChecklist: Boolean = false,

    val colorHex: String = "#FFFFFF",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @PropertyName("isSynced")
    @get:PropertyName("isSynced")
    @set:PropertyName("isSynced")
    var isSynced: Boolean = false,

    val lastSyncedAt: LocalDateTime? = null,

    @PropertyName("isDeleted")
    @get:PropertyName("isDeleted")
    @set:PropertyName("isDeleted")
    var isDeleted: Boolean = false,
    val deletedAt: LocalDateTime? = null

) {
    fun getDeletedAt() : String{
        val temp : LocalDateTime = this.deletedAt ?: return ""
        return localDateTimeFormater(temp)
    }


    fun getLastUpdatedAt(): String {
        return localDateTimeFormater(this.updatedAt)
    }

    private fun localDateTimeFormater(localDateTime: LocalDateTime) : String{
        val dateFormat = DateTimeFormatter.ofPattern("dd MMM")
        val timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss")

        val date = localDateTime.toLocalDate()
        val time = localDateTime.toLocalTime()

        return if (date.isEqual(LocalDate.now())) {
            time.format(timeFormat)
        } else {
            date.format(dateFormat)
        }
    }



}

@TypeConverters
class LocalDateTimeTypeConvertor {

    private val dateTimeFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime): String {
        return date.format(dateTimeFormat)
    }

    @TypeConverter
    fun toLocalDateTime(date: String): LocalDateTime {
        return LocalDateTime.parse(date, dateTimeFormat)
    }


}