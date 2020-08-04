package com.codingbatch.positivenews.data.remote.response

import com.google.gson.annotations.SerializedName

class ChildrenData(
    @SerializedName("subreddit")
    val subreddit: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("link_flair_text")
    val linkFlairText: String,
    @SerializedName("score")
    val score: String,
    @SerializedName("ups")
    val ups: Int,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("over_18")
    val nsfw: Boolean
)
