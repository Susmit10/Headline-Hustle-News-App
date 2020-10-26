package com.example.headlinehustle

import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONObject.NULL

class NewsListAdapter(private val listener: NewsItemClicked) :
    RecyclerView.Adapter<NewsViewHolder>() {

    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.authorView.text = currentItem.author
        holder.description.text = currentItem.desc
        if (holder.authorView.text == "null" || currentItem.author.length > 18 )
        {
            holder.authorView.text = currentItem.source.getString("name")
        }

        val fullTime: String = currentItem.time
        val date: String = fullTime.substring(0,10)
        val time: String = fullTime.substring(11,19)
        val dot: Spanned? = Html.fromHtml("&#8226;")
        holder.time.text = date.plus(" ").plus(dot).plus(" ").plus(time)

        Glide.with(holder.itemView.context).load(currentItem.urlToImage).into(holder.imageView)
        holder.share.setOnClickListener {
            listener.onShare(items[position])
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updateNews: ArrayList<News>) {
        items.clear()
        items.addAll(updateNews)

        notifyDataSetChanged()
    }

}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.findViewById(R.id.image)
    val share: ImageView = itemView.findViewById(R.id.shareNews)
    val titleView: TextView = itemView.findViewById(R.id.title)
    val authorView: TextView = itemView.findViewById(R.id.author)
    val description: TextView = itemView.findViewById(R.id.description)
    var time: TextView = itemView.findViewById(R.id.time)
}

interface NewsItemClicked {
    fun onItemClicked(item: News)
    fun refreshData()
    fun onShare(news: News)
}