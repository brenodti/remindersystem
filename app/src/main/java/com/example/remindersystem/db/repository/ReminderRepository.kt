package com.example.remindersystem.db.repository

import androidx.lifecycle.LiveData
import com.example.remindersystem.db.dao.ReminderDao
import com.example.remindersystem.model.Reminder
import com.example.remindersystem.network.holiday.CalendarificApiService
import com.example.remindersystem.network.holiday.Holiday
import java.time.LocalDate

class ReminderRepository(
    private val reminderDao: ReminderDao,
    private val calendarificApi: CalendarificApiService
) {
    suspend fun insertHolidaysAsReminders(reminders: List<Reminder>) {
        val holidays = getHolidays()

        val newReminders = getOnlyNewReminders(holidays, reminders)

        newReminders.forEach { reminder ->
            insertReminder(reminder)
        }
    }

    private fun getOnlyNewReminders(
        holidays: List<Holiday>,
        reminders: List<Reminder>?
    ): List<Reminder> {
        if(reminders == null){
            return holidays.map { holiday ->
                Reminder(name = holiday.name, date = LocalDate.parse(holiday.date.dateIso))
            }
        }

        return holidays.filter { holiday ->
            !reminders.any { reminder ->
                reminder.name == holiday.name && reminder.date.isEqual(LocalDate.parse(holiday.date.dateIso))
            }
        }.map { holiday ->
            Reminder(name = holiday.name, date = LocalDate.parse(holiday.date.dateIso))
        }
    }

    private suspend fun getHolidays(): List<Holiday> {
        val response = calendarificApi.getHolidays()
        if (response.body()?.response?.holidays == null)
            return emptyList()
        return response.body()!!.response.holidays
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