package com.alexvanyo.sportsfeed.dagger

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import org.mockito.Mockito
import javax.inject.Singleton

@Suppress("unused")
@Module
class TestViewModelModule {
    @Singleton
    @Provides
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return Mockito.mock(ViewModelProvider.Factory::class.java)
    }
}