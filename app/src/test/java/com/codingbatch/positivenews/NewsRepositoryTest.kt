package com.codingbatch.positivenews

import com.codingbatch.positivenews.data.local.NewsDao
import com.codingbatch.positivenews.data.remote.NewsApi
import com.codingbatch.positivenews.data.repository.NewsRepository
import com.codingbatch.positivenews.model.News
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class NewsRepositoryTest {

    @Mock
    lateinit var newsList: List<News>

    @Mock
    lateinit var newsApi: NewsApi

    @Mock
    lateinit var newsDao: NewsDao

    @InjectMocks
    lateinit var newsRepository: NewsRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun fetch_news_success() {
        val dummyNewsListSingle = Single.just(newsList)
        `when`(newsRepository.fetchHotNewsFromApi()).thenReturn(dummyNewsListSingle)

        val resultNewsListSingle = newsRepository.fetchHotNewsFromApi()
        Mockito.verify(newsRepository).fetchHotNewsFromApi()
        assertEquals(dummyNewsListSingle, resultNewsListSingle)
    }

    @Test
    fun search_news_success() {
        val dummySearchResult = listOf<News>()
        `when`(newsRepository.searchNews("test")).thenReturn(dummySearchResult)

        val resultNewsList = newsRepository.searchNews("test")
        Mockito.verify(newsRepository).searchNews("test")
        assertEquals(dummySearchResult, resultNewsList)
    }
}
