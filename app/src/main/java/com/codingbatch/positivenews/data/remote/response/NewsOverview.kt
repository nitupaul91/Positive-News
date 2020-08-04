package com.codingbatch.positivenews.data.remote.response

import com.google.gson.annotations.SerializedName

open class NewsOverview(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("data")
    val newsData: NewsData?
) {
    constructor() : this("",null)
}