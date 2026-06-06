package com.mashakulabukhova.expensesharingsystem.domain.usecase.friendship

import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.FriendshipRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

private val users = listOf(
    User(id = "1", username = "Таня", email = "alex@example.com"),
    User(id = "2", username = "Карина", email = "maria@example.com"),
    User(id = "3", username = "Олег", email = "john@gmail.com")
)

class GetIncomingFriendshipUseCase @Inject constructor(
    private val friendshipRepository: FriendshipRepository
) {

    suspend operator fun invoke(): NetworkResult<List<User>> {
        return friendshipRepository.getIncomingFriendship()

//        return NetworkResult.Success(result = emptyList())
//        return NetworkResult.Success(result = users)
//        return NetworkResult.Loading
//        return NetworkResult.Error(404, "")
    }
}