package com.mashakulabukhova.expensesharingsystem.data.repository

import android.util.Log
import com.mashakulabukhova.expensesharingsystem.data.local.TokenManager
import com.mashakulabukhova.expensesharingsystem.data.mapper.toUser
import com.mashakulabukhova.expensesharingsystem.data.remote.AuthenticationRemoteDataSource
import com.mashakulabukhova.expensesharingsystem.data.model.request.LoginRequest
import com.mashakulabukhova.expensesharingsystem.data.model.request.RegistrationRequest
import com.mashakulabukhova.expensesharingsystem.data.model.UserDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.AuthenticationRepository
import com.mashakulabukhova.expensesharingsystem.utils.BaseApiResponse
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationRemoteDataSource: AuthenticationRemoteDataSource,
    private val baseApiResponse: BaseApiResponse
) : AuthenticationRepository {

    override suspend fun login(body: LoginRequest): NetworkResult<User> {
        return baseApiResponse.safeApiCall(
            api = { authenticationRemoteDataSource.login(body) },
            mapper = { userDto: UserDto ->
                Log.d("Registration", "Received UserDto: id=${userDto.user_id}, username=${userDto.username}, email=${userDto.email}")
                userDto.token?.let { TokenManager.saveToken(it) }
                userDto.toUser()
            }
        )
    }

    override suspend fun registration(body: RegistrationRequest): NetworkResult<User> {
        return baseApiResponse.safeApiCall(
            api = { authenticationRemoteDataSource.registration(body) },
            mapper = { userDto: UserDto ->
                Log.d("Registration", "Received UserDto: id=${userDto.user_id}, username=${userDto.username}, email=${userDto.email}")
                userDto.token?.let { TokenManager.saveToken(it) }
                userDto.toUser()
            }
        )
    }
}