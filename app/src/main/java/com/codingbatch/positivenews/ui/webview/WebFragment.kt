package com.codingbatch.positivenews.ui.webview

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.databinding.FragmentWebBinding
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.ui.base.BaseFragment
import com.codingbatch.positivenews.ui.moreoptions.MoreOptionsBottomFragment
import com.codingbatch.positivenews.util.Constants
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_web.*

@AndroidEntryPoint
class WebFragment : BaseFragment() {

    private var news: News? = null

    private val webViewModel: WebViewModel by viewModels()

    override fun getLayoutId() = R.layout.fragment_web

    override fun setupDataBinding(view: View) {
        news = arguments?.getParcelable(Constants.NEWS)
        val binding = FragmentWebBinding.bind(view)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = webViewModel
            news = this@WebFragment.news
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView(news?.url)

        webViewModel.isMoreOptionsClicked.observe(viewLifecycleOwner, Observer {
            MoreOptionsBottomFragment.newInstance(news)
                .show(childFragmentManager, Constants.MORE_OPTIONS_TAG)
        })

        webViewModel.snackbarMessageId.observe(viewLifecycleOwner, Observer { snackbarMessageId ->
            Snackbar.make(rootLayoutWeb, snackbarMessageId, Snackbar.LENGTH_LONG)
                .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        try {
                            navigateBack()
                        } catch (exception: IllegalStateException) {
                            Log.d("Already navigated", exception.localizedMessage)
                        }
                    }
                })
                .show()
        })

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

            webViewClient = SimpleWebClient(object : PageResult {
                override fun onErrorReceived() {
                    webViewModel.setError()
                }
            })
            loadUrl(url)
        }
    }
}