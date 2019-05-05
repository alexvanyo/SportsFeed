package com.alexvanyo.sportsfeed.dagger

import com.alexvanyo.sportsfeed.api.ESPNService
import dagger.Module
import dagger.Provides
import org.mockito.Mockito
import javax.inject.Singleton

@Module(includes = [TestViewModelModule::class])
class TestAppModule {
    @Singleton
    @Provides
    fun provideSportsService(): ESPNService {
        return Mockito.mock(ESPNService::class.java)
    }
}