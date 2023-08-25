package com.example.remindersystem.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.remindersystem.db.repository.ReminderRepository
import com.example.remindersystem.model.Reminder
import com.example.remindersystem.ui.form.NewReminderFormEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.SortedMap

class ReminderListViewModel(
    private val repository: ReminderRepository,
    private val navController: NavController
) : ViewModel(), ReminderListener {

    val reminders: LiveData<List<Reminder>> = repository.getAllReminders()

    private val _groupedReminders = MutableLiveData<Map<String, List<Reminder>>>()
    val groupedReminders: LiveData<Map<String, List<Reminder>>> = _groupedReminders

    private val _events = MutableSharedFlow<ReminderListEvent>()
    val events: SharedFlow<ReminderListEvent> = _events.asSharedFlow()

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

    fun goToNewReminderFragment() {
        val action =
            ReminderListFragmentDirections.actionReminderListFragmentToNewReminderFormFragment()
        navController.navigate(action)
    }

    private fun handleEvent(event: ReminderListEvent) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    override fun onReminderClicked(reminder: Reminder){
        handleEvent(ReminderListEvent.ShowToast("${reminder.name} clickado"))
    }

}
interface ReminderListener{
    fun onReminderClicked(reminder: Reminder)
}

sealed class ReminderListEvent {
    data class ShowToast(val message: String) : ReminderListEvent()
}