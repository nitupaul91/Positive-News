package com.codingbatch.positivenews.ui.webview

import android.webkit.*

const val BLANK_PAGE = "about:blank"

class WebClient(private val listener: PageLoadProgress) : WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        when (newProgress) {
            10 -> listener.onLoadingStarted()
            100 -> listener.onLoadingComplete()
        }
    }
}

interface PageLoadProgress {
    fun onLoadingStarted()
    fun onLoadingComplete()
}

class SimpleWebClient(private val pageResult: PageResult) : WebViewClient() {
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        pageResult.onErrorReceived()
        view?.loadUrl(BLANK_PAGE);
    }
}

interface PageResult {
    fun onErrorReceived()
}
