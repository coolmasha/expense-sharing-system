package com.mashakulabukhova.expensesharingsystem.domain.usecase.friends

import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

private val users = listOf(
    User(id = "1", username = "alex_kuznetsov", email = "alex@example.com"),
    User(id = "2", username = "maria_s", email = "maria@example.com"),
    User(id = "3", username = "john_doe", email = "john@gmail.com"),
    User(id = "4", username = "elena_petr", email = "elena@mail.ru"),
    User(id = "5", username = "dmitry_volkov", email = "dmitry@yandex.ru"),
    User(id = "6", username = "anna_koval", email = "anna@ukr.net"),
    User(id = "7", username = "serg_ivanov", email = "serg@example.com"),
    User(id = "8", username = "olga_smith", email = "olga@gmail.com"),
    User(id = "9", username = "maxim_litvin", email = "maxim@mail.ru"),
    User(id = "10", username = "nadia_f", email = "nadia@example.com")
)

class SearchUserUseCase @Inject constructor(
) {

    suspend operator fun invoke(username: String): NetworkResult<List<User>> {
        return NetworkResult.Success(result = emptyList())
//        return NetworkResult.Success(result = users)
//        return NetworkResult.Loading
//        return NetworkResult.Error(404, "")
    }
}