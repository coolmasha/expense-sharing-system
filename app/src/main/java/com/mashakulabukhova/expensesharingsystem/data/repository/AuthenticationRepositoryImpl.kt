package com.mashakulabukhova.expensesharingsystem.data.repository

import com.mashakulabukhova.expensesharingsystem.data.mapper.toUser
import com.mashakulabukhova.expensesharingsystem.data.remote.AuthenticationRemoteDataSource
import com.mashakulabukhova.expensesharingsystem.data.remote.model.LoginRequest
import com.mashakulabukhova.expensesharingsystem.data.remote.model.UserDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.AuthenticationRepository
import com.mashakulabukhova.expensesharingsystem.utils.BaseApiResponse
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationRemoteDataSource: AuthenticationRemoteDataSource,
    private val baseApiResponse: BaseApiResponse
): AuthenticationRepository {

    override suspend fun login(body: LoginRequest): NetworkResult<User> {
        return baseApiResponse.safeApiCall(
            api = { authenticationRemoteDataSource.login(body) },
            mapper = { userDto: UserDto -> userDto.toUser() }
        )
    }
}