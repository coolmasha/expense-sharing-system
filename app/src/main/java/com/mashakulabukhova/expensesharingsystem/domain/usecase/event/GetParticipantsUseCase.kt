package com.mashakulabukhova.expensesharingsystem.domain.usecase.event

import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.EventRepository
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

class GetParticipantsUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {

    suspend operator fun invoke(eventId: String): NetworkResult<List<User>> {
        return eventRepository.getParticipants(eventId)
//        return NetworkResult.Success(users)
    }
}
