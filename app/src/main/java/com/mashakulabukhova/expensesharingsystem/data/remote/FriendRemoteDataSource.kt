package com.mashakulabukhova.expensesharingsystem.data.remote

import javax.inject.Inject

class FriendRemoteDataSource @Inject constructor(
    private val friendService: FriendService
) {

//    suspend fun getAllFriends(user_id: String) = friendService.getAllFriends(user_id = user_id)
    suspend fun getAllFriends() = friendService.getAllFriends()
    suspend fun getFriend(user_id: String) = friendService.getFriend(user_id = user_id)
    suspend fun deleteFriend(id: String) = friendService.deleteFriend(id = id)
}