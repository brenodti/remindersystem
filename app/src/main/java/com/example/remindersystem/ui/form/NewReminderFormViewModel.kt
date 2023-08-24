package com.example.remindersystem.ui.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindersystem.db.repository.ReminderRepository
import com.example.remindersystem.events.Event
import com.example.remindersystem.model.Reminder
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewReminderFormViewModel(private val repository: ReminderRepository) : ViewModel() {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val date = MutableLiveData("")
    val name = MutableLiveData("")

    private val _events = MutableSharedFlow<Event>()
    val events: SharedFlow<Event> = _events.asSharedFlow()

    fun addNewReminder() {
        viewModelScope.launch {
            if (areFieldsValid()) {
                saveReminder()
                handleEvent(Event.GoToReminderListFragment)
            } else {
                handleEvent(Event.ShowToast("Fields must not be empty"))
            }
        }
    }

    private suspend fun saveReminder() {
        val formattedDate = LocalDate.parse(date.value, dateFormatter)
        val reminder = Reminder(name = name.value!!, date = formattedDate)

        repository.insertReminder(reminder)
    }

    private fun areFieldsValid() =
        name.value?.trim()!!.isNotEmpty() && date.value?.trim()!!.isNotEmpty()


    fun handleEvent(event: Event) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }
}