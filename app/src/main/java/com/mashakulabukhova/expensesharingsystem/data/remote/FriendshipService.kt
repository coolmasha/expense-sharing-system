package com.mashakulabukhova.expensesharingsystem.data.remote

import com.mashakulabukhova.expensesharingsystem.data.remote.model.request.FriendRequest
import com.mashakulabukhova.expensesharingsystem.data.remote.model.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface FriendshipService {

    @GET("friend-requests/incoming")
    suspend fun getIncomingFriendRequests(): Response<List<UserDto>>

    @GET("friend-requests/outgoing")
    suspend fun getOutgoingFriendRequests(): Response<List<UserDto>>

    @POST("friend-requests/")
    suspend fun sendFriendRequest(@Body body: FriendRequest): Response<UserDto>

    @PATCH("friend-requests/{id}")
    suspend fun acceptFriendRequest(@Path("id") id: String): Response<UserDto>

    @DELETE("friends/{id}")
    suspend fun rejectFriendRequest(@Path("id") id: String): Response<Unit>

    @DELETE("friends/{id}")
    suspend fun cancelFriendRequest(@Path("id") id: String): Response<Unit>

}