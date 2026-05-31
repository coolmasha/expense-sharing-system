package com.mashakulabukhova.expensesharingsystem.di

import com.mashakulabukhova.expensesharingsystem.data.remote.AuthenticationService
import com.mashakulabukhova.expensesharingsystem.data.remote.EventService
import com.mashakulabukhova.expensesharingsystem.data.repository.AuthenticationRepositoryImpl
import com.mashakulabukhova.expensesharingsystem.data.repository.EventRepositoryImpl
import com.mashakulabukhova.expensesharingsystem.domain.repository.AuthenticationRepository
import com.mashakulabukhova.expensesharingsystem.domain.repository.EventRepository
import com.mashakulabukhova.expensesharingsystem.utils.BaseApiResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "http://172.20.10.5:3000/"

    fun getBaseUrl(): String = BASE_URL

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun providesAuthenticationService(retrofit: Retrofit) = retrofit.create(AuthenticationService::class.java)

    @Provides
    @Singleton
    fun providesAuthenticationRepository(
        impl: AuthenticationRepositoryImpl
    ): AuthenticationRepository = impl

    @Provides
    @Singleton
    fun providesEventService(retrofit: Retrofit) = retrofit.create(EventService::class.java)

    @Provides
    @Singleton
    fun providesEventRepository(
        impl: EventRepositoryImpl
    ): EventRepository = impl

}

//json-server --watch db.json
