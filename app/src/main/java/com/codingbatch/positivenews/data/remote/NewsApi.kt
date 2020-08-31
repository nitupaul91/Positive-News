package com.codingbatch.positivenews.data.remote

import com.codingbatch.positivenews.data.remote.response.NewsOverview
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/r/UpliftingNews/hot.json?limit=20")
    fun getTopNews(
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): Single<NewsOverview>

    @GET("/r/UpliftingNews/search/.json?limit=100&sort=relevance")
    fun searchNews(
        @Query("q") searchText: String
    ): Single<NewsOverview>
}