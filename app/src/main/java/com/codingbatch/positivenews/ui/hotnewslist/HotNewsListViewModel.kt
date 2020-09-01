package com.codingbatch.positivenews.ui.hotnewslist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.codingbatch.positivenews.data.repository.NewsRepository
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.ui.base.BaseViewModel
import com.codingbatch.positivenews.util.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HotNewsListViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository
) : BaseViewModel() {

    val newsList = MutableLiveData<List<News>>()
    val isLoading = MutableLiveData<Boolean>()
    val searchText = MutableLiveData<String>()

    init {
        newsList.value = mutableListOf()
        getHotNews()
    }

    fun getHotNews(after: String? = null) {
        isLoading.value = true
        disposable.add(
            newsRepository.getHotNews(after)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doAfterTerminate { isLoading.value = false }
                .subscribe({ news ->
                    newsList.plusAssign(news)
                }, { throwable ->
                    throwable.printStackTrace()
                })
        )
    }

    fun searchNews() {
        val searchText = searchText.value ?: ""
        if (searchText.isEmpty()) {
//todo display snackbar empty search
            return
        }
        newsList.value = newsRepository.searchNews(searchText)
    }

}