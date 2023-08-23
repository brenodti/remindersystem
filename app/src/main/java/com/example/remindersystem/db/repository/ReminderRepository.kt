package com.example.remindersystem.db.repository

import androidx.lifecycle.LiveData
import com.example.remindersystem.db.dao.ReminderDao
import com.example.remindersystem.model.Reminder

class ReminderRepository(private val reminderDao: ReminderDao) {

    suspend fun insertReminder(reminder: Reminder){
        reminderDao.save(reminder)
    }

    fun getAllReminders(): LiveData<List<Reminder>> {
        return reminderDao.getAll()
    }

    suspend fun deleteReminder(reminder: Reminder){
        reminderDao.delete(reminder)
    }
}