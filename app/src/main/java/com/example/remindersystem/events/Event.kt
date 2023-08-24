package com.example.remindersystem.events

sealed class Event{
    data object ShowDatePicker : Event()
    data object GoToReminderListFragment : Event()
    data class ShowToast(val message: String) : Event()
}
