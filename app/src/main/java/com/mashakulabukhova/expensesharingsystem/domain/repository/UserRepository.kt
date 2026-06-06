package com.mashakulabukhova.expensesharingsystem.domain.repository

import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUsersByName(username: String): NetworkResult<List<User>>
    suspend fun getUser(user_id: String): NetworkResult<User>
    suspend fun getAllUsers(): NetworkResult<List<User>>
    suspend fun getMyProfile(): NetworkResult<User>
}