package com.codingbatch.positivenews.ui.blockedsources

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.data.repository.NewsRepository
import com.codingbatch.positivenews.model.NewsSource
import com.codingbatch.positivenews.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BlockedSourcesViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
) : BaseViewModel() {

    val blockedSources = MutableLiveData<List<NewsSource>>()
    val isPlaceHolderTextVisible = MutableLiveData<Boolean>()
    val snackbarMessageId = MutableLiveData<Int>()

    private val blockedSourcesSubscription =
        repository.getBlockedSourcesSubject()
            .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe({
                blockedSources.value = it
                isPlaceHolderTextVisible.value = it.isEmpty()
            }) { t ->
                t.printStackTrace()
            }

    fun unblockNewsSource(newsSource: NewsSource) {
        disposable.add(repository.unblockNewsSource(newsSource.domain)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                snackbarMessageId.value = R.string.snackbar_news_source_unblocked
            }) { t ->
                t.printStackTrace()
            })
    }

    override fun onCleared() {
        super.onCleared()
        blockedSourcesSubscription.unsubscribe()
    }


}
