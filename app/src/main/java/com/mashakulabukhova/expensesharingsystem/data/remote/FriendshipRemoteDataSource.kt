package com.mashakulabukhova.expensesharingsystem.data.remote

import com.mashakulabukhova.expensesharingsystem.data.remote.model.request.FriendRequest
import javax.inject.Inject

class FriendshipRemoteDataSource @Inject constructor(
    private val friendshipService: FriendshipService
) {

    suspend fun getIncomingFriendRequests() = friendshipService.getIncomingFriendRequests()
    suspend fun getOutgoingFriendRequests() = friendshipService.getOutgoingFriendRequests()
    suspend fun sendFriendRequest(body: FriendRequest) = friendshipService.sendFriendRequest(body)
    suspend fun acceptFriendRequest(id: String) = friendshipService.acceptFriendRequest(id)
    suspend fun rejectFriendRequest(id: String) = friendshipService.rejectFriendRequest(id)
    suspend fun cancelFriendRequest(id: String) = friendshipService.cancelFriendRequest(id)
}
