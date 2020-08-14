package com.codingbatch.positivenews.data.repository

import com.codingbatch.positivenews.data.local.NewsDao
import com.codingbatch.positivenews.data.remote.NewsApi
import com.codingbatch.positivenews.data.remote.response.NewsOverview
import com.codingbatch.positivenews.model.News
import io.reactivex.Single
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
) {

    fun getTopNews(): Single<List<News>> {
        return newsApi.getTopNews()
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
        return Single.just(newsList)
    }

    private fun saveNews(newsList: List<News>) {
        newsDao.saveNews(newsList)
    }

    private fun getSavedNews(): Single<List<News>> {
        return newsDao.getAllNews()
    }
}