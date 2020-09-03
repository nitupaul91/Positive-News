package com.codingbatch.positivenews.ui.webview

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.codingbatch.positivenews.ui.base.BaseViewModel

class WebViewModel @ViewModelInject constructor(
) : BaseViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val isBackPressed = MutableLiveData<Boolean>()

    fun loadingStarted() {
        isLoading.value = true
    }

    fun loadingComplete() {
        isLoading.value = false
    }

    fun onBackPressed() {
        isBackPressed.value = true
    }

}