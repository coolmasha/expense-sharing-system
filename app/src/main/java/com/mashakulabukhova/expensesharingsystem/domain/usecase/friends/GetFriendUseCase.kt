package com.mashakulabukhova.expensesharingsystem.domain.usecase.friends

import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.FriendRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

private val user = User(id = "3", username = "Полина", email = "john@gmail.com")

class GetFriendUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {

    suspend operator fun invoke(userId: String): NetworkResult<User> {
        return friendRepository.getFriend(userId)

//        return NetworkResult.Success(result = user)
//        return NetworkResult.Success(result = emptyList())
    }
}