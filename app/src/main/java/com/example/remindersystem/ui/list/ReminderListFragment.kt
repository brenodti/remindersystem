package com.example.remindersystem.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.remindersystem.databinding.FragmentReminderListBinding
import com.example.remindersystem.databinding.ItemReminderGroupBinding
import com.example.remindersystem.ui.list.recyclerview.adapter.ReminderGroupListAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ReminderListFragment : Fragment() {

    private val viewModel: ReminderListViewModel by viewModel {
        parametersOf(findNavController())
    }

    private lateinit var adapter: ReminderGroupListAdapter<ItemReminderGroupBinding>

    private val binding by lazy {
        FragmentReminderListBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
        observeEvents()

//        adapter.whenReminderIsLongClickedListener = {
//            lifecycleScope.launch(IO) {
//                viewModel.deleteReminder(it)
//            }
//        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    is ReminderListEvent.ShowToast -> showToast(event)
                    else -> {}
                }
            }
        }
    }

    private fun showToast(event: ReminderListEvent.ShowToast) {
        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
    }

    private fun bind() {
        adapter = ReminderGroupListAdapter(mapOf(), reminderListener = viewModel)
        binding.apply {
            recyclerView.adapter = adapter
            viewModel = this@ReminderListFragment.viewModel
            lifecycleOwner = this@ReminderListFragment
        }
    }

}