package com.codingbatch.positivenews.ui.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingbatch.positivenews.databinding.ItemListBlockedSourcesBinding
import com.codingbatch.positivenews.model.NewsSource
import com.codingbatch.positivenews.util.ItemClickListener
import com.codingbatch.positivenews.util.setOnSafeClickListener
import kotlinx.android.synthetic.main.item_list_blocked_sources.view.*

class BlockedSourcesAdapter(
    private val newsClickListener: ItemClickListener<NewsSource>
) :
    RecyclerView.Adapter<BlockedSourcesAdapter.ViewHolder>() {

    private var blockedSources: MutableList<NewsSource>? = mutableListOf()

    fun setBlockedSources(blockedSources: MutableList<NewsSource>?) {
        this.blockedSources = blockedSources
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBlockedSourcesBinding.inflate(inflater, parent, false)

        return ViewHolder(
            binding.root
        )
    }

    override fun getItemCount() = blockedSources?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(blockedSources!![position], newsClickListener)
    }

    override fun getItemId(position: Int): Long {
        return blockedSources!![position].hashCode().toLong()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(newsSource: NewsSource, newsClickListener: ItemClickListener<NewsSource>) {
            itemView.tvUnblockSource.setOnSafeClickListener {
                newsClickListener.onItemClicked(newsSource)
            }
            val binding: ItemListBlockedSourcesBinding = DataBindingUtil.getBinding(itemView)!!
            binding.newsSource = newsSource
        }
    }

}