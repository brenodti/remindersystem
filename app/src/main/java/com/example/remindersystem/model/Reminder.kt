package com.example.remindersystem.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val date: LocalDate,
    val imageUrl: String?
)