package com.mashakulabukhova.expensesharingsystem.domain.usecase.friendship

import com.mashakulabukhova.expensesharingsystem.data.remote.model.request.FriendRequest
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.FriendshipRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

private val user = User(id = "3", username = "Полина", email = "john@gmail.com")

//отправить запрос на дружбу
class SendFriendshipUseCase @Inject constructor(
    private val friendshipRepository: FriendshipRepository
) {

    suspend operator fun invoke(body: FriendRequest): NetworkResult<User> {
        return friendshipRepository.sendFriendship(body)

//        return NetworkResult.Success(result = emptyList())
//        return NetworkResult.Success(result = user)
//        return NetworkResult.Loading
//        return NetworkResult.Error(404, "")
    }
}