package com.codingbatch.positivenews.data.repository

import android.content.Context
import com.codingbatch.positivenews.data.local.NewsDao
import com.codingbatch.positivenews.data.remote.NewsApi
import com.codingbatch.positivenews.data.remote.response.NewsOverview
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.util.Constants
import com.codingbatch.positivenews.util.NetworkStatus
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao,
    private val context: Context
) {

    private var newsList: List<News> = listOf()
    private var newsCount: Int = 0

    fun getBookmarkedNews(): Flowable<List<News>> {
        return newsDao.getBookmarkedNews()
    }

    fun searchNews(searchText: String): List<News>? {
        val news = mutableListOf<News>()
        newsList.forEach { newsItem ->
            if (newsItem.title.contains(searchText, true))
                news.add(newsItem)
        }
        return news
    }

    fun bookmarkNews(news: News): Completable {
        news.isBookmarked = true
        return newsDao.saveNewsItem(news)
    }

    fun removeBookmark(news: News): Completable {
        news.isBookmarked = false
        return newsDao.saveNewsItem(news)
    }

    fun getNewsById(newsId: String): Single<News> {
        return newsDao.getNewsById(newsId)
    }

    fun getHotNews(after: String? = null): Single<List<News>> {
        if (NetworkStatus.isConnected(context) && newsCount > Constants.MAX_NEWS_ENTRIES)
            deleteNonBookmarkedNews()

        return newsApi.getHotNews(after = after)
            .flatMap(this::mapApiResponseToNews)
            .onErrorResumeNext {
                getSavedNews()
            }
    }

    private fun deleteNonBookmarkedNews(): Completable {
        return newsDao.deleteNonBookmarkedNews()
    }

    private fun mapApiResponseToNews(overview: NewsOverview): Single<List<News>> {
        val newsList = mutableListOf<News>()
        overview.newsData?.children?.forEach { child ->
            newsList.add(
                News(
                    child.data.title,
                    child.data.url,
                    child.data.thumbnail,
                    child.data.NSFW,
                    child.data.ups,
                    child.data.fullName,
                    child.data.domain
                )
            )
        }
        saveNews(newsList)
        cacheNews(newsList)
        updateNewsCount(newsList.size)
        return Single.just(newsList)
    }

    private fun cacheNews(newsList: List<News>) {
        this.newsList = newsList
    }

    private fun saveNews(newsList: List<News>) {
        newsDao.saveNews(newsList)
    }

    private fun updateNewsCount(count: Int) {
        newsCount += count
    }

    private fun getSavedNews(): Single<List<News>> {
        return newsDao.getAllNews()
    }
}