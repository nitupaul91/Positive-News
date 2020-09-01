package com.codingbatch.positivenews.ui.bookmarkednewslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.databinding.FragmentBookmarkedNewsListBinding
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.ui.base.BaseFragment
import com.codingbatch.positivenews.ui.common.adapter.NewsListAdapter
import com.codingbatch.positivenews.ui.moreoptions.MoreOptionsBottomFragment
import com.codingbatch.positivenews.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_bookmarked_news_list.*

@AndroidEntryPoint
class BookmarkedNewsListFragment : BaseFragment(), NewsListAdapter.NewsClickListener {

    private val bookmarkedNewsListViewModel: BookmarkedNewsListViewModel by viewModels()
    private val newsListAdapter: NewsListAdapter = NewsListAdapter(
        this
    )

    override fun getLayoutId() = R.layout.fragment_bookmarked_news_list

    override fun setupDataBinding(view: View) {
        val binding = FragmentBookmarkedNewsListBinding.bind(view)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            binding.viewModel = bookmarkedNewsListViewModel
        }
    }

    override fun setupRecyclerView() {
        rvBookmarkedNewsList.apply {
            adapter = newsListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    override fun onNewsClicked(news: News) {
        val args = Bundle()
        args.putString(Constants.NEWS_URL, news.url)
        arguments = args
        navigateTo(R.id.action_newsListFragment_to_webFragment, args)
    }

    override fun onMoreOptionsClicked(news: News) {
        MoreOptionsBottomFragment.newInstance(news)
            .show(childFragmentManager, Constants.MORE_OPTIONS_TAG)
    }

}