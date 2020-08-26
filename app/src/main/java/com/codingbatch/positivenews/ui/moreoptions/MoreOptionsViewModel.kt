package com.codingbatch.positivenews.ui.moreoptions

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingbatch.positivenews.data.repository.NewsRepository
import com.codingbatch.positivenews.model.News
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MoreOptionsViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val isNewsBookmarked = MutableLiveData<Boolean>()
    val isNewsShared = MutableLiveData<Boolean>()

//    fun setBookmarkStatus(bookmarkStatus: Boolean) {
//        isNewsBookmarked.value = bookmarkStatus
//    }

    fun onBookmarkClicked(news: News) {
        if (news.isBookmarked)
            removeBookmark(news)
        else
            bookmarkNews(news)
    }

    private fun bookmarkNews(news: News) {
        repository.bookmarkNews(news)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                isNewsBookmarked.value = true
            }
    }

    private fun removeBookmark(news: News) {
        repository.removeBookmark(news)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                isNewsBookmarked.value = false
            }
    }

    fun shareNews() {
        isNewsShared.value = true
    }
}