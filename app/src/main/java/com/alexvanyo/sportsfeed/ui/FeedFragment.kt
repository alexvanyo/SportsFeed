package com.alexvanyo.sportsfeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexvanyo.sportsfeed.api.ESPNService
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feed_fragment.*
import javax.inject.Inject
import androidx.recyclerview.widget.DividerItemDecoration
import com.alexvanyo.sportsfeed.R
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

/**
 * Fragment that contains the main competition feed, a list of competitions.
 */
class FeedFragment : DaggerFragment() {

    @Inject
    lateinit var espnService: ESPNService

    private val competitionAdapter = CompetitionAdapter(this)

    private val compositeDisposable = CompositeDisposable()

    override fun onResume() {
        super.onResume()

        compositeDisposable
            .add(Observable.interval(0,1, TimeUnit.MINUTES)
                .flatMap { espnService.getMLBGames() }
                .subscribeOn(Schedulers.io())
                .subscribe {
                    competitionAdapter.sortedList.replaceAll(it.events.flatMap { event -> event.competitions })
                })
    }

    override fun onPause() {
        super.onPause()

        compositeDisposable.clear()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.recyclerView.apply {
            setHasFixedSize(true)
            adapter = competitionAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}