package com.example.remindersystem.ui.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.remindersystem.db.repository.ReminderRepository
import com.example.remindersystem.model.Reminder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewReminderFormViewModel(
    private val repository: ReminderRepository,
    private val navController: NavController
) : ViewModel() {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val date = MutableStateFlow("")
    val name = MutableStateFlow("")

    private val _events = MutableSharedFlow<NewReminderFormEvent>()
    val events: SharedFlow<NewReminderFormEvent> = _events.asSharedFlow()

    fun addNewReminder() {
        if (areFieldsValid()) {
            saveReminder()
            gotToReminderListFragment()
        } else {
            handleEvent(NewReminderFormEvent.ShowToast("Fields must not be empty"))
        }
    }

    private fun areFieldsValid() =
        name.value.trim().isNotEmpty() && date.value.trim().isNotEmpty()

    private fun saveReminder() {
        val formattedDate = LocalDate.parse(date.value, dateFormatter)
        val reminder = Reminder(name = name.value, date = formattedDate)

        viewModelScope.launch {
            repository.insertReminder(reminder)
        }
    }

    private fun gotToReminderListFragment() {
        val action =
            NewReminderFormFragmentDirections.actionNewReminderFormFragmentToReminderListFragment()
        navController.navigate(action)
    }

    fun handleEvent(event: NewReminderFormEvent) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }
}

sealed class NewReminderFormEvent{
    data object ShowDatePicker : NewReminderFormEvent()
    data class ShowToast(val message: String) : NewReminderFormEvent()
}