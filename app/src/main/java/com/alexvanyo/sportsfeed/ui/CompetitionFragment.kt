package com.alexvanyo.sportsfeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alexvanyo.sportsfeed.R
import com.alexvanyo.sportsfeed.api.BaseballCompetition
import com.alexvanyo.sportsfeed.databinding.BaseballBoxScoreTextViewBinding
import com.alexvanyo.sportsfeed.databinding.CompetitionFragmentBinding
import com.alexvanyo.sportsfeed.viewmodel.FeedViewModel
import com.alexvanyo.sportsfeed.viewmodel.InjectableSavedStateVMFactoryFactory
import com.bumptech.glide.Glide
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.baseball_box_score.*
import kotlinx.android.synthetic.main.competition_fragment.*
import javax.inject.Inject

/**
 * Fragment that displays the detail view for a specific competition
 */
class CompetitionFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactoryFactory: InjectableSavedStateVMFactoryFactory

    private lateinit var model: FeedViewModel

    private lateinit var binding: CompetitionFragmentBinding

    private val compositeDisposable = CompositeDisposable()

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
        model = activity!!.run {
            ViewModelProviders.of(this, viewModelFactoryFactory.create(this, null)).get(FeedViewModel::class.java)
        }

        headlinesRecyclerView.apply {
            adapter = headlineAdapter
        }
        statisticsRecyclerView.apply {
            adapter = statisticAdapter
        }

        model.selectedCompetition.observe(this, Observer {
            binding.competition = it
            Glide.with(this).load(it.getFirstTeam().team.logo).into(firstLogo)
            Glide.with(this).load(it.getSecondTeam().team.logo).into(secondLogo)

            headlineAdapter.submitList(it.headlines.orEmpty())
            statisticAdapter.submitList(it.getPairedStatistics())

            when (it) {
                is BaseballCompetition -> {
                    baseballBoxScoreStub?.inflate()

                    // Update the box score
                    inningRow.removeAllViews()
                    awayTeamRow.removeAllViews()
                    homeTeamRow.removeAllViews()

                    for (inning in it.getBoxScore()) {
                        BaseballBoxScoreTextViewBinding.inflate(layoutInflater, inningRow, true).value =
                            inning.inningLabel
                        BaseballBoxScoreTextViewBinding.inflate(layoutInflater, awayTeamRow, true).value =
                            inning.awayTeamRuns
                        BaseballBoxScoreTextViewBinding.inflate(layoutInflater, homeTeamRow, true).value =
                            inning.homeTeamRuns
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        compositeDisposable.add(model.dataFetchErrorObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            Toast.makeText(activity, R.string.data_fetch_error, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onPause() {
        super.onPause()

        compositeDisposable.clear()
    }
}