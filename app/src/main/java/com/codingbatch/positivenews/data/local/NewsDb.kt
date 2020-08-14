package com.codingbatch.positivenews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codingbatch.positivenews.model.News

@Database(entities = [News::class], version = 3, exportSchema = false )
 abstract class NewsDb : RoomDatabase() {
    abstract fun newsDao() : NewsDao
}