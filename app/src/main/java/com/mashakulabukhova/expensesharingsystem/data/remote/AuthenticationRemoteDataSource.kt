package com.mashakulabukhova.expensesharingsystem.data.remote

import com.mashakulabukhova.expensesharingsystem.data.remote.model.request.LoginRequest
import com.mashakulabukhova.expensesharingsystem.data.remote.model.request.RegistrationRequest
import javax.inject.Inject

class AuthenticationRemoteDataSource @Inject constructor(
    private val authenticationService: AuthenticationService
) {
    suspend fun login(body: LoginRequest) = authenticationService.login(body = body)

    suspend fun registration(body: RegistrationRequest) = authenticationService.registration(body = body)
}