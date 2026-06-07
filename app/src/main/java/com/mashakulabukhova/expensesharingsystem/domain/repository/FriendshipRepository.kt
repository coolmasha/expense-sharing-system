package com.mashakulabukhova.expensesharingsystem.domain.repository

import com.mashakulabukhova.expensesharingsystem.data.model.request.FriendRequest
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult

interface FriendshipRepository {

    suspend fun getIncomingFriendship(): NetworkResult<List<User>>
    suspend fun getOutgoingFriendship(): NetworkResult<List<User>>
    suspend fun sendFriendship(body: FriendRequest): NetworkResult<User>

    suspend fun acceptFriendship(id: String): NetworkResult<User>
    suspend fun rejectFriendship(id: String): NetworkResult<Unit>
    suspend fun cancelFriendship(id: String): NetworkResult<Unit>
}