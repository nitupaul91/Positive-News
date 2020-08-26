package com.codingbatch.positivenews.ui.newslist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.codingbatch.positivenews.data.repository.NewsRepository
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.ui.base.BaseViewModel
import com.codingbatch.positivenews.util.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsListViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository
) : BaseViewModel() {

    val newsList = MutableLiveData<List<News>>()
    val isLoading = MutableLiveData<Boolean>()

    init {
        newsList.value = mutableListOf()
        getTopNews()
    }

    fun getTopNews(after: String? = null) {
        isLoading.value = true
        disposable.add(
            newsRepository.getTopNews(after)
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

}