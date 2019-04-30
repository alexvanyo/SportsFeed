package com.alexvanyo.sportsfeed.dagger

import com.alexvanyo.sportsfeed.SportsFeedApp
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class]
)
interface AppComponent : AndroidInjector<SportsFeedApp> {
    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<SportsFeedApp>()
}