package com.example.swipequizkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestionAdapter(private val reminders: List<Question>) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return reminders.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reminders[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvReminder: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(reminder: Question) {
            tvReminder.text = reminder.reminder
        }

    }
}