package com.codingbatch.positivenews.ui.bookmarkednewslist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.codingbatch.positivenews.data.repository.NewsRepository
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BookmarkedNewsListViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
) : BaseViewModel() {

    val bookmarkedNewsList = MutableLiveData<List<News>>()

    init {
        getBookmarkedNews()
    }

    private fun getBookmarkedNews() {
        disposable.add(
            repository.getBookmarkedNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ news ->
                    bookmarkedNewsList.value = news
                }, { throwable ->
                    throwable.printStackTrace()
                })
        )
    }
}