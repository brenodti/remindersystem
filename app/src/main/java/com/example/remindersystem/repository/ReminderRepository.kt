package com.example.remindersystem.repository

import androidx.lifecycle.LiveData
import com.example.remindersystem.db.dao.ReminderDao
import com.example.remindersystem.model.Reminder
import com.example.remindersystem.network.holiday.CalendarificApiService
import com.example.remindersystem.network.holiday.Holiday
import com.example.remindersystem.network.image.GoogleSearchApiService
import timber.log.Timber
import java.time.LocalDate

class ReminderRepository(
    private val reminderDao: ReminderDao,
    private val calendarificApi: CalendarificApiService,
    private val googleSearchApi: GoogleSearchApiService
) {
    private suspend fun getImageForReminder(reminderName: String): String?{
        val response = googleSearchApi.getImageFromGoogle(searchFor = reminderName)
        if (response.isSuccessful){
            val items = response.body()?.items
            if(!items.isNullOrEmpty()){
                Timber.i("Link da imagem: ${items[0].link}")
                return items[0].link
            }
        }
        return null
    }
    suspend fun insertHolidaysAsReminders(reminders: List<Reminder>) {
        val holidays = getHolidays()

        val newReminders = getOnlyNewReminders(holidays, reminders)

        newReminders.forEach { reminder ->
            insertReminder(reminder)
        }
    }

    private suspend fun getOnlyNewReminders(
        holidays: List<Holiday>,
        reminders: List<Reminder>?
    ): List<Reminder> {
        if(reminders == null){
            return holidays.map { holiday ->
                val imageUrl = getImageForReminder(holiday.name)
                Reminder(name = holiday.name, date = LocalDate.parse(holiday.date.dateIso), imageUrl = imageUrl)
            }
        }

        return holidays.filter { holiday ->
            !reminders.any { reminder ->
                reminder.name == holiday.name && reminder.date.isEqual(LocalDate.parse(holiday.date.dateIso))
            }
        }.map { holiday ->
            val imageUrl = getImageForReminder(holiday.name)
            Reminder(name = holiday.name, date = LocalDate.parse(holiday.date.dateIso), imageUrl = imageUrl)
        }
    }

    private suspend fun getHolidays(): List<Holiday> {
        val response = calendarificApi.getHolidays(month = 9)
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