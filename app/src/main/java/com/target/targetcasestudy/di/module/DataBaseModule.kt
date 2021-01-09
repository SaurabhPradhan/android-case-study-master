package com.target.targetcasestudy.di.module

import android.app.Application
import com.target.targetcasestudy.data.local.DealsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DataBaseModule {

    @Singleton
    @Provides
    fun provideDataBase(application: Application) = DealsDataBase.getInstance(application)

    @Singleton
    @Provides
    fun provideDealsDao(dataBase: DealsDataBase) = dataBase.dealsDao()
}