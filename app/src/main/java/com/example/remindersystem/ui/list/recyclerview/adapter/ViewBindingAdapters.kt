package com.example.remindersystem.ui.list.recyclerview.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.remindersystem.R
import com.example.remindersystem.databinding.ItemReminderBinding
import com.example.remindersystem.databinding.ItemReminderGroupBinding
import com.example.remindersystem.model.Reminder
import com.google.android.material.imageview.ShapeableImageView
import timber.log.Timber
import java.time.LocalDate

@BindingAdapter("submitList")
fun submitList(recyclerView: RecyclerView, list: List<Reminder>?) {
    val adapter = recyclerView.adapter as ReminderRowAdapter<*>?
    adapter?.updateData(list ?: emptyList())
}

@BindingAdapter("submitMap")
fun submitMap(recyclerView: RecyclerView, map: Map<String, List<Reminder>>?){
    val adapter = recyclerView.adapter as ReminderGroupListAdapter<*>?
    adapter?.update(map ?: emptyMap())
}

@BindingAdapter("setImage")
fun setImage(imageView: ImageView, imageUrl: String){
    imageView.load(imageUrl){
        fallback(R.drawable.error)
        error(R.drawable.error)
        placeholder(R.drawable.loading)
    }
}

