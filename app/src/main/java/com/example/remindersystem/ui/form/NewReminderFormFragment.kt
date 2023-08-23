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
import com.example.remindersystem.model.Reminder
import com.example.remindersystem.viewmodel.form.NewReminderFormViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.Instant
import java.time.LocalDate
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
        binding.dateEditText.setOnClickListener {
            showDatePickerDialog()
        }

        configureSaveButton()

        return binding.root
    }

    private fun showDatePickerDialog() {
        val datePicker = configureDatePicker()

        datePicker.addOnPositiveButtonClickListener { selectedDateInMillis ->
            val selectedLocalDate = getFormattedSelectedDate(selectedDateInMillis)
            binding.dateEditText.setText(selectedLocalDate.toString())
        }

        datePicker.show(requireActivity().supportFragmentManager, "datePickerTag")
    }

    private fun configureDatePicker() = MaterialDatePicker.Builder.datePicker()
        .setTitleText("Select date")
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .setCalendarConstraints(getContraintsToOnlyFutureDates())
        .build()

    private fun getFormattedSelectedDate(selectedDateInMillis: Long): String? {
        return Instant.ofEpochMilli(selectedDateInMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .plusDays(1)
            .format(dateFormatter)
    }

    private fun getContraintsToOnlyFutureDates(): CalendarConstraints {
        return CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())
            .build()
    }

    private fun configureSaveButton() {
        binding.saveButton.setOnClickListener {
            if(areFieldsValid()) {
                lifecycleScope.launch(IO) {
                    createNewReminder()
                }
                goToReminderListFragment()
            } else {
                Toast.makeText(this.context, "Fields must not be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun areFieldsValid(): Boolean {
        val date = binding.dateEditText.text.toString().trim()
        val name = binding.nameEditText.text.toString().trim()

        return name.isNotEmpty() && date.isNotEmpty()
    }

    private fun goToReminderListFragment() {
        val action =
            NewReminderFormFragmentDirections.actionNewReminderFormFragmentToReminderListFragment()
        findNavController().navigate(action)
    }

    private suspend fun createNewReminder() {
        val name = binding.nameEditText.text.toString()
        val date = binding.dateEditText.text.toString()
        val unformattedDate = LocalDate.parse(date, dateFormatter)

        viewModel.addNewReminder(
            Reminder(
                name = name,
                date = LocalDate.parse(unformattedDate.toString())
            )
        )
    }

}