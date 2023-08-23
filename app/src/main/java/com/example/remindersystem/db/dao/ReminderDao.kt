package com.example.remindersystem.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.remindersystem.model.Reminder

@Dao
interface ReminderDao {

    @Query("SELECT * FROM Reminder")
    fun getAll(): LiveData<List<Reminder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg reminder: Reminder)

    @Delete
    suspend fun delete(vararg reminder: Reminder)
}