package com.example.remindersystem.db.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Converters {

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        return value?.let {
            LocalDate.parse(it)
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date?.toString()
    }
}