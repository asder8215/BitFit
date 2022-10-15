package com.example.bitfitapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DiaryAdapter (private val context: Context, private val diaries: List<DisplayDiary>):
    RecyclerView.Adapter<DiaryAdapter.ViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.diary_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val diary = diaries[position]
        holder.bind(diary)
    }

    override fun getItemCount() = diaries.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val date = itemView.findViewById<TextView>(R.id.date)
        private val mood = itemView.findViewById<TextView>(R.id.mood)
        private val entry = itemView.findViewById<TextView>(R.id.entry)

        fun bind(diary: DisplayDiary) {
            title.text = diary.title
            date.text = diary.date
            entry.text = diary.entry
            mood.text = diary.mood
        }

    }

}