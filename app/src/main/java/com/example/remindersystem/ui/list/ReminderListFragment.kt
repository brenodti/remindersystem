package com.example.remindersystem.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.remindersystem.databinding.FragmentReminderListBinding
import com.example.remindersystem.ui.list.recyclerview.adapter.ReminderGroupListAdapter
import com.example.remindersystem.viewmodel.list.ReminderListViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReminderListFragment : Fragment() {

    private val viewModel: ReminderListViewModel by viewModel()

    private lateinit var adapter: ReminderGroupListAdapter

    private val binding by lazy {
        FragmentReminderListBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        configureRecyclerView()

        binding.newReminderButton.setOnClickListener {
            goToNewReminderFragment()
        }

        viewModel.groupedReminders.observe(viewLifecycleOwner) {
            adapter.update(it)
        }

        adapter.whenReminderIsLongClickedListener = {
            lifecycleScope.launch(IO) {
                viewModel.deleteReminder(it)
            }
        }

        return binding.root
    }

    private fun configureRecyclerView() {
        adapter = ReminderGroupListAdapter(context = this.context)
        binding.recyclerView.adapter = adapter
    }

    private fun goToNewReminderFragment() {
        val action =
            ReminderListFragmentDirections.actionReminderListFragmentToNewReminderFormFragment()
        findNavController().navigate(action)
    }

}