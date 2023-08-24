package com.example.remindersystem.ui.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.remindersystem.databinding.FragmentNewReminderFormBinding
import com.example.remindersystem.events.Event
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class NewReminderFormFragment : Fragment() {

    private val viewModel: NewReminderFormViewModel by viewModel()

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    private val binding by lazy {
        FragmentNewReminderFormBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindEventsAndViewModel()

        return binding.root
    }

    private fun bindEventsAndViewModel() {
        binding.viewModel = viewModel
        binding.showDatePickerEvent = Event.ShowDatePicker
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvents()
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    is Event.ShowDatePicker -> {
                        showDatePickerDialog()
                    }

                    is Event.GoToReminderListFragment -> {
                        gotToReminderListFragment()
                    }

                    is Event.ShowToast -> {
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun gotToReminderListFragment() {
        val action =
            NewReminderFormFragmentDirections.actionNewReminderFormFragmentToReminderListFragment()
        findNavController().navigate(action)
    }

    private fun showDatePickerDialog() {
        val datePicker = createDatePicker()

        datePicker.addOnPositiveButtonClickListener { selectedDateInMillis ->
            val selectedLocalDate = getFormattedSelectedDate(selectedDateInMillis)
            binding.dateEditText.setText(selectedLocalDate.toString())
        }

        datePicker.show(requireActivity().supportFragmentManager, "datePickerTag")
    }

    private fun createDatePicker() = MaterialDatePicker.Builder.datePicker()
        .setTitleText("Select date")
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .setCalendarConstraints(getContraintsToOnlyFutureDates())
        .build()

    private fun getContraintsToOnlyFutureDates(): CalendarConstraints {
        return CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())
            .build()
    }

    private fun getFormattedSelectedDate(selectedDateInMillis: Long): String? {
        return Instant.ofEpochMilli(selectedDateInMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .plusDays(1)
            .format(dateFormatter)
    }
}