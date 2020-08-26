package com.codingbatch.positivenews.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

abstract class BaseFragment : Fragment() {

    abstract fun setupDataBinding(view: View)
    abstract fun getLayoutId(): Int

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
}