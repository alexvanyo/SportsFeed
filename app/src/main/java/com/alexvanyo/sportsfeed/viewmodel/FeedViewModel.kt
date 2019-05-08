package com.alexvanyo.sportsfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexvanyo.sportsfeed.api.Competition
import com.alexvanyo.sportsfeed.api.ESPNService
import com.alexvanyo.sportsfeed.api.ScoreboardData
import com.alexvanyo.sportsfeed.util.PausableInterval
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Main view model for the feed.
 */
class FeedViewModel @Inject constructor(private val espnService: ESPNService) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val pausableInterval = PausableInterval({ System.currentTimeMillis() }, 10, TimeUnit.SECONDS)

    init {
        compositeDisposable.add(pausableInterval.observable
            .flatMap { espnService.getMLBGames().onErrorResumeNext(Observable.empty()) }
            .subscribeOn(Schedulers.io())
            .subscribe(::handleScoreboardData))
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }

    private fun handleScoreboardData(scoreboardData: ScoreboardData) {

        val competitionList = scoreboardData.events.flatMap { it.competitions }

        _competitions.postValue(competitionList)
    }

    // Override inactive/active callbacks to pause/resume network polling
    private val _competitions: MutableLiveData<List<Competition>> = object: MutableLiveData<List<Competition>>() {
        override fun onInactive() {
            super.onInactive()
            pausableInterval.pause()
        }

        override fun onActive() {
            super.onActive()
            pausableInterval.resume()
        }
    }

    val competitions: LiveData<List<Competition>>
        get() = _competitions
}