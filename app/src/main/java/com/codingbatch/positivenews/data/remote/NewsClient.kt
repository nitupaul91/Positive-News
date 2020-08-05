package com.codingbatch.positivenews.data.remote

import com.codingbatch.positivenews.data.remote.response.NewsOverview
import io.reactivex.Single
import javax.inject.Inject

class NewsClient @Inject constructor(
    private val newsRestInterface: NewsRestInterface
) {
    fun getNews(): Single<NewsOverview> {
        return newsRestInterface.getNews()
    }

    fun getNewsAfter(name: String): Single<NewsOverview> {
        return newsRestInterface.getNewsAfter(name)
    }
}