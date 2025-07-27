package com.example.mynotesapp.notes.domain


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.firebase.firestore.PropertyName
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import com.google.firebase.Timestamp

@Entity(tableName = "notes")
data class NotesItem(
    @PrimaryKey()
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
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now(),

    @PropertyName("isSynced")
    @get:PropertyName("isSynced")
    @set:PropertyName("isSynced")
    var isSynced: Boolean = false,

    val lastSyncedAt: Timestamp? = null,

    @PropertyName("isDeleted")
    @get:PropertyName("isDeleted")
    @set:PropertyName("isDeleted")
    var isDeleted: Boolean = false,

    val deletedAt: Timestamp? = null
) {
    fun getNoteDeletedAt(): String {
        val temp: Timestamp = this.deletedAt ?: return ""
        return timestampFormatter(temp)
    }

    fun getLastUpdatedAt(): String {
        return timestampFormatter(this.updatedAt)
    }

    private fun timestampFormatter(timestamp: Timestamp): String {
        val localDateTime = timestamp.toDate().toInstant()
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDateTime()

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
class TimestampTypeConverter {

    @TypeConverter
    fun fromTimestamp(timestamp: Timestamp?): Long? {
        return timestamp?.toDate()?.time // millis
    }

    @TypeConverter
    fun toTimestamp(millis: Long?): Timestamp? {
        return millis?.let { Timestamp(java.util.Date(it)) }
    }
}
