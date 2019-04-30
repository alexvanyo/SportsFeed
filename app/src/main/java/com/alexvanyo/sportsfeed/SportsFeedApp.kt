package com.alexvanyo.sportsfeed

import com.alexvanyo.sportsfeed.dagger.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class SportsFeedApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? =
        DaggerAppComponent.builder().create(this)
}