package com.alexvanyo.sportsfeed.dagger

import com.alexvanyo.sportsfeed.BuildConfig
import com.alexvanyo.sportsfeed.api.ESPNService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideSportsService(
        httpClient: OkHttpClient
    ): ESPNService {
        return Retrofit.Builder()
            .baseUrl("http://site.api.espn.com/apis/site/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(com.alexvanyo.sportsfeed.api.ESPNService::class.java)
    }
}