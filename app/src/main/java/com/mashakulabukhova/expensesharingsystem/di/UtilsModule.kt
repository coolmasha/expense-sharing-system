package com.mashakulabukhova.expensesharingsystem.di

import com.mashakulabukhova.expensesharingsystem.utils.BaseApiResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {
    @Provides
    @Singleton
    fun providesBaseApiResponse(): BaseApiResponse = BaseApiResponse()
}