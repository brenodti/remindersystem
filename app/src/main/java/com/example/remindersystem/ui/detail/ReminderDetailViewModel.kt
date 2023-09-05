package com.example.remindersystem.ui.detail

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.remindersystem.model.Reminder

class ReminderDetailViewModel() : ViewModel() {
    lateinit var reminder: Reminder
}