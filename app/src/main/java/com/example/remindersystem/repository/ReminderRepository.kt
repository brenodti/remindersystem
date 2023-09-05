package com.example.remindersystem.repository

import androidx.lifecycle.LiveData
import com.example.remindersystem.db.dao.ReminderDao
import com.example.remindersystem.model.Reminder
import com.example.remindersystem.network.holiday.HolidayService

class ReminderRepository(
    private val reminderDao: ReminderDao,
    private val holidayService: HolidayService
) {

    suspend fun loadHolidays(reminders: List<Reminder>) {
        val newReminders = holidayService.tryLoadNewHolidaysAsReminders(reminders)

        newReminders.forEach { reminder ->
            insertReminder(reminder)
        }
    }

    suspend fun insertReminder(reminder: Reminder) {
        reminderDao.save(reminder)
    }

    fun getAllReminders(): LiveData<List<Reminder>> {
        return reminderDao.getAll()
    }

    suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.delete(reminder)
    }
}