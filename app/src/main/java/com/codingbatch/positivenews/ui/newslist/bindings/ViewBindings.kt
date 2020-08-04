package com.codingbatch.positivenews.ui.newslist.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.ui.newslist.NewsListAdapter


class ViewBindings {

    companion object {
        @JvmStatic
        @BindingAdapter("android:news")
        fun setNewsList(recyclerView: RecyclerView, newsList: MutableList<News>?) {
            val adapter = recyclerView.adapter as NewsListAdapter
            adapter.newsList = newsList
        }

        @JvmStatic
        @BindingAdapter("android:thumbnail")
        fun setThumbnail(imageView: ImageView, url: String) {
            Glide.with(imageView.context)
                .load(url)
                .dontAnimate()
                .into(imageView)
        }
    }
}