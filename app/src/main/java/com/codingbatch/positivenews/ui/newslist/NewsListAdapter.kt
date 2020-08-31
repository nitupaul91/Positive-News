package com.codingbatch.positivenews.ui.newslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingbatch.positivenews.databinding.ItemRegularListNewsBinding
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.util.setOnSafeClickListener
import kotlinx.android.synthetic.main.item_regular_list_news.view.*

class NewsListAdapter(
    private val newsClickListener: NewsClickListener,
    private val newsScrollListener: NewsScrollListener
) :
    RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    private var newsList: MutableList<News>? = mutableListOf()

    fun setNews(newsList: MutableList<News>?) {
        this.newsList = newsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRegularListNewsBinding.inflate(inflater, parent, false)

        return ViewHolder(
            binding.root
        )
    }

    override fun getItemCount() = newsList?.size ?: 0

    override fun onBindViewHolder(holder: NewsListAdapter.ViewHolder, position: Int) {
        holder.bindView(newsList!![position], newsClickListener)
        if (newsList!!.size > 18 && position == newsList!!.size - 1) {
            newsScrollListener.fetchMoreNews(newsList!![newsList!!.size - 1].fullName!!)
        }
    }

    override fun getItemId(position: Int): Long {
        return newsList!![position].hashCode().toLong()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(news: News, newsClickListener: NewsClickListener) {
            itemView.rlNewsContainer.setOnSafeClickListener {
                newsClickListener.onNewsClicked(news)
            }
            itemView.ivMoreOptions.setOnSafeClickListener {
                newsClickListener.onMoreOptionsClicked(news)
            }
            val binding: ItemRegularListNewsBinding = DataBindingUtil.getBinding(itemView)!!
            binding.news = news
        }
    }

    interface NewsClickListener {
        fun onNewsClicked(news: News)
        fun onMoreOptionsClicked(news: News)
    }

    interface NewsScrollListener {
        fun fetchMoreNews(after: String)
    }
}