package com.codingbatch.positivenews.ui.webview

import android.webkit.WebChromeClient
import android.webkit.WebView

class WebClient(private val listener: OnPageLoadedListener) : WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        when (newProgress) {
            10 -> listener.onLoadingStarted()
            100 -> listener.onLoadingComplete()
        }
    }


}

interface OnPageLoadedListener {
    fun onLoadingStarted()
    fun onLoadingComplete()
}