package com.codingbatch.positivenews.util

import android.view.View

class SafeClickListener(
    private var defaultInterval: Int = 800,
    private val onSafeClick: (View) -> Unit
) : View.OnClickListener {

    private var lastTimeClicked: Long = 0

    override fun onClick(v: View) {
        if (System.currentTimeMillis() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = System.currentTimeMillis()
        onSafeClick(v)
    }
}