package com.codingbatch.positivenews.data.remote

import com.codingbatch.positivenews.data.remote.response.NewsOverview
import io.reactivex.Single
import javax.inject.Inject

class NewsClient @Inject constructor(
    private val newsRestInterface: NewsRestInterface
) {
    fun getPositiveNews(): Single<NewsOverview> {
        return newsRestInterface.getPositiveNews()
    }
}