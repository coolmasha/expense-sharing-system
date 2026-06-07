package com.mashakulabukhova.expensesharingsystem.domain.usecase.authentication

import com.mashakulabukhova.expensesharingsystem.data.model.request.RegistrationRequest
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.AuthenticationRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend operator fun invoke(body: RegistrationRequest): NetworkResult<User> {
        return authenticationRepository.registration(body)
    }
}