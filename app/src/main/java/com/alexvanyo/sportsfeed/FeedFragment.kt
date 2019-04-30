package com.alexvanyo.sportsfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexvanyo.sportsfeed.api.ESPNService
import com.alexvanyo.sportsfeed.api.ScoreboardData
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feed_fragment.*
import javax.inject.Inject

class FeedFragment : DaggerFragment() {

    @Inject
    lateinit var espnService: ESPNService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textAdapter = TextAdapter()

        this.recycler_view.apply {
            setHasFixedSize(true)
            adapter = textAdapter
        }

        val gamesObservable: Observable<ScoreboardData> = espnService.getMLBGames()
        gamesObservable
            .subscribeOn(Schedulers.io())
            .subscribe {
                textAdapter.sortedList.addAll(it.events.map { event -> event.name })
            }
    }
}