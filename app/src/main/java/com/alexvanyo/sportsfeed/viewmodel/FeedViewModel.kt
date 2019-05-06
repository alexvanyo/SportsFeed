package com.alexvanyo.sportsfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexvanyo.sportsfeed.api.ESPNService
import com.alexvanyo.sportsfeed.api.ScoreboardData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

/**
 * Main view model for the feed.
 */
class FeedViewModel @Inject constructor(private val espnService: ESPNService) : ViewModel() {

    private val shouldPoll = AtomicBoolean()

    private val compositeDisposable = CompositeDisposable()

    init {
        shouldPoll.set(true)

        compositeDisposable.add(Observable.interval(0, 10, TimeUnit.SECONDS)
            .filter { shouldPoll.get() }
            .flatMap { espnService.getMLBGames() }
            .subscribeOn(Schedulers.io())
            .subscribe { _mlbData.postValue(it) })
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }

    // Override inactive/active callbacks to pause/resume network polling
    private val _mlbData = object: MutableLiveData<ScoreboardData>() {
        override fun onInactive() {
            super.onInactive()

            shouldPoll.set(false)
        }

        override fun onActive() {
            super.onActive()

            shouldPoll.set(true)
        }

    }

    val mlbData: LiveData<ScoreboardData>
        get() = _mlbData

}