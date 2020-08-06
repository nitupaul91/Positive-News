package com.codingbatch.positivenews.data.remote

import com.codingbatch.positivenews.data.remote.response.NewsOverview
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsRestInterface {

    @GET("/r/UpliftingNews/.json?limit=25")
    fun getNews(): Single<NewsOverview>

    @GET("/r/UpliftingNews/.json?limit=25?after{name}")
    fun getNewsAfter(@Path("name") newsName: String): Single<NewsOverview>
}