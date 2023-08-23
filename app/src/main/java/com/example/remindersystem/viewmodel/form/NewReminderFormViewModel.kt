package com.example.remindersystem.viewmodel.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remindersystem.db.repository.ReminderRepository
import com.example.remindersystem.model.Reminder

class NewReminderFormViewModel(private val repository: ReminderRepository) : ViewModel() {
    suspend fun addNewReminder (reminder: Reminder){
        repository.insertReminder(reminder)
    }
}