package com.mashakulabukhova.expensesharingsystem.domain.usecase.users

import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.UserRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

private val users = listOf(
    User(id = "1", username = "Димид", email = "alex@example.com"),
    User(id = "2", username = "Давид", email = "maria@example.com"),
    User(id = "3", username = "Антон", email = "john@gmail.com"),
    User(id = "4", username = "Лена", email = "elena@mail.ru")
)

class SearchUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(username: String): NetworkResult<List<User>> {
        return userRepository.getUsersByName(username)

//        return NetworkResult.Success(result = emptyList())
//        return NetworkResult.Success(result = users)
//        return NetworkResult.Loading
//        return NetworkResult.Error(404, "")
    }
}