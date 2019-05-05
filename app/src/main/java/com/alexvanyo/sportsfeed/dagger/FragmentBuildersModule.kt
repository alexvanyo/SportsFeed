package com.alexvanyo.sportsfeed.dagger

import com.alexvanyo.sportsfeed.ui.CompetitionFragment
import com.alexvanyo.sportsfeed.ui.FeedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeFeedFragment(): FeedFragment

    @ContributesAndroidInjector
    abstract fun contributeCompetitionFragment(): CompetitionFragment
}
