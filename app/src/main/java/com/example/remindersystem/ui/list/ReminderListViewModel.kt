package com.example.remindersystem.ui.list

import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.remindersystem.R
import com.example.remindersystem.repository.ReminderRepository
import com.example.remindersystem.model.Reminder
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.SortedMap
import coil.load
import coil.request.Disposable
import com.bumptech.glide.integration.compose.placeholder

class ReminderListViewModel(
    private val repository: ReminderRepository,
    private val navController: NavController
) : ViewModel(), ReminderListener {
    private val remindersFromDatabase: LiveData<List<Reminder>> = repository.getAllReminders()

    private val _groupedReminders = MutableLiveData<Map<String, List<Reminder>>>()
    val groupedReminders: LiveData<Map<String, List<Reminder>>> = _groupedReminders

    private var hasLoadedHolidays = false

    init {
        remindersFromDatabase.observeForever { reminders ->
            if(!hasLoadedHolidays) {
                viewModelScope.launch {
                    repository.loadHolidays(reminders)
                }
                hasLoadedHolidays = true
            }

            val sortedGroupMapByDate = groupRemindersByDate(reminders)
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

    fun goToNewReminderFragment() {
        val action =
            ReminderListFragmentDirections.actionReminderListFragmentToNewReminderFormFragment()
        navController.navigate(action)
    }

    override fun onReminderClicked(reminder: Reminder) {
        goToDetailReminderFragment(reminder)
    }

    private fun goToDetailReminderFragment(reminder: Reminder) {
        val action = ReminderListFragmentDirections.actionReminderListFragmentToReminderDetailFragment(reminder)
        navController.navigate(action)
    }

    override fun onReminderLongClicked(reminder: Reminder, view: View): Boolean {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menuInflater.inflate(R.menu.reminder_menu, popupMenu.menu)

        configureMenuItemListener(popupMenu, reminder)

        popupMenu.show()

        return true
    }

    private fun configureMenuItemListener(
        popupMenu: PopupMenu,
        reminder: Reminder
    ) {
        popupMenu.setOnMenuItemClickListener { item ->
            when (item?.itemId) {
                R.id.menu_reminder_delete -> {
                    viewModelScope.launch {
                        repository.deleteReminder(reminder)
                    }
                    true
                }
                else -> false
            }
        }
    }

}

interface ReminderListener {
    fun onReminderLongClicked(reminder: Reminder, view: View): Boolean
    fun onReminderClicked(reminder: Reminder)
}