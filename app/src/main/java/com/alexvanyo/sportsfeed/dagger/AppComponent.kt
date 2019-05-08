package com.alexvanyo.sportsfeed.dagger

import android.content.Context
import com.alexvanyo.sportsfeed.SportsFeedApp
import dagger.Component
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        FragmentBuildersModule::class,
        MainActivityModule::class]
)
interface AppComponent : AndroidInjector<SportsFeedApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<SportsFeedApp>()
}