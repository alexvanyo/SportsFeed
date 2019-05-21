package com.alexvanyo.sportsfeed.dagger

import com.alexvanyo.sportsfeed.ui.CompetitionFragment
import com.alexvanyo.sportsfeed.ui.FeedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module that provides the fragments.
 * @see <a href="https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/di/FragmentBuildersModule.kt">Based on (Apache license)</a>
 */
@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeFeedFragment(): FeedFragment

    @ContributesAndroidInjector
    abstract fun contributeCompetitionFragment(): CompetitionFragment
}
