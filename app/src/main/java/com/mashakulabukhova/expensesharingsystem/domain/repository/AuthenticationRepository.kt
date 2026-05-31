package com.mashakulabukhova.expensesharingsystem.domain.repository

import com.mashakulabukhova.expensesharingsystem.data.remote.model.LoginRequest
import com.mashakulabukhova.expensesharingsystem.data.remote.model.RegistrationRequest
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult

interface AuthenticationRepository {

    suspend fun login(body: LoginRequest): NetworkResult<User>
    suspend fun registration(body: RegistrationRequest): NetworkResult<User>
}