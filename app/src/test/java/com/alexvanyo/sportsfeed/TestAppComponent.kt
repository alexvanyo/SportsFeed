package com.alexvanyo.sportsfeed

import com.alexvanyo.sportsfeed.dagger.AppModule
import com.alexvanyo.sportsfeed.dagger.FragmentBuildersModule
import com.alexvanyo.sportsfeed.dagger.MainActivityModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        MainActivityModule::class,
        FragmentBuildersModule::class]
)
interface TestAppComponent : AndroidInjector<TestSportsFeedApp> {
    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<TestSportsFeedApp>()
}