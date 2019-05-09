package com.alexvanyo.sportsfeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.alexvanyo.sportsfeed.R
import com.alexvanyo.sportsfeed.viewmodel.FeedViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.feed_fragment.*
import javax.inject.Inject

/**
 * Fragment that contains the main competition feed, a list of competitions.
 */
class FeedFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: FeedViewModel

    private val competitionAdapter = CompetitionAdapter(this, ::competitionClickListener)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        model = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        model.competitions.observe(this, Observer { competitionAdapter.sortedList.addAll(it) })

        this.recyclerView.apply {
            setHasFixedSize(true)
            adapter = competitionAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun competitionClickListener(uid: String) {
        model.selectCompetition(uid)

        findNavController().navigate(FeedFragmentDirections.actionFeedFragmentToCompetitionFragment())
    }
}