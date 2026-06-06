package com.mashakulabukhova.expensesharingsystem.data.repository

import com.mashakulabukhova.expensesharingsystem.data.mapper.toUser
import com.mashakulabukhova.expensesharingsystem.data.remote.FriendRemoteDataSource
import com.mashakulabukhova.expensesharingsystem.data.remote.model.UserDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.FriendRepository
import com.mashakulabukhova.expensesharingsystem.utils.BaseApiResponse
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val friendRemoteDataSource: FriendRemoteDataSource,
    private val baseApiResponse: BaseApiResponse
) : FriendRepository {

    //    override suspend fun getAllFriends(user_id: String): NetworkResult<List<User>> {
//        return baseApiResponse.safeApiCall(
//            api = { friendRemoteDataSource.getAllFriends(user_id) },
//            mapper = { userDtoList: List<UserDto> -> userDtoList.map { it.toUser() } }
//        )
//    }
    override suspend fun deleteFriend(id: String): NetworkResult<Unit> {
        return baseApiResponse.safeApiCall(
            api = { friendRemoteDataSource.deleteFriend(id)},
            mapper = { Unit }
        )
    }

    override suspend fun getAllFriends(): NetworkResult<List<User>> {
        return baseApiResponse.safeApiCall(
            api = { friendRemoteDataSource.getAllFriends() },
            mapper = { userDtoList: List<UserDto> -> userDtoList.map { it.toUser() } }
        )
    }

    override suspend fun getFriend(user_id: String): NetworkResult<User> {
        return baseApiResponse.safeApiCall(
            api = { friendRemoteDataSource.getFriend(user_id) },
            mapper = { userDto: UserDto -> userDto.toUser() }
        )
    }
}