package com.example.remindersystem.ui.list.recyclerview.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.remindersystem.databinding.ItemReminderBinding
import com.example.remindersystem.databinding.ItemReminderGroupBinding
import com.example.remindersystem.model.Reminder
import timber.log.Timber
import java.time.LocalDate

@BindingAdapter("submitList")
fun submitList(recyclerView: RecyclerView, list: List<Reminder>?) {
    Timber.i("submitList: $list")
    val adapter = recyclerView.adapter as ReminderRowAdapter<*>?
    adapter?.updateData(list ?: emptyList())
}

@BindingAdapter("submitMap")
fun submitMap(recyclerView: RecyclerView, map: Map<String, List<Reminder>>?){
    val adapter = recyclerView.adapter as ReminderGroupListAdapter<*>?
    adapter?.update(map ?: emptyMap())
}

