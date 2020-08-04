package com.codingbatch.positivenews.data.remote.response

import com.google.gson.annotations.SerializedName

class NewsData(
    @SerializedName("modhash")
    val modhash: String,
    @SerializedName("dist")
    val dist: Int,
    @SerializedName("children")
    val children: List<Children>,
    @SerializedName("after")
    val after: String,
    @SerializedName("before")
    val before: String
)
