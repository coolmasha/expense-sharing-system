package com.mashakulabukhova.expensesharingsystem.domain.usecase.users

import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.UserRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject


class GetMyProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): NetworkResult<User> {
        return userRepository.getMyProfile()
    }
}