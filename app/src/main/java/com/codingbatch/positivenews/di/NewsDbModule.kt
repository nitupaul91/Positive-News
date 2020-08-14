package com.codingbatch.positivenews.di

import android.app.Application
import androidx.room.Room
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.data.local.NewsDao
import com.codingbatch.positivenews.data.local.NewsDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NewsDbModule {

    @Provides
    @Singleton
    fun getNewsDb(application: Application): NewsDb {
        return Room.databaseBuilder(
            application,
            NewsDb::class.java,
            application.getString(R.string.news_database_name)
        )
                //TODO add migrations
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun getNewsDao(newsDb: NewsDb) : NewsDao {
        return newsDb.newsDao();
    }
}