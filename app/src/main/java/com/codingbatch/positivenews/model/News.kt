package com.codingbatch.positivenews.model

data class News(
    private val title: String,
    private val url: String,
    private val thumbnail: String,
    private val NSFW: Boolean,
    private val likes: Int
)