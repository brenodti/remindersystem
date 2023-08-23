package com.example.remindersystem.ui.list.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.remindersystem.R
import com.example.remindersystem.databinding.ItemReminderBinding
import com.example.remindersystem.model.Reminder

class ReminderRowAdapter(
    private val context: Context?,
    private val reminders: List<Reminder>?,
    var whenReminderIsClickedListener: (reminder: Reminder) -> Unit = {}
) : RecyclerView.Adapter<ReminderRowAdapter.ViewHolder>() {

    private val dataSet: MutableList<Reminder>? = reminders?.toMutableList()

    inner class ViewHolder(binding: ItemReminderBinding) : RecyclerView.ViewHolder(binding.root),
        PopupMenu.OnMenuItemClickListener {
        private val name = binding.nameTextView

        private lateinit var reminder: Reminder

        init {
            itemView.setOnClickListener {
                if (::reminder.isInitialized) {
                }
            }

            itemView.setOnLongClickListener { list ->
                if(::reminder.isInitialized){
                    val popupMenu = PopupMenu(context, list)
                    val inflater: MenuInflater = popupMenu.menuInflater
                    inflater.inflate(R.menu.reminder_menu, popupMenu.menu)
                    popupMenu.setOnMenuItemClickListener {
                        onMenuItemClick(it)
                    }
                    popupMenu.show()
                }
                return@setOnLongClickListener false
            }
        }

        fun bind(reminder: Reminder) {
            name.text = reminder.name
            this.reminder = reminder
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            return when (item?.itemId){
                R.id.menu_reminder_delete -> {
                    whenReminderIsClickedListener(reminder)
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReminderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reminder = dataSet?.get(position)
        if (reminder != null) {
            holder.bind(reminder)
        }
    }

}