package com.mashakulabukhova.expensesharingsystem.data.remote

import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
) {

    suspend fun getUsersByName(username: String) = userService.getUsersByName(username = username)
    suspend fun getUser(user_id: String) = userService.getUser(user_id = user_id)
    suspend fun getAllUsers() = userService.getAllUsers()
    suspend fun getMyProfile() = userService.getMyProfile()
}