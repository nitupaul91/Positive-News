package com.codingbatch.positivenews.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "news")
data class News(
    @PrimaryKey
    val id: String = "",
    val title: String = "",
    val url: String = "",
    val thumbnail: String = "",
    val NSFW: Boolean? = null,
    val likes: Int? = null,
    val fullName: String? = "",
    val domain: String = "",
    val created: Long? = null,
    var isBookmarked: Boolean? = false
) : Parcelable