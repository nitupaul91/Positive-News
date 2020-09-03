package com.codingbatch.positivenews.ui.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.ui.base.BaseFragment
import com.codingbatch.positivenews.util.Constants
import kotlinx.android.synthetic.main.fragment_web.*

class WebFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString(Constants.NEWS_URL)

        webView.loadUrl(url)
    }
}