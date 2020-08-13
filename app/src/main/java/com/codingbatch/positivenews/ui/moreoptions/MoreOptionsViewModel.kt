package com.codingbatch.positivenews.ui.moreoptions

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MoreOptionsViewModel @ViewModelInject constructor(
) : ViewModel() {

    val isNewsBookmarked = MutableLiveData<Boolean>()
    val isNewsShared = MutableLiveData<Boolean>()

    fun setBookmarkStatus(bookmarkStatus: Boolean) {
        isNewsBookmarked.value = bookmarkStatus
    }

    fun onBookmarkClicked() {
        //TODO refactor and retrieve/change the status directly in the database
        isNewsBookmarked.value = !isNewsBookmarked.value!!
    }

    fun shareNews() {
        isNewsShared.value = true
    }
}