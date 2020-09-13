package com.codingbatch.positivenews.ui

import android.view.MenuItem
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.codingbatch.positivenews.R
import com.codingbatch.positivenews.ui.base.BaseViewModel
import com.codingbatch.positivenews.util.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivityViewModel @ViewModelInject constructor(
) : BaseViewModel(), BottomNavigationView.OnNavigationItemSelectedListener {

    val destinationId = MutableLiveData<Int>()

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.hotNewsListFragment -> {
                changeDestinationId(R.id.hotNewsListFragment)
                true
            }
            R.id.bookmarkedNewsListFragment -> {
                changeDestinationId(R.id.bookmarkedNewsListFragment)
                true
            }
            R.id.blockedSourcesFragment -> {
                changeDestinationId(R.id.blockedSourcesFragment)
                true
            }
            else -> {
                throw IllegalArgumentException(Constants.DESTINATION_NOT_FOUND)
            }
        }
    }

    private fun changeDestinationId(destinationId: Int) {
        this.destinationId.value = destinationId
    }

}