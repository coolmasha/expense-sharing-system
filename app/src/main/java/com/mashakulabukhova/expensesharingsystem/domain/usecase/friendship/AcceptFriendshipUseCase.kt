package com.mashakulabukhova.expensesharingsystem.domain.usecase.friendship

import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.FriendshipRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

private val user = User(id = "3", username = "Полина", email = "john@gmail.com")

class AcceptFriendshipUseCase @Inject constructor(
    private val friendshipRepository: FriendshipRepository
) {
    suspend operator fun invoke(userId: String): NetworkResult<User> {
        return friendshipRepository.acceptFriendship(userId)

//        return NetworkResult.Success(result = user)
//        return NetworkResult.Success(result = users)
//        return NetworkResult.Loading
//        return NetworkResult.Error(404, "")
    }
}