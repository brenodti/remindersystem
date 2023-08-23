package com.example.remindersystem.ui.list.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.remindersystem.databinding.ItemReminderGroupBinding
import com.example.remindersystem.model.Reminder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ReminderGroupListAdapter(
    private val context: Context?,
    private var remindersMap: Map<String, List<Reminder>> = emptyMap(),
    var whenReminderIsLongClickedListener: (reminder: Reminder) -> Unit = {}
) : RecyclerView.Adapter<ReminderGroupListAdapter.ViewHolder>() {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    private val dataSet: MutableList<String> = remindersMap.keys.toMutableList()

    inner class ViewHolder(binding: ItemReminderGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val dateTextView = binding.dateTextView
        private val recyclerView = binding.remindersRecyclerView
        private lateinit var adapter: ReminderRowAdapter

        fun bind(date: String) {
            val formattedDate = LocalDate.parse(date).format(dateFormatter)
            dateTextView.text = formattedDate
            adapter = ReminderRowAdapter(context, remindersMap[date])
            adapter.whenReminderIsClickedListener = {
                whenReminderIsLongClickedListener(it)
            }

            recyclerView.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReminderGroupBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = dataSet[position]
        holder.bind(date)
    }

    fun update(remindersMap: Map<String, List<Reminder>>) {
        this.remindersMap = remindersMap
        this.dataSet.clear()
        this.dataSet.addAll(remindersMap.keys)
        notifyDataSetChanged()
    }
}