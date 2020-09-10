package com.codingbatch.positivenews.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codingbatch.positivenews.model.NewsSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface NewsSourceDao {

    @Query("SELECT * FROM news_source")
    fun getBlockedNewsSources(): Single<List<NewsSource>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun blockNewsSource(newsSource: NewsSource): Completable
}