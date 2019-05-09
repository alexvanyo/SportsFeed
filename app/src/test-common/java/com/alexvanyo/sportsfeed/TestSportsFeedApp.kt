package com.alexvanyo.sportsfeed

import androidx.lifecycle.ViewModelProvider
import com.alexvanyo.sportsfeed.dagger.DaggerTestAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class TestSportsFeedApp : DaggerApplication() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? =
        DaggerTestAppComponent.builder().create(this)
}