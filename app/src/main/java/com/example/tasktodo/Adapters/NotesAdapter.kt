package com.example.tasktodo.Adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tasktodo.R
import com.example.tasktodo.clicklisteners.ItemClickListener
import com.example.tasktodo.db.Notes


class NotesAdapter(val list: List<Notes>, val itemClickListener: ItemClickListener) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.notes_adapter_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notes: Notes = list[position]
        val title = notes.title
        val description = notes.description

        holder.titleTV.text = title
        holder.descTV.text = description
        holder.markStatusCB.isChecked = notes.isTaskCompleted
        Glide.with(holder.itemView).load(notes.imagePath).into(holder.previewIV)

        holder.itemView.setOnClickListener { itemClickListener.onClick(notes) }
        holder.markStatusCB.setOnCheckedChangeListener { _, isChecked ->
            notes.isTaskCompleted = isChecked
            itemClickListener.onUpdate(notes)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById(R.id.tv_title)
        val descTV: TextView = itemView.findViewById(R.id.tv_description)
        val markStatusCB: CheckBox = itemView.findViewById(R.id.cb_mark_status)
        val previewIV: ImageView = itemView.findViewById(R.id.iv_image_preview)
    }
}