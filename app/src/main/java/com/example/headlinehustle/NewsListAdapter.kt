package com.example.headlinehustle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsListAdapter(private val items: ArrayList<String>): RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    val titleView: TextView = itemView.findViewById(R.id.title)
}