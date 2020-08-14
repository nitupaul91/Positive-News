package com.codingbatch.positivenews.ui.newslist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingbatch.positivenews.data.repository.NewsRepository
import com.codingbatch.positivenews.model.News
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsListViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val newsList = MutableLiveData<List<News>>()
    private val disposable = CompositeDisposable()

    init {
        getTopNews()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun getTopNews(after: String? = null) {
        disposable.add(
            newsRepository.getTopNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ news ->
                    newsList.value = news
                }, { throwable ->
                    throwable.printStackTrace()
                })
        )
    }

}