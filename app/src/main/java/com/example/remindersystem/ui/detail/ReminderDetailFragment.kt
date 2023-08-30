package com.example.remindersystem.ui.detail

import ReminderDetailScreen
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.remindersystem.R
import com.example.remindersystem.model.Reminder
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ReminderDetailFragment : Fragment() {

    private val viewModel: ReminderDetailViewModel by viewModel {
        parametersOf(findNavController())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val args: ReminderDetailFragmentArgs by navArgs()
        val reminder = args.reminder
        Timber.i("Lembrete no Detail: $reminder")
        return ComposeView(requireContext()).apply {
            setContent {
                ReminderDetailScreen(navController = viewModel.navController, reminder = reminder)
            }
        }
    }

}