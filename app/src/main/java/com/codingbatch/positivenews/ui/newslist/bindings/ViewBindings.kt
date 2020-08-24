package com.codingbatch.positivenews.ui.newslist.bindings

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.ui.newslist.NewsListAdapter


class ViewBindings {

    companion object {

        private const val DEFAULT = "default"

        @JvmStatic
        @BindingAdapter("android:news")
        fun setNewsList(recyclerView: RecyclerView, newsList: MutableList<News>?) {
            val adapter = recyclerView.adapter as NewsListAdapter
            adapter.newsList = newsList
        }

        @JvmStatic
        @BindingAdapter("android:thumbnail")
        fun setThumbnail(imageView: ImageView, url: String) {
            setDefaultThumbnail(imageView, url)
            Glide.with(imageView.context)
                .load(url)
                .dontAnimate()
                .into(imageView)
        }

        private fun setDefaultThumbnail(imageView: ImageView, url: String) {
            if (url == DEFAULT)
                imageView.setBackgroundResource(R.drawable.ic_news_placeholder)
        }

        @JvmStatic
        @BindingAdapter("android:loading")
        fun setLoadingVisibility(animation: LottieAnimationView, isLoading: Boolean) {
            animation.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}