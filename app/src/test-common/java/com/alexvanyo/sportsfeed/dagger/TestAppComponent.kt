package com.alexvanyo.sportsfeed.dagger

import com.alexvanyo.sportsfeed.TestSportsFeedApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        FragmentBuildersModule::class,
        MainActivityModule::class,
        TestAppModule::class]
)
interface TestAppComponent : AndroidInjector<TestSportsFeedApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TestSportsFeedApp>()
}