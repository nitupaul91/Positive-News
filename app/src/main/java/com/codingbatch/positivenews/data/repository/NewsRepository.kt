package com.codingbatch.positivenews.data.repository

import com.codingbatch.positivenews.data.remote.NewsClient
import com.codingbatch.positivenews.data.remote.response.ChildrenData
import com.codingbatch.positivenews.data.remote.response.NewsOverview
import com.codingbatch.positivenews.model.News
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsClient: NewsClient) {

    fun getPositiveNews(): Single<List<News>> {
        return newsClient.getPositiveNews().flatMap(this::mapApiResponseToNews)
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
                    child.data.domain,
                    getTimeSincePosted(child.data)
                )
            )
        }
        return Single.just(newsList)
    }

    private fun getTimeSincePosted(child: ChildrenData): String {
        val hours = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis() - child.created * 1000)
        return getDaysSincePosted(hours).toString()
    }

    private fun getDaysSincePosted(hours: Long) =
        if (hours > 24) TimeUnit.HOURS.toDays(hours) else hours
}