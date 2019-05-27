package com.alexvanyo.sportsfeed.dagger.viewmodel

import com.alexvanyo.sportsfeed.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * By providing a view model factory, view models can use constructor injection.
 * @see <a href="https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/di/ViewModelModule.kt">Based on (Apache license)</a>
 */
@Suppress("unused")
@Module(includes = [FeedViewModelModule::class])
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    abstract fun bindFeedViewModel(feedViewModel: FeedViewModelFactory): ViewModelFactory
}
