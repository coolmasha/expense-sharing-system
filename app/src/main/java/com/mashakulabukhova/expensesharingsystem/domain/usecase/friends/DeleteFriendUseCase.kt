package com.mashakulabukhova.expensesharingsystem.domain.usecase.friends

import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.FriendRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

class DeleteFriendUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {

    suspend operator fun invoke(id: String): NetworkResult<Unit> {
        return friendRepository.deleteFriend(id)

//        return NetworkResult.Success(result = Unit)
    }
}