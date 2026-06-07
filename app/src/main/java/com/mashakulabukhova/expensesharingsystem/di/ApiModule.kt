package com.mashakulabukhova.expensesharingsystem.di

import com.mashakulabukhova.expensesharingsystem.data.remote.AuthenticationService
import com.mashakulabukhova.expensesharingsystem.data.remote.EventService
import com.mashakulabukhova.expensesharingsystem.data.remote.ExpenseService
import com.mashakulabukhova.expensesharingsystem.data.remote.FriendService
import com.mashakulabukhova.expensesharingsystem.data.remote.FriendshipService
import com.mashakulabukhova.expensesharingsystem.data.remote.UserService
import com.mashakulabukhova.expensesharingsystem.data.repository.AuthenticationRepositoryImpl
import com.mashakulabukhova.expensesharingsystem.data.repository.EventRepositoryImpl
import com.mashakulabukhova.expensesharingsystem.data.repository.ExpenseRepositoryImpl
import com.mashakulabukhova.expensesharingsystem.data.repository.FriendRepositoryImpl
import com.mashakulabukhova.expensesharingsystem.data.repository.FriendshipRepositoryImpl
import com.mashakulabukhova.expensesharingsystem.data.repository.UserRepositoryImpl
import com.mashakulabukhova.expensesharingsystem.domain.repository.AuthenticationRepository
import com.mashakulabukhova.expensesharingsystem.domain.repository.EventRepository
import com.mashakulabukhova.expensesharingsystem.domain.repository.ExpenseRepository
import com.mashakulabukhova.expensesharingsystem.domain.repository.FriendRepository
import com.mashakulabukhova.expensesharingsystem.domain.repository.FriendshipRepository
import com.mashakulabukhova.expensesharingsystem.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

//    private const val BASE_URL = "http://172.20.10.5:3000/"
    private const val BASE_URL1 = "http://90.188.118.51:8000/api/"
    private const val BASE_URL2 = "http://90.188.118.51:8080/api/"
    private const val BASE_URL3 = "http://90.188.118.51:8081/api/"

    fun getBaseUrl1(): String = BASE_URL1
    fun getBaseUrl2(): String = BASE_URL2
    fun getBaseUrl3(): String = BASE_URL3

    @Provides
    @Singleton
    fun providesAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    @Singleton
    @Named("retrofit1")
    fun providesRetrofit1(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL1)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    // Retrofit для второго сервера
    @Provides
    @Singleton
    @Named("retrofit2")
    fun providesRetrofit2(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL2)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    @Named("retrofit3")
    fun providesRetrofit3(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL3)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

//    @Provides
//    @Singleton
//    fun providesRetrofit(okHttpClient: OkHttpClient) =
//        Retrofit.Builder()
//            .baseUrl(BASE_URL1)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()


    @Provides
    @Singleton
    fun providesAuthenticationService(@Named("retrofit1") retrofit1: Retrofit) =
        retrofit1.create(AuthenticationService::class.java)

    @Provides
    @Singleton
    fun providesAuthenticationRepository(
        impl: AuthenticationRepositoryImpl
    ): AuthenticationRepository = impl

    @Provides
    @Singleton
    fun providesEventService(@Named("retrofit3") retrofit3: Retrofit) = retrofit3.create(EventService::class.java)

    @Provides
    @Singleton
    fun providesEventRepository(
        impl: EventRepositoryImpl
    ): EventRepository = impl

    @Provides
    @Singleton
    fun providesExpenseService(@Named("retrofit3") retrofit3: Retrofit) = retrofit3.create(ExpenseService::class.java)

    @Provides
    @Singleton
    fun providesExpenseRepository(
        impl: ExpenseRepositoryImpl
    ): ExpenseRepository = impl

    @Provides
    @Singleton
    fun providesUserService(@Named("retrofit2") retrofit2: Retrofit) = retrofit2.create(UserService::class.java)

    @Provides
    @Singleton
    fun providesUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository = impl

    @Provides
    @Singleton
    fun providesFriendService(@Named("retrofit2") retrofit2: Retrofit) = retrofit2.create(FriendService::class.java)

    @Provides
    @Singleton
    fun providesFriendRepository(
        impl: FriendRepositoryImpl
    ): FriendRepository = impl

    @Provides
    @Singleton
    fun providesFriendshipService(@Named("retrofit2") retrofit2: Retrofit) = retrofit2.create(FriendshipService::class.java)

    @Provides
    @Singleton
    fun providesFriendshipRepository(
        impl: FriendshipRepositoryImpl
    ): FriendshipRepository = impl

}
