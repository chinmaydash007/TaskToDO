package com.example.tasktodo.Adapters

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Data
import com.bumptech.glide.Glide
import com.example.tasktodo.R


class BlogsAdapter(val list: List<com.example.tasktodo.Model.Data>) : RecyclerView.Adapter<BlogsAdapter.BlogsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogsViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.blog_layout, parent, false)
        return BlogsViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BlogsViewHolder, position: Int) {
        val blog = list[position]
        Glide.with(holder.itemView).load(blog.img_url).into(holder.previewIV)
        holder.titleTV.text = blog.title
        holder.descTV.text = blog.description
        holder.itemView.setOnClickListener {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(blog.blog_url))
                holder.itemView.context.startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(holder.itemView.context, "No application can handle this request."
                        + " Please install a web browser", Toast.LENGTH_LONG).show()
            }
        }
    }

    inner class BlogsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById(R.id.tv_blog_title)
        val descTV: TextView = itemView.findViewById(R.id.tv_blog_desc)
        val previewIV: ImageView = itemView.findViewById(R.id.iv_blog_preview)
    }
}