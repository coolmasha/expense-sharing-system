package com.mashakulabukhova.expensesharingsystem.data.repository

import com.mashakulabukhova.expensesharingsystem.data.mapper.toUser
import com.mashakulabukhova.expensesharingsystem.data.remote.UserRemoteDataSource
import com.mashakulabukhova.expensesharingsystem.data.model.UserDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.UserRepository
import com.mashakulabukhova.expensesharingsystem.utils.BaseApiResponse
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val baseApiResponse: BaseApiResponse
) : UserRepository {

    override suspend fun getAllUsers(): NetworkResult<List<User>> {
        return baseApiResponse.safeApiCall(
            api = { userRemoteDataSource.getAllUsers() },
            mapper = { userDtoList: List<UserDto> -> userDtoList.map { it.toUser() } }
        )
    }

    override suspend fun getUsersByName(username: String): NetworkResult<List<User>> {
        return baseApiResponse.safeApiCall(
            api = { userRemoteDataSource.getUsersByName(username) },
            mapper = { userDtoList: List<UserDto> -> userDtoList.map { it.toUser() } }
        )
    }

    override suspend fun getUser(user_id: String): NetworkResult<User> {
        return baseApiResponse.safeApiCall(
            api = { userRemoteDataSource.getUser(user_id) },
            mapper = { userDto: UserDto -> userDto.toUser() }
        )
    }

    override suspend fun getMyProfile(): NetworkResult<User> {
        return baseApiResponse.safeApiCall(
            api = { userRemoteDataSource.getMyProfile() },
            mapper = { userDto: UserDto -> userDto.toUser() }
        )
    }
}