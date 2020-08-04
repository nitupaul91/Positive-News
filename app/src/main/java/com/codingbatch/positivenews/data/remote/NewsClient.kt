package com.codingbatch.positivenews.data.remote

import com.codingbatch.positivenews.data.remote.response.NewsOverview
import io.reactivex.Single
import javax.inject.Inject

const val URL = "https://www.reddit.com/"

class NewsClient @Inject constructor(
    private val newsRestInterface: NewsRestInterface
) {
    fun getPositiveNews(): Single<NewsOverview> {
        return newsRestInterface.getPositiveNews()
    }
}