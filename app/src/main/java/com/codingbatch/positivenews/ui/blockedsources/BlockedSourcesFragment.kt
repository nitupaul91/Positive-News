package com.codingbatch.positivenews.ui.blockedsources

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.databinding.FragmentBlockedSourcesBinding
import com.codingbatch.positivenews.model.NewsSource
import com.codingbatch.positivenews.ui.base.BaseFragment
import com.codingbatch.positivenews.ui.common.adapter.BlockedSourcesAdapter
import com.codingbatch.positivenews.util.ItemClickListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_blocked_sources.*

@AndroidEntryPoint
class BlockedSourcesFragment : BaseFragment(), ItemClickListener<NewsSource> {

    private val viewModel: BlockedSourcesViewModel by viewModels()
    private val adapter = BlockedSourcesAdapter(this)

    override fun getLayoutId() = R.layout.fragment_blocked_sources

    override fun setupDataBinding(view: View) {
        val binding = FragmentBlockedSourcesBinding.bind(view)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@BlockedSourcesFragment.viewModel
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvBlockedNewsSources.adapter = adapter

        viewModel.snackbarMessageId.observe(viewLifecycleOwner, Observer { snackbarMessageId ->
            Snackbar.make(rootLayoutBlockedSources, snackbarMessageId, Snackbar.LENGTH_SHORT)
        })
    }

    override fun onItemClicked(item: NewsSource) {
        viewModel.unblockNewsSource(item)
    }
}
