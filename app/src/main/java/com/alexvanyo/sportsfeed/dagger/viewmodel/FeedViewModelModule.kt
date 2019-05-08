package com.alexvanyo.sportsfeed.dagger.viewmodel

import com.alexvanyo.sportsfeed.R
import com.alexvanyo.sportsfeed.SportsFeedApp
import com.alexvanyo.sportsfeed.util.PausableInterval
import com.alexvanyo.sportsfeed.util.PausableObservable
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit

@Module
class FeedViewModelModule {
    @Provides
    fun providePausableObservable(sportsFeedApp: SportsFeedApp): PausableObservable<*> {
        return PausableInterval(
            { System.currentTimeMillis() },
            sportsFeedApp.resources.getInteger(R.integer.pollingPeriodSeconds).toLong(),
            TimeUnit.SECONDS
        )
    }
}