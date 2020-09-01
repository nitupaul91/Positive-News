package com.codingbatch.positivenews.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.codingbatch.positivenews.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.destinationId.observe(this, Observer { destinationId ->
            navigateTo(destinationId)
        })
    }

    private fun navigateTo(destinationId: Int, args: Bundle? = null) {
        Navigation.findNavController(rootLayout)
            .navigate(destinationId, args)
    }
}
