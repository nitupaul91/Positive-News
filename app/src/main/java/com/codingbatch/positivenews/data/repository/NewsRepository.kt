package com.codingbatch.positivenews.data.repository

import com.codingbatch.positivenews.data.local.NewsDao
import com.codingbatch.positivenews.data.local.NewsSourceDao
import com.codingbatch.positivenews.data.remote.NewsApi
import com.codingbatch.positivenews.data.remote.response.NewsOverview
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.model.NewsSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import rx.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao,
    private val newsSourceDao: NewsSourceDao
) {

    private var newsList: MutableList<News> = mutableListOf()
    private val blockedNewsSources: BehaviorSubject<List<NewsSource>> = BehaviorSubject.create()

    fun fetchHotNews(after: String? = null): Single<List<News>> {
        return newsApi.getHotNews(after = after)
            .flatMap(this::mapApiResponseToNews)
            .flatMap(this::filterBlockedNews)
    }

    private fun mapApiResponseToNews(overview: NewsOverview): Single<List<News>> {
        val newsList = mutableListOf<News>()
        overview.newsData?.children?.forEach { child ->
            newsList.add(
                News(
                    child.data.fullName,
                    child.data.title,
                    child.data.url,
                    child.data.thumbnail,
                    child.data.NSFW,
                    child.data.ups,
                    child.data.fullName,
                    child.data.domain,
                    child.data.created
                )
            )
        }
        this.newsList = newsList
        return Single.just(newsList)
    }

    private fun filterBlockedNews(
        newsList: List<News>
    ): Single<List<News>> {
        val hashSet = HashSet<String>()
        val list = mutableListOf<News>()
        list.addAll(newsList)
        return getBlockedSources()
            .flatMap {
                it.forEach { newsSource ->
                    hashSet.add(newsSource.domain)
                }
                newsList.forEach { newsItem ->
                    if (hashSet.contains(newsItem.domain))
                        list.remove(newsItem)
                }
                Single.just(list)
            }
    }

    private fun getBlockedSources(): Single<List<NewsSource>> {
        return newsSourceDao.getBlockedNewsSources()
            .flatMap {
                blockedNewsSources.onNext(it)
                Single.just(it)
            }
    }

    fun bookmarkNews(news: News): Completable {
        news.isBookmarked = true
        return newsDao.saveNewsItem(news)
    }

    fun getBookmarkedNews(): Flowable<List<News>> {
        return newsDao.getBookmarkedNews()
    }

    fun deleteAllBookmarkedNews(): Completable {
        return newsDao.deleteAllBookmarkedNews()
    }

    fun removeBookmark(news: News): Completable {
        news.isBookmarked = false
        return newsDao.saveNewsItem(news)
    }

    fun getNewsById(newsId: String): Single<News> {
        return newsDao.getNewsById(newsId)
    }

    fun searchNews(searchText: String): List<News>? {
        val news = mutableListOf<News>()
        newsList.forEach { newsItem ->
            if (newsItem.title.contains(searchText, true))
                news.add(newsItem)
        }
        return news
    }

    fun blockNewsSource(newsSource: NewsSource): Completable {
        return newsSourceDao.blockNewsSource(newsSource)
            .andThen(getBlockedSources())
            .flatMapCompletable {
                blockedNewsSources.onNext(it)
                Completable.complete()
            }
            .andThen {
                filterBlockedNews(newsList)
                Completable.complete()
            }
    }

    fun unblockNewsSource(domain: String): Completable {
        return newsSourceDao.unblockNewsSource(domain).andThen(getBlockedSources())
            .flatMapCompletable {
                blockedNewsSources.onNext(it)
                Completable.complete()
            }
    }

    fun getBlockedSourcesSubject() = blockedNewsSources

}
