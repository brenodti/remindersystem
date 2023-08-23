package com.example.remindersystem.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remindersystem.db.repository.ReminderRepository
import com.example.remindersystem.model.Reminder
import java.time.LocalDate
import java.util.SortedMap

class ReminderListViewModel(private val repository: ReminderRepository) : ViewModel() {

    private val reminders: LiveData<List<Reminder>> = repository.getAllReminders()

    private val _groupedReminders = MutableLiveData<Map<String, List<Reminder>>>()
    val groupedReminders: LiveData<Map<String, List<Reminder>>> = _groupedReminders

    init {
        reminders.observeForever { list ->
            val sortedGroupMapByDate = groupRemindersByDate(list)
            _groupedReminders.postValue(sortedGroupMapByDate)
        }
    }

    private fun groupRemindersByDate(reminders: List<Reminder>): Map<String, List<Reminder>> {
        val groupedMap = mutableMapOf<String, MutableList<Reminder>>()

        for (reminder in reminders) {
            val date = reminder.date.toString()
            if (groupedMap.containsKey(date)) {
                groupedMap[date]?.add(reminder)
            } else {
                groupedMap[date] = mutableListOf(reminder)
            }
        }

        return sortGroupByDate(groupedMap)
    }

    private fun sortGroupByDate(groupedMap: MutableMap<String, MutableList<Reminder>>): SortedMap<String, MutableList<Reminder>> =
        groupedMap.toSortedMap(compareBy { LocalDate.parse(it) })


    suspend fun deleteReminder(reminder: Reminder) {
        repository.deleteReminder(reminder)
    }

}