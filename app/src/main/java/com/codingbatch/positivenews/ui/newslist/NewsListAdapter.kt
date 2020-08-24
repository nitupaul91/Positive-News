package com.codingbatch.positivenews.ui.newslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.databinding.ItemRegularListNewsBinding
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.util.setOnSafeClickListener
import kotlinx.android.synthetic.main.item_regular_list_news.view.*

class NewsListAdapter(
    private val newsClickListener: NewsClickListener,
    private val newsScrollListener: NewsScrollListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var newsList: MutableList<News>? = mutableListOf()
        set(value) {
            field?.clear()
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRegularListNewsBinding.inflate(inflater, parent, false)

        return when (viewType) {
            ListItem.TYPE_HEADER.viewType -> HeaderViewHolder(
                inflater.inflate(R.layout.item_header_news_list, parent, false)
            )
            ListItem.TYPE_REGULAR.viewType -> RegularViewHolder(
                binding.root
            )
            else -> throw java.lang.IllegalArgumentException("invalid viewType")
        }
    }

    override fun getItemCount() = newsList?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RegularViewHolder)
            when (getItemViewType(position)) {
                ListItem.TYPE_REGULAR.viewType -> holder.bindView(
                    newsList!![position],
                    newsClickListener
                )
            }
        if (position == newsList!!.size - 1) {
            newsScrollListener.fetchMoreNews(newsList!![newsList!!.size - 1].fullName!!)
        }
    }

    override fun getItemId(position: Int): Long {
        return newsList!![position].hashCode().toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            ListItem.TYPE_HEADER.viewType
        else
            ListItem.TYPE_REGULAR.viewType
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class RegularViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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

    enum class ListItem(val viewType: Int) {
        TYPE_HEADER(0),
        TYPE_REGULAR(1)
    }
}