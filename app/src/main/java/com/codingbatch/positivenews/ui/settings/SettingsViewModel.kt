package com.codingbatch.positivenews.ui.settings

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.ui.base.BaseViewModel

class SettingsViewModel @ViewModelInject constructor() : BaseViewModel() {

    val destinationId = MutableLiveData<Int>()

    fun changeDestinationId(view: View) {
    }

}
