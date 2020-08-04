package com.codingbatch.positivenews.data.remote

import com.codingbatch.positivenews.data.remote.response.NewsOverview
import io.reactivex.Single
import retrofit2.http.GET

interface NewsRestInterface {

    @GET("/r/UpliftingNews/.json")
    fun getPositiveNews(): Single<NewsOverview>
}