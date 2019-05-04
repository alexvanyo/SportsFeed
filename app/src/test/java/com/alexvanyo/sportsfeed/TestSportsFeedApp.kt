package com.alexvanyo.sportsfeed

import com.alexvanyo.sportsfeed.api.ESPNService
import com.alexvanyo.sportsfeed.dagger.DaggerTestAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class TestSportsFeedApp : DaggerApplication() {
    @Inject
    lateinit var espnService: ESPNService

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? =
        DaggerTestAppComponent.builder().create(this)
}