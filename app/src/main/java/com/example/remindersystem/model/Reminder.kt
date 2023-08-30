package com.example.remindersystem.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Entity
@Parcelize
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val date: LocalDate,
    val imageUrl: String?
): Parcelable