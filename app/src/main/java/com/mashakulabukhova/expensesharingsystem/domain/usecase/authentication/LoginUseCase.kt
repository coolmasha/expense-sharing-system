package com.mashakulabukhova.expensesharingsystem.domain.usecase.authentication

import com.mashakulabukhova.expensesharingsystem.data.remote.model.LoginRequest
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.AuthenticationRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend operator fun invoke(body: LoginRequest): NetworkResult<User> {
        return authenticationRepository.login(body)
    }
}