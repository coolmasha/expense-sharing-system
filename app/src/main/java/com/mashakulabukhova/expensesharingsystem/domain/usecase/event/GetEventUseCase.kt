package com.mashakulabukhova.expensesharingsystem.domain.usecase.event

import com.mashakulabukhova.expensesharingsystem.R
import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.EventRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject


private val users1 = listOf(
    User(id = "6", username = "anna_koval", email = "anna@ukr.net"),
    User(id = "7", username = "serg_ivanov", email = "serg@example.com"),
    User(id = "8", username = "olga_smith", email = "olga@gmail.com"),
    User(id = "9", username = "maxim_litvin", email = "maxim@mail.ru"),
    User(id = "10", username = "nadia_f", email = "nadia@example.com")
)

private val event1 = Event(
    "123",
    "Шашлыки",
    1,
    "RUB",
    isFinished = false
)

class GetEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {

    suspend operator fun invoke(eventId: String): NetworkResult<Event> {
        return eventRepository.getEvent(eventId)
//        return NetworkResult.Success(event1)
    }
}