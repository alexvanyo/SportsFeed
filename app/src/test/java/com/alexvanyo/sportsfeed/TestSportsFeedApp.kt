package com.alexvanyo.sportsfeed

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class TestSportsFeedApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? =
        DaggerTestAppComponent.builder().create(this)
}