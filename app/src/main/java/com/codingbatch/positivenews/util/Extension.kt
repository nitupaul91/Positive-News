package com.codingbatch.positivenews.util

import android.view.View
import androidx.lifecycle.MutableLiveData

fun View.setOnSafeClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

operator fun <T> MutableLiveData<List<T>>.plusAssign(items: List<T>) {
    val value = this.value ?: emptyList()
    this.value = value + items
}