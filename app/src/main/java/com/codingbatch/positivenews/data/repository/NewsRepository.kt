package com.codingbatch.positivenews.data.repository

import com.codingbatch.positivenews.data.local.NewsDao
import com.codingbatch.positivenews.data.remote.NewsApi
import com.codingbatch.positivenews.data.remote.response.NewsOverview
import com.codingbatch.positivenews.model.News
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
) {

    private var newsList: List<News> = listOf()

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

    fun getTopNews(after: String? = null): Single<List<News>> {
        return newsApi.getTopNews(after = after)
            .flatMap(this::mapApiResponseToNews)
            .onErrorResumeNext {
                getSavedNews()
            }
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
        return Single.just(newsList)
    }

    private fun cacheNews(newsList: List<News>) {
        this.newsList = newsList
    }

    private fun saveNews(newsList: List<News>) {
        newsDao.saveNews(newsList)
    }

    private fun getSavedNews(): Single<List<News>> {
        return newsDao.getAllNews()
    }
}