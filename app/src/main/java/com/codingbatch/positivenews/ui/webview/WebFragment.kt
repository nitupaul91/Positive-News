package com.codingbatch.positivenews.ui.webview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.databinding.FragmentWebBinding
import com.codingbatch.positivenews.ui.base.BaseFragment
import com.codingbatch.positivenews.util.Constants
import kotlinx.android.synthetic.main.fragment_web.*

class WebFragment : BaseFragment() {

    private val webViewModel: WebViewModel by viewModels()

    override fun getLayoutId() = R.layout.fragment_web

    override fun setupDataBinding(view: View) {
        val binding = FragmentWebBinding.bind(view)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = webViewModel
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWebView(arguments?.getString(Constants.NEWS_URL))

        webViewModel.isBackPressed.observe(viewLifecycleOwner, Observer { isBackPressed ->
            if (isBackPressed)
                navigateBack()
        })
    }

    private fun setupWebView(url: String?) {
        webView.apply {
            webChromeClient = WebClient(object : PageLoadProgress {
                override fun onLoadingStarted() {
                    webViewModel.loadingStarted()
                }

                override fun onLoadingComplete() {
                    webViewModel.loadingComplete()
                }
            })

            webViewClient = SimpleWebClient(object : PageResult{
                override fun onErrorReceived() {
                    webViewModel.setError()
                }
            })
            loadUrl(url)
        }
    }
}