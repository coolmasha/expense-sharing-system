package com.mashakulabukhova.expensesharingsystem.domain.repository

import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult

interface FriendRepository {

//    suspend fun getAllFriends(user_id: String): NetworkResult<List<User>>
    suspend fun getAllFriends(): NetworkResult<List<User>>
    suspend fun getFriend(user_id: String): NetworkResult<User>
    suspend fun deleteFriend(id: String): NetworkResult<Unit>
}