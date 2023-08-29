package com.example.remindersystem.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.remindersystem.db.converter.Converters
import com.example.remindersystem.db.dao.ReminderDao
import com.example.remindersystem.model.Reminder
import kotlin.concurrent.Volatile

@Database(
    entities = [Reminder::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao

    companion object{
        @Volatile
        private lateinit var db : ReminderDatabase

        fun getInstance(context: Context) : ReminderDatabase{

            if(::db.isInitialized) return db

            return Room.databaseBuilder(
                context = context,
                klass = ReminderDatabase::class.java,
                name = "reminders.db"
            ).addMigrations(
                MIGRATION_1_2
            )
                .build()
                .also {
                    db = it
                }
        }
    }

}