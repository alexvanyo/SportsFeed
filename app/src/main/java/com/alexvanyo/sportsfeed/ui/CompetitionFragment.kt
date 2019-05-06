package com.alexvanyo.sportsfeed.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alexvanyo.sportsfeed.viewmodel.FeedViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Fragment that displays the detail view for a specific competition
 */
class CompetitionFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: FeedViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        model = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }
}