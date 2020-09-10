package com.codingbatch.positivenews.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "news_source")
data class NewsSource(
    @PrimaryKey
    val domain: String
) : Parcelable