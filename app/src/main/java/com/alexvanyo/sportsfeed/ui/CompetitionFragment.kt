package com.alexvanyo.sportsfeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alexvanyo.sportsfeed.R
import com.alexvanyo.sportsfeed.api.Competition
import com.alexvanyo.sportsfeed.databinding.CompetitionFragmentBinding
import com.alexvanyo.sportsfeed.viewmodel.FeedViewModel
import com.bumptech.glide.Glide
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.competition_fragment.*
import kotlinx.android.synthetic.main.competition_item.view.*
import javax.inject.Inject

/**
 * Fragment that displays the detail view for a specific competition
 */
class CompetitionFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: FeedViewModel

    private lateinit var binding: CompetitionFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.competition_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        model = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        model.selectedCompetition.observe(this, Observer {
            binding.competition = it
            Glide.with(this).load(it.competitors[1].team.logo).into(leftLogo)
            Glide.with(this).load(it.competitors[0].team.logo).into(rightLogo)
        })
    }
}