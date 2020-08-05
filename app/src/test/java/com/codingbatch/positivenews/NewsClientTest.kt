package com.codingbatch.positivenews

import com.codingbatch.positivenews.data.remote.NewsClient
import com.codingbatch.positivenews.data.remote.NewsRestInterface
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

    @Mock
    lateinit var newsRestInterface: NewsRestInterface

    @InjectMocks
    lateinit var newsClient: NewsClient

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun fetch_news_success() {
        val dummyPositiveNewsSingle = Single.just(newsOverview)
        `when`(newsClient.getNews()).thenReturn(dummyPositiveNewsSingle)

        val resultPositiveNewsSingle = newsClient.getNews()
        Mockito.verify(newsRestInterface).getNews()
        assertEquals(dummyPositiveNewsSingle, resultPositiveNewsSingle)
    }
}
