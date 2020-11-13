package com.codingbatch.positivenews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.model.NewsSource

@Database(entities = [News::class, NewsSource::class], version = 12, exportSchema = false)
abstract class NewsDb : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun newsSourceDao(): NewsSourceDao
}