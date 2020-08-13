package com.codingbatch.positivenews.ui.moreoptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.databinding.BottomFragmentMoreOptionsBinding
import com.codingbatch.positivenews.databinding.FragmentNewsListBinding
import com.codingbatch.positivenews.ui.newslist.NewsListViewModel
import com.codingbatch.positivenews.util.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreOptionsBottomFragment : BottomSheetDialogFragment() {

    private val moreOptionsViewModel: MoreOptionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_fragment_more_options, container, false)
        setupDataBinding(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isNewsBookmarked = requireArguments().getBoolean(Constants.NEWS_BOOKMARK, false)
        moreOptionsViewModel.setBookmarkStatus(isNewsBookmarked)
    }


    companion object {
        fun newInstance(isNewsBookmarked: Boolean): MoreOptionsBottomFragment {
            val fragment = MoreOptionsBottomFragment()
            val args = Bundle()
            args.putBoolean(Constants.NEWS_BOOKMARK, isNewsBookmarked)
            fragment.arguments = args
            return fragment
        }
    }

    private fun setupDataBinding(view: View) {
        val binding = BottomFragmentMoreOptionsBinding.bind(view)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            binding.viewModel = moreOptionsViewModel
        }
    }
}