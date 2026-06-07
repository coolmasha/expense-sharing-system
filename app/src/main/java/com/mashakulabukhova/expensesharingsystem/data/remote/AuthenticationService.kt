package com.mashakulabukhova.expensesharingsystem.data.remote

import com.mashakulabukhova.expensesharingsystem.data.model.request.LoginRequest
import com.mashakulabukhova.expensesharingsystem.data.model.request.RegistrationRequest
import com.mashakulabukhova.expensesharingsystem.data.model.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {

    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): Response<UserDto>

    @POST("auth/register")
    suspend fun registration(@Body body: RegistrationRequest): Response<UserDto>
}