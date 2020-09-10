package com.codingbatch.positivenews.ui.moreoptions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.databinding.BottomFragmentMoreOptionsBinding
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.util.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_fragment_more_options.*

@AndroidEntryPoint
class MoreOptionsBottomFragment : BottomSheetDialogFragment() {

    private val moreOptionsViewModel: MoreOptionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_fragment_more_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val news = requireArguments().getParcelable<News>(Constants.NEWS)
        setupDataBinding(view, news!!)

        moreOptionsViewModel.setBookmarkStatus(news.id)

        moreOptionsViewModel.isNewsShared.observe(viewLifecycleOwner, Observer {
            if (it)
                shareNews(news)
        })
        moreOptionsViewModel.snackbarMessageId.observe(
            viewLifecycleOwner,
            Observer { snackbarMessageId ->
                Snackbar.make(rootLayout, snackbarMessageId, Snackbar.LENGTH_SHORT)
                    .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            this@MoreOptionsBottomFragment.dismiss()
                        }
                    })
                    .show()
            })
    }

    private fun shareNews(news: News) {
        val sharingIntent = Intent(Intent.ACTION_SEND).apply {
            this.type = Constants.TEXT_PLAIN
            this.putExtra(Intent.EXTRA_TEXT, news.url)
        }
        startActivity(sharingIntent)
    }

    private fun setupDataBinding(view: View, news: News) {
        val binding = BottomFragmentMoreOptionsBinding.bind(view)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            binding.viewModel = moreOptionsViewModel
            binding.news = news
        }
    }

    companion object {
        fun newInstance(news: News?): MoreOptionsBottomFragment {
            val fragment = MoreOptionsBottomFragment()
            val args = Bundle()
            args.putParcelable(Constants.NEWS, news)
            fragment.arguments = args
            return fragment
        }
    }
}