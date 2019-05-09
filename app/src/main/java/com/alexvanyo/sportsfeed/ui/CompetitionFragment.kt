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
import com.alexvanyo.sportsfeed.api.baseball.BaseballCompetition
import com.alexvanyo.sportsfeed.databinding.BaseballBoxScoreTextViewBinding
import com.alexvanyo.sportsfeed.databinding.CompetitionFragmentBinding
import com.alexvanyo.sportsfeed.viewmodel.FeedViewModel
import com.bumptech.glide.Glide
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.baseball_box_score.*
import kotlinx.android.synthetic.main.competition_fragment.*
import javax.inject.Inject

/**
 * Fragment that displays the detail view for a specific competition
 */
class CompetitionFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: FeedViewModel

    private lateinit var binding: CompetitionFragmentBinding

    private val statisticAdapter = StatisticAdapter()
    private val headlineAdapter = HeadlineAdapter()

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

        headlinesRecyclerView.apply {
            adapter = headlineAdapter
        }
        statisticsRecyclerView.apply {
            adapter = statisticAdapter
        }

        model.selectedCompetition.observe(this, Observer {
            binding.competition = it
            Glide.with(this).load(it.getLeftTeam().team.logo).into(leftLogo)
            Glide.with(this).load(it.getRightTeam().team.logo).into(rightLogo)

            headlineAdapter.submitList(it.headlines.orEmpty())
            statisticAdapter.submitList(it.getPairedStatistics())

            if (it is BaseballCompetition) {

                baseballBoxScoreStub?.inflate()

                // Update the box score
                inningRow.removeAllViews()
                awayTeamRow.removeAllViews()
                homeTeamRow.removeAllViews()

                for (inning in it.getBoxScore()) {
                    BaseballBoxScoreTextViewBinding.inflate(layoutInflater, inningRow, true).value = inning.inningLabel
                    BaseballBoxScoreTextViewBinding.inflate(layoutInflater, awayTeamRow, true).value =
                        inning.awayTeamRuns
                    BaseballBoxScoreTextViewBinding.inflate(layoutInflater, homeTeamRow, true).value =
                        inning.homeTeamRuns
                }
            }
        })
    }
}