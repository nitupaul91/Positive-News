package com.codingbatch.positivenews.ui.hotnewslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.databinding.FragmentHotNewsListBinding
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.ui.base.BaseFragment
import com.codingbatch.positivenews.ui.common.adapter.NewsListAdapter
import com.codingbatch.positivenews.ui.moreoptions.MoreOptionsBottomFragment
import com.codingbatch.positivenews.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_hot_news_list.*

@AndroidEntryPoint
class HotNewsListFragment : BaseFragment(), NewsListAdapter.NewsClickListener,
    NewsListAdapter.NewsScrollListener {

    private val hotNewsListViewModel: HotNewsListViewModel by viewModels(this::requireActivity)

    private val newsListAdapter: NewsListAdapter = NewsListAdapter(
        this,
        this
    ).apply {
        setHasStableIds(true)
    }

    override fun getLayoutId() = R.layout.fragment_hot_news_list

    override fun setupDataBinding(view: View) {
        val binding = FragmentHotNewsListBinding.bind(view)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            binding.viewModel = hotNewsListViewModel
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hotNewsListViewModel.searchText.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty())
                hotNewsListViewModel.searchNews()
        })
    }

    override fun setupRecyclerView() {
        rvHotNewsList.apply {
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

    override fun fetchMoreNews(after: String) {
        hotNewsListViewModel.getHotNews(after)
    }

}