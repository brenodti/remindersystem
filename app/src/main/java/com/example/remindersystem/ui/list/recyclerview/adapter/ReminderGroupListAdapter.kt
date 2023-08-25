package com.example.remindersystem.ui.list.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.remindersystem.R
import com.example.remindersystem.databinding.ItemReminderBinding
import com.example.remindersystem.databinding.ItemReminderGroupBinding
import com.example.remindersystem.model.Reminder
import com.example.remindersystem.ui.list.ReminderListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ReminderGroupListAdapter<BINDING : ItemReminderGroupBinding>(
    private var remindersMap: Map<String, List<Reminder>>,
    private val reminderListener: ReminderListener
) : RecyclerView.Adapter<ReminderGroupListAdapter<BINDING>.ViewHolder>() {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    private val dataSet: MutableMap<String, List<Reminder>> = remindersMap.toMutableMap()

    inner class ViewHolder(val binder: ItemReminderGroupBinding) :
        RecyclerView.ViewHolder(binder.root) {

        fun bind(
            binding: ItemReminderGroupBinding,
            dateInfo: String,
            remindersList: List<Reminder>
        ) {
            val formattedDate = LocalDate.parse(dateInfo).format(dateFormatter)
            binding.apply {
                date = formattedDate
                reminders = remindersList
                adapter = ReminderRowAdapter<ItemReminderBinding>(remindersList, reminderListener)
                remindersRecyclerView.adapter = adapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemReminderGroupBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_reminder_group,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dates = dataSet.keys.toMutableList()
        val selectedDate = dates[position]
        val dateWithReminders = dataSet[selectedDate] ?: emptyList()
        holder.bind(holder.binder, selectedDate, dateWithReminders)
    }

    fun update(map: Map<String, List<Reminder>>) {
        this.dataSet.clear()
        this.dataSet.putAll(map)
        this.remindersMap = map
        notifyDataSetChanged()
    }
}