package com.codingbatch.positivenews.ui.newslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.databinding.FragmentNewsListBinding
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.ui.base.BaseFragment
import com.codingbatch.positivenews.ui.moreoptions.MoreOptionsBottomFragment
import com.codingbatch.positivenews.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news_list.*

@AndroidEntryPoint
class NewsListFragment : BaseFragment(), NewsListAdapter.NewsClickListener,
    NewsListAdapter.NewsScrollListener {

    private val newsListViewModel: NewsListViewModel by viewModels()

    private lateinit var newsListAdapter: NewsListAdapter

    override fun getLayoutId() = R.layout.fragment_news_list

    override fun setupDataBinding(view: View) {
        val binding = FragmentNewsListBinding.bind(view)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            binding.viewModel = newsListViewModel
        }
    }

    private fun setupRecyclerView() {
        rvNewsList.apply {
            adapter?.setHasStableIds(true)
            adapter = newsListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsListAdapter = NewsListAdapter(this, this)
        setupRecyclerView()

        newsListViewModel.searchText.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty())
                newsListViewModel.searchNews()
        })
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

    override fun fetchMoreNews(after: String) {
        newsListViewModel.getTopNews(after)
    }

}