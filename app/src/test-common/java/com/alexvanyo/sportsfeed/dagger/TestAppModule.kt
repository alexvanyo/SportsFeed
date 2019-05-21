package com.alexvanyo.sportsfeed.dagger

import com.alexvanyo.sportsfeed.api.EspnService
import dagger.Module
import dagger.Provides
import org.mockito.Mockito
import javax.inject.Singleton

@Module(includes = [TestViewModelModule::class])
class TestAppModule {
    @Singleton
    @Provides
    fun provideSportsService(): EspnService {
        return Mockito.mock(EspnService::class.java)
    }
}