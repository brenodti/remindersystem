package com.example.remindersystem.network.holiday

import com.example.remindersystem.model.Reminder
import com.example.remindersystem.network.image.ImageSearchService
import java.time.LocalDate

class HolidayService(
    private val calendarificApi: CalendarificApiService,
    private val imageSearchService: ImageSearchService
) {

    suspend fun tryLoadNewHolidaysAsReminders(reminders: List<Reminder>): List<Reminder> {
        val holidays = fetchHolidays()

        return getOnlyNewHolidaysAsReminders(holidays, reminders)
    }

    private suspend fun getOnlyNewHolidaysAsReminders(
        holidays: List<Holiday>,
        listOfReminders: List<Reminder>?
    ): List<Reminder> {

        if (listOfReminders == null) {
            return holidays.map { holiday -> createNewReminderFromHoliday(holiday) }
        }

        return holidays.filter { holiday ->
            holidayWasNotAlreadyCreated(holiday, listOfReminders)
        }.map { holiday ->
            createNewReminderFromHoliday(holiday)
        }
    }

    private fun holidayWasNotAlreadyCreated(
        holiday: Holiday,
        listOfReminders: List<Reminder>
    ): Boolean {
        return listOfReminders.none { reminder ->
            reminderAndHolidayAreTheSame(reminder, holiday)
        }
    }

    private fun reminderAndHolidayAreTheSame(
        reminder: Reminder,
        holiday: Holiday
    ): Boolean {
        val isTheSameName = (reminder.name == holiday.name)
        val isTheSameDate = reminder.date.isEqual(LocalDate.parse(holiday.date.dateIso))

        return (isTheSameName) && (isTheSameDate)
    }

    private suspend fun createNewReminderFromHoliday(holiday: Holiday): Reminder {
        val imageUrl = imageSearchService.searchForImageUrl(holiday.name)
        return Reminder(
            name = holiday.name,
            date = LocalDate.parse(holiday.date.dateIso),
            imageUrl = imageUrl
        )
    }

    private suspend fun fetchHolidays(): List<Holiday> {
        val response = calendarificApi.getHolidays(month = 9)
        if (response.body()?.response?.holidays == null)
            return emptyList()
        return response.body()!!.response.holidays
    }
}