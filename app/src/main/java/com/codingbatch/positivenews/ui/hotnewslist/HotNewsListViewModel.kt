package com.codingbatch.positivenews.ui.hotnewslist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.codingbatch.positivenews.data.repository.NewsRepository
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.ui.base.BaseViewModel
import com.codingbatch.positivenews.util.NetworkManager
import com.codingbatch.positivenews.util.plusAssign

class HotNewsListViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository,
    private val networkManager: NetworkManager
) : BaseViewModel() {

    val newsList = MutableLiveData<List<News>>()
    val isLoading = MutableLiveData<Boolean>()
    val searchText = MutableLiveData<String>()
    val isRefreshing = MutableLiveData<Boolean>()
    val isNetworkUnavailable = MutableLiveData<Boolean>()
    val isSettingsClicked = MutableLiveData<Boolean>()

    init {
        fetchHotNews()
    }

    private fun fetchHotNews(after: String? = null) {
        isLoading.value = true
        if (networkManager.isNetworkAvailable()) {
            disposable.add(
                newsRepository.fetchHotNews(after)
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                    .subscribe({
                        newsList.plusAssign(it)
                        isLoading.value = false
                        isRefreshing.value = false
                        isNetworkUnavailable.value = false
                    }, { t ->

                    })
            )
        } else {
            isLoading.value = false
            isRefreshing.value = false
            isNetworkUnavailable.value = true
        }
    }

    fun fetchMoreNews(after: String? = null) {
        if (networkManager.isNetworkAvailable()) {
            isNetworkUnavailable.value = false
            fetchHotNews(after)
        } else
            isNetworkUnavailable.value = true
    }

    fun refreshNews() {
        isRefreshing.value = true
        if (networkManager.isNetworkAvailable()) {
            isNetworkUnavailable.value = false
            newsList.value = emptyList()
            fetchHotNews()
        } else {
            isRefreshing.value = false
            isNetworkUnavailable.value = true
        }
    }

    fun searchNews() {
        val searchText = searchText.value ?: ""
        newsList.value = newsRepository.searchNews(searchText)
    }

    fun onSettingsClicked() {
        isSettingsClicked.value = true
        isSettingsClicked.value = false
    }

}