package com.mashakulabukhova.expensesharingsystem.data.remote

import com.mashakulabukhova.expensesharingsystem.data.remote.model.LoginRequest
import javax.inject.Inject

class AuthenticationRemoteDataSource @Inject constructor(
    private val authenticationService: AuthenticationService
) {
    suspend fun login(body: LoginRequest) = authenticationService.login(body = body)
}