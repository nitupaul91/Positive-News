package com.codingbatch.positivenews.ui.webview

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.data.repository.NewsRepository
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.model.NewsSource
import com.codingbatch.positivenews.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WebViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
) : BaseViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val isBackPressed = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Boolean>()
    val isMoreOptionsClicked = MutableLiveData<Boolean>()
    val snackbarMessageId = MutableLiveData<Int>()

    fun onMoreOptionsClicked() {
        isMoreOptionsClicked.value = true
    }

    fun loadingStarted() {
        isLoading.value = true
    }

    fun loadingComplete() {
        isLoading.value = false
    }

    fun onBackPressed() {
        isBackPressed.value = true
    }

    fun blockNewsSource(news: News) {
        isLoading.value = true
        disposable.add(
            repository.blockNewsSource(NewsSource(news.domain))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doAfterTerminate {
                    isLoading.value = false
                }
                .subscribe({
                    snackbarMessageId.value = R.string.snackbar_news_source_blocked
                }, { t ->
                    t.printStackTrace()
                })
        )
    }

    fun setError() {
        isError.value = true
    }
}