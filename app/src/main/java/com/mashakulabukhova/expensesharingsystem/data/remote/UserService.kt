package com.mashakulabukhova.expensesharingsystem.data.remote

import com.mashakulabukhova.expensesharingsystem.data.model.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("users/search")
    suspend fun getUsersByName(@Query("username") username: String): Response<List<UserDto>>

    @GET("users/id/{user_id}")
    suspend fun getUser(@Path("user_id") user_id: String): Response<UserDto>

    @GET("users/all")
    suspend fun getAllUsers(): Response<List<UserDto>>

    @GET("users/me")
    suspend fun getMyProfile(): Response<UserDto>
}