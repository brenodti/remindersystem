package com.example.remindersystem.ui.list.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.remindersystem.R
import com.example.remindersystem.databinding.ItemReminderBinding
import com.example.remindersystem.model.Reminder
import com.example.remindersystem.ui.list.ReminderListener
import timber.log.Timber

class ReminderRowAdapter<BINDING : ItemReminderBinding>(
    private var reminders: List<Reminder>,
    private val reminderListener: ReminderListener,
) : RecyclerView.Adapter<ReminderRowAdapter<BINDING>.ViewHolder>() {

    private val dataSet: MutableList<Reminder> = reminders.toMutableList()

    inner class ViewHolder(val binder: ItemReminderBinding) : RecyclerView.ViewHolder(binder.root) {

        fun bind(binding: ItemReminderBinding, item: Reminder) {
            binding.apply {
                reminder = item
                listener = reminderListener
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemReminderBinding>(
            LayoutInflater.from(parent.context), R.layout.item_reminder, parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reminder = dataSet[position]
        holder.bind(holder.binder, reminder)
    }

    fun updateData(list: List<Reminder>) {
        dataSet.clear()
        dataSet.addAll(list)
        this.reminders = list
        notifyDataSetChanged()
    }

}