package com.codingbatch.positivenews.model

data class News(
    val title: String,
    val url: String,
    val thumbnail: String,
    val NSFW: Boolean,
    val likes: Int,
    val domain: String
)