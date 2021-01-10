package com.target.targetcasestudy.di.module

import com.target.targetcasestudy.BuildConfig.DEALS_API_URL
import com.target.targetcasestudy.data.remote.api.DealsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * class provides the deals service
 */
@InstallIn(ApplicationComponent::class)
@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideDealsService(): DealsService {
        return Retrofit.Builder().baseUrl(DEALS_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
            .create(DealsService::class.java)
    }
}