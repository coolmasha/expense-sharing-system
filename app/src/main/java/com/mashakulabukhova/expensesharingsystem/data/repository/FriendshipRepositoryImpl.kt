package com.mashakulabukhova.expensesharingsystem.data.repository

import com.mashakulabukhova.expensesharingsystem.data.mapper.toUser
import com.mashakulabukhova.expensesharingsystem.data.remote.FriendshipRemoteDataSource
import com.mashakulabukhova.expensesharingsystem.data.model.request.FriendRequest
import com.mashakulabukhova.expensesharingsystem.data.model.UserDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.FriendshipRepository
import com.mashakulabukhova.expensesharingsystem.utils.BaseApiResponse
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

class FriendshipRepositoryImpl @Inject constructor(
    private val friendshipRemoteDataSource: FriendshipRemoteDataSource,
    private val baseApiResponse: BaseApiResponse
) : FriendshipRepository {
    override suspend fun getIncomingFriendship(): NetworkResult<List<User>> {
        return baseApiResponse.safeApiCall(
            api = { friendshipRemoteDataSource.getIncomingFriendRequests() },
            mapper = { userDtoList: List<UserDto> -> userDtoList.map { it.toUser() } }
        )
    }

    override suspend fun getOutgoingFriendship(): NetworkResult<List<User>> {
        return baseApiResponse.safeApiCall(
            api = { friendshipRemoteDataSource.getOutgoingFriendRequests() },
            mapper = { userDtoList: List<UserDto> -> userDtoList.map { it.toUser() } }
        )
    }

    override suspend fun sendFriendship(body: FriendRequest): NetworkResult<User> {
        return baseApiResponse.safeApiCall(
            api = { friendshipRemoteDataSource.sendFriendRequest(body) },
            mapper = { userDto: UserDto -> userDto.toUser() }
        )
    }

    override suspend fun acceptFriendship(id: String): NetworkResult<User> {
        return baseApiResponse.safeApiCall(
            api = { friendshipRemoteDataSource.acceptFriendRequest(id) },
            mapper = { userDto: UserDto -> userDto.toUser() }
        )
    }

    override suspend fun rejectFriendship(id: String): NetworkResult<Unit> {
        return baseApiResponse.safeApiCall(
            api = { friendshipRemoteDataSource.rejectFriendRequest(id) },
            mapper = { Unit }
        )
    }

    override suspend fun cancelFriendship(id: String): NetworkResult<Unit> {
        return baseApiResponse.safeApiCall(
            api = { friendshipRemoteDataSource.cancelFriendRequest(id) },
            mapper = { Unit }
        )
    }
}