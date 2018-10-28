package com.example.henrikandersson.bbcrssapp.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.henrikandersson.bbcrssapp.R

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsItemViewHolder>() {
    private var mNews: ArrayList<DisplayableNewsItem> = ArrayList()

    fun updateData(data: List<DisplayableNewsItem>) {
        mNews.clear()
        mNews.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): NewsItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false)
        return NewsItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mNews.size
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val newsItem = mNews[position]
        holder.title.text = newsItem.name
        holder.date.text = newsItem.date
    }

    class NewsItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val date: TextView = view.findViewById(R.id.date)
    }
}