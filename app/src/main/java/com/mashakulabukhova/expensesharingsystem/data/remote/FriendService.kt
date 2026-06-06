package com.mashakulabukhova.expensesharingsystem.data.remote

import com.mashakulabukhova.expensesharingsystem.data.remote.model.UserDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface FriendService {

//    @GET("users/all")
//    suspend fun getAllFriends(@Path("user_id") user_id: String): Response<List<UserDto>>
    @GET("friends/all")
    suspend fun getAllFriends(): Response<List<UserDto>>

    @GET("users/id/{user_id}")
    suspend fun getFriend(@Path("user_id") user_id: String): Response<UserDto>

    @DELETE("friends/{id}")
    suspend fun deleteFriend(@Path("id") id: String): Response<Unit>
}