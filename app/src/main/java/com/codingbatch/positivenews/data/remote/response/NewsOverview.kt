package com.codingbatch.positivenews.data.remote.response

import com.google.gson.annotations.SerializedName

class NewsOverview(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("data")
    val newsData: NewsData
)