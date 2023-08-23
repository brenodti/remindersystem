package com.example.remindersystem.ui.form

import androidx.lifecycle.ViewModel
import com.example.remindersystem.db.repository.ReminderRepository
import com.example.remindersystem.model.Reminder

class NewReminderFormViewModel(private val repository: ReminderRepository) : ViewModel() {
    suspend fun addNewReminder (reminder: Reminder){
        repository.insertReminder(reminder)
    }
}