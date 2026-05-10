package com.mashakulabukhova.expensesharingsystem.domain.repository

import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun logIn(login: String, password: String): User

    fun getAllFriends(): Flow<List<User>>

    suspend fun addNewFriend(userName: String)
}