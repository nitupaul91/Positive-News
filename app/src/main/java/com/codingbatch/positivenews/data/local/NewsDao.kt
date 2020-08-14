package com.codingbatch.positivenews.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codingbatch.positivenews.model.News
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getAllNews(): Single<List<News>>

    @Query("SELECT * FROM news WHERE isBookmarked = 1")
    fun getBookmarkedNews(): Flowable<List<News>>

    @Query("SELECT * FROM news WHERE :id = id")
    fun getNewsById(id: Int): Single<News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNews(newsList: List<News>)
}