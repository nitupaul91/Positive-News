package com.codingbatch.positivenews.ui.common.bindings

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.ui.common.adapter.NewsListAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView


class ViewBindings {

    companion object {

        private const val DEFAULT = "default"

        @JvmStatic
        @BindingAdapter("news")
        fun setNewsList(recyclerView: RecyclerView, newsList: MutableList<News>?) {
            val adapter = recyclerView.adapter as NewsListAdapter?
            adapter?.setNews(newsList)
        }

        @JvmStatic
        @BindingAdapter("thumbnail")
        fun setThumbnail(imageView: ImageView, url: String) {
            setDefaultThumbnail(
                imageView,
                url
            )
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
        @BindingAdapter("loading")
        fun setLoadingVisibility(animation: LottieAnimationView, isLoading: Boolean) {
            animation.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        @JvmStatic
        @BindingAdapter("onNavigationItemSelected")
        fun setNavigationItemSelected(
            bottomNavigationView: BottomNavigationView,
            listener: BottomNavigationView.OnNavigationItemSelectedListener
        ) {
            bottomNavigationView.setOnNavigationItemSelectedListener(listener)
        }
    }
}