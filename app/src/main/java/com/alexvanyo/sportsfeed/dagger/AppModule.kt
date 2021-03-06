package com.alexvanyo.sportsfeed.dagger

import com.alexvanyo.sportsfeed.BuildConfig
import com.alexvanyo.sportsfeed.api.EspnService
import com.alexvanyo.sportsfeed.dagger.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideSportsService(
        httpClient: OkHttpClient
    ): EspnService {
        return Retrofit.Builder()
            .baseUrl("https://site.api.espn.com/apis/site/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(EspnService::class.java)
    }
}