package com.codingbatch.positivenews

import com.codingbatch.positivenews.data.remote.NewsApi
import com.codingbatch.positivenews.data.remote.response.NewsOverview
import io.reactivex.Single
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class NewsClientTest {

    @Mock
    lateinit var newsOverview: NewsOverview

    @InjectMocks
    lateinit var newsApi: NewsApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun fetch_news_success() {
        val dummyPositiveNewsSingle = Single.just(newsOverview)
        `when`(newsApi.getTopNews()).thenReturn(dummyPositiveNewsSingle)

        val resultPositiveNewsSingle = newsApi.getTopNews()
        Mockito.verify(newsApi).getTopNews()
        assertEquals(dummyPositiveNewsSingle, resultPositiveNewsSingle)
    }
}
