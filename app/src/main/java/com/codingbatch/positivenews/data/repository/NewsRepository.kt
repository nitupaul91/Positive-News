package com.codingbatch.positivenews.data.repository

import com.codingbatch.positivenews.data.local.NewsDao
import com.codingbatch.positivenews.data.remote.NewsApi
import com.codingbatch.positivenews.data.remote.response.NewsOverview
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.util.Constants
import com.codingbatch.positivenews.util.NetworkManager
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao,
    private val networkManager: NetworkManager
) {

    private var newsList: MutableList<News> = mutableListOf()
    private var newsCount: Int = 0

    fun fetchHotNewsFromApi(after: String? = null): Completable {
        return if (networkManager.isNetworkAvailable() && newsCount > Constants.MAX_NEWS_ENTRIES)
            deleteNonBookmarkedNews().andThen(
                newsApi.getHotNews(after = after)
                    .flatMap(this::mapApiResponseToNews)
                    .flatMapCompletable(this::saveNews)
            )
        else
            newsApi.getHotNews(after = after)
                .flatMap(this::mapApiResponseToNews)
                .flatMapCompletable(this::saveNews)
    }

    fun deleteNonBookmarkedNews(): Completable {
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
                    child.data.domain,
                    false,
                    child.data.fullName
                )
            )
        }
        this.newsList = newsList
        return Single.just(newsList)
    }

    fun getHotNews(): Single<List<News>> {
        return newsDao.getAllNews()
    }

    fun bookmarkNews(news: News): Completable {
        news.isBookmarked = true
        return newsDao.saveNewsItem(news)
    }

    fun getBookmarkedNews(): Flowable<List<News>> {
        return newsDao.getBookmarkedNews()
    }

    fun deleteAllBookmarkedNews(): Completable {
        return newsDao.deleteAllBookmarkedNews()
    }

    fun removeBookmark(news: News): Completable {
        news.isBookmarked = false
        return newsDao.saveNewsItem(news)
    }

    fun getNewsById(newsId: String): Single<News> {
        return newsDao.getNewsById(newsId)
    }

    fun searchNews(searchText: String): List<News>? {
        val news = mutableListOf<News>()
        newsList.forEach { newsItem ->
            if (newsItem.title.contains(searchText, true))
                news.add(newsItem)
        }
        return news
    }

    private fun saveNews(newsList: List<News>): Completable {
        updateNewsCount(newsList.size)
        return newsDao.saveNews(newsList)
    }

    private fun updateNewsCount(count: Int) {
        newsCount += count
    }
}