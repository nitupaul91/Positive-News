package com.codingbatch.positivenews.data.remote.response

import com.google.gson.annotations.SerializedName

class Children(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("data")
    val data: ChildrenData
)