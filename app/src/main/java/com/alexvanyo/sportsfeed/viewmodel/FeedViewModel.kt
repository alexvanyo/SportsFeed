package com.alexvanyo.sportsfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexvanyo.sportsfeed.api.ESPNService
import com.alexvanyo.sportsfeed.api.ScoreboardData
import com.alexvanyo.sportsfeed.util.PausableInterval
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
            .flatMap { espnService.getMLBGames() }
            .subscribeOn(Schedulers.io())
            // TODO: Add error handler
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
            pausableInterval.pause()
        }

        override fun onActive() {
            super.onActive()
            pausableInterval.resume()
        }
    }

    val mlbData: LiveData<ScoreboardData>
        get() = _mlbData

}