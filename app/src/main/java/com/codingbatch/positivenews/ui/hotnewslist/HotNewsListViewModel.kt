package com.codingbatch.positivenews.ui.hotnewslist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.codingbatch.positivenews.data.repository.NewsRepository
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.ui.base.BaseViewModel
import com.codingbatch.positivenews.util.NetworkManager
import com.codingbatch.positivenews.util.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HotNewsListViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository,
    private val networkManager: NetworkManager
) : BaseViewModel() {

    val newsList = MutableLiveData<List<News>>()
    val isLoading = MutableLiveData<Boolean>()
    val searchText = MutableLiveData<String>()
    val isRefreshing = MutableLiveData<Boolean>()
    val isNetworkAvailable = MutableLiveData<Boolean>()

    init {
        fetchHotNewsFromApi()
    }

    private fun fetchHotNewsFromApi(after: String? = null) {
        isLoading.value = true
        disposable.add(newsRepository.fetchHotNewsFromApi(after)
            .andThen(newsRepository.getHotNews())
            .doOnError {
                newsRepository.getHotNews()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate {
                isLoading.value = false
                isRefreshing.value = false
            }
            .subscribeOn(Schedulers.io())
            .subscribe({ news ->
                isNetworkAvailable.value = true
                newsList.plusAssign(news)
            }, { t ->
                isNetworkAvailable.value = false
                t.printStackTrace()
            })
        )
    }

    fun fetchMoreNews(after: String? = null) {
        if (networkManager.isNetworkAvailable())
            fetchHotNewsFromApi(after)
        else
            isNetworkAvailable.value = false
    }

    fun refreshNews() {
        isRefreshing.value = true
        if (networkManager.isNetworkAvailable())
            newsList.value = emptyList()
        else
            isNetworkAvailable.value = false
        fetchHotNewsFromApi()
    }

    fun searchNews() {
        val searchText = searchText.value ?: ""
        newsList.value = newsRepository.searchNews(searchText)
    }

}