package com.codingbatch.positivenews.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "news")
data class News(
    val title: String,
    val url: String,
    val thumbnail: String,
    val NSFW: Boolean,
    val likes: Int,
    val fullName: String?,
    val domain: String,
    var isBookmarked: Boolean = false,
    @PrimaryKey
    val id: String
) : Parcelable