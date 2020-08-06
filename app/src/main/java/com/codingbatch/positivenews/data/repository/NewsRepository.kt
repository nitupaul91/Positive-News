package com.codingbatch.positivenews.data.repository

import com.codingbatch.positivenews.data.local.NewsDao
import com.codingbatch.positivenews.data.remote.NewsClient
import com.codingbatch.positivenews.data.remote.response.NewsOverview
import com.codingbatch.positivenews.model.News
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsClient: NewsClient,
    private val newsDao: NewsDao
) {

    private fun refreshNews(): Single<List<News>> {
        return newsClient.getNews()
            .flatMap(this::mapApiResponseToNews)
            .doOnSuccess(this::saveNews)
    }

    fun getNews(): Flowable<List<News>> {
        refreshNews()
            .subscribeOn(Schedulers.io())
            .subscribe()
        return newsDao.getAllNews()
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
                    child.data.domain
                )
            )
        }
        return Single.just(newsList)
    }

    private fun saveNews(newsList: List<News>) {
        newsDao.saveNews(newsList)
    }
}