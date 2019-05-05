package com.alexvanyo.sportsfeed.viewmodel

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.alexvanyo.sportsfeed.api.ESPNService
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Main view model for the feed.
 */
class FeedViewModel @Inject constructor(private val espnService: ESPNService) : ViewModel() {
    private val mlbDataObservable = Observable.interval(0, 10, TimeUnit.SECONDS)
        .flatMap { espnService.getMLBGames() }
        .subscribeOn(Schedulers.io()).toFlowable(BackpressureStrategy.LATEST)

    val mlbData = LiveDataReactiveStreams.fromPublisher(mlbDataObservable)
}