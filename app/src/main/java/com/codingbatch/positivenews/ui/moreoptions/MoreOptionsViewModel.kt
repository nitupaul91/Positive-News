package com.codingbatch.positivenews.ui.moreoptions

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.data.repository.NewsRepository
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MoreOptionsViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
) : BaseViewModel() {

    val isNewsBookmarked = MutableLiveData<Boolean>()
    val isNewsShared = MutableLiveData<Boolean>()
    val snackbarMessageId = MutableLiveData<Int>()

    fun setBookmarkStatus(newsId: String) {
        disposable.add(
            repository.getNewsById(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ news ->
                    isNewsBookmarked.value = news.isBookmarked
                }, { t ->
                    t.printStackTrace()
                })
        )
    }

    fun onBookmarkClicked(news: News) {
        if (news.isBookmarked == true)
            removeBookmark(news)
        else
            bookmarkNews(news)
    }

    private fun bookmarkNews(news: News) {
        disposable.add(
            repository.bookmarkNews(news)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    snackbarMessageId.value = R.string.snackbar_bookmark_added
                    isNewsBookmarked.value = true
                }, { t ->
                    t.printStackTrace()
                })
        )
    }

    private fun removeBookmark(news: News) {
        disposable.add(
            repository.removeBookmark(news)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    snackbarMessageId.value = R.string.snackbar_bookmark_removed
                    isNewsBookmarked.value = false
                }, { t ->
                    t.printStackTrace()
                })
        )
    }

    fun shareNews() {
        isNewsShared.value = true
    }
}