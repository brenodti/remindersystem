package com.example.remindersystem.ui.detail

import com.example.remindersystem.compose.detail.ReminderDetailScreen
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.remindersystem.model.Reminder
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ReminderDetailFragment : Fragment() {

    private val args: ReminderDetailFragmentArgs by navArgs()

    private val viewModel: ReminderDetailViewModel by viewModel {
        parametersOf(findNavController())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.reminder = args.reminder

        return ComposeView(requireContext()).apply {
            setContent {
                ReminderDetailScreen(reminder = viewModel.reminder)
            }
        }
    }

}