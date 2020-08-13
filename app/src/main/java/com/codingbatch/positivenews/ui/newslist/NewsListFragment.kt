package com.codingbatch.positivenews.ui.newslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.databinding.FragmentNewsListBinding
import com.codingbatch.positivenews.model.News
import com.codingbatch.positivenews.ui.moreoptions.MoreOptionsBottomFragment
import com.codingbatch.positivenews.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news_list.*

@AndroidEntryPoint
class NewsListFragment : Fragment(), NewsListAdapter.NewsClickListener {

    private val newsListViewModel: NewsListViewModel by viewModels()

    lateinit var newsListAdapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)

        newsListAdapter = NewsListAdapter(this)

        setupDataBinding(view)
        return view
    }

    private fun setupDataBinding(view: View) {
        val binding = FragmentNewsListBinding.bind(view)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            binding.viewModel = newsListViewModel

            setupRecyclerView(rvNewsList)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            adapter = newsListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        toolbar.apply {
            title = ""
            setNavigationIcon(R.drawable.ic_search)
        }
    }


    override fun onNewsClicked(news: News) {
        navigateToWebFragment(news)
    }

    override fun onMoreOptionsClicked(news: News) {
        MoreOptionsBottomFragment.newInstance(news.isBookmarked).show(childFragmentManager,"mob")
    }

    private fun navigateToWebFragment(news: News) {
        val args = Bundle()
        args.putString(Constants.NEWS_URL, news.url)
        arguments = args
        Navigation.findNavController(requireView())
            .navigate(R.id.action_newsListFragment_to_webFragment, args)
    }
}