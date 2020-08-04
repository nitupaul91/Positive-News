package com.codingbatch.positivenews.ui.newslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingbatch.positivenews.databinding.ItemListNewsBinding
import com.codingbatch.positivenews.model.News
import javax.inject.Inject

class NewsListAdapter @Inject constructor() : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    var newsList: MutableList<News>? = mutableListOf()
        set(value) {
            field?.clear()
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListNewsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount() = newsList?.size ?: 0

    override fun onBindViewHolder(holder: NewsListAdapter.ViewHolder, position: Int) {
        holder.bindView(newsList!!.get(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(news: News) {
            val binding: ItemListNewsBinding = DataBindingUtil.getBinding(itemView)!!
            binding.news = news
        }
    }
}