package com.codingbatch.positivenews.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingbatch.positivenews.ui.common.adapter.NewsListAdapter
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {

    abstract fun getLayoutId(): Int
    abstract fun setupDataBinding(view: View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        setupDataBinding(view)
        return view
    }

    fun navigateTo(destinationId: Int, args: Bundle) {
        Navigation.findNavController(requireView())
            .navigate(destinationId, args)
    }

    fun showSnackbar(view: View, messageId: Int) {
        Snackbar.make(view, messageId, Snackbar.LENGTH_LONG)
            .show()
    }

    fun setupRecyclerView(
        recyclerView: RecyclerView,
        adapter: NewsListAdapter
    ) {
        recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        }
    }
}
