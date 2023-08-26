package com.example.remindersystem.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.remindersystem.databinding.FragmentReminderListBinding
import com.example.remindersystem.databinding.ItemReminderGroupBinding
import com.example.remindersystem.ui.list.recyclerview.adapter.ReminderGroupListAdapter
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
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