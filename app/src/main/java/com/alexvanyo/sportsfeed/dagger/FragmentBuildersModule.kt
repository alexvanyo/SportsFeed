package com.alexvanyo.sportsfeed.dagger

import com.alexvanyo.sportsfeed.FeedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeFeedFragment(): FeedFragment
}
