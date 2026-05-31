package com.mashakulabukhova.expensesharingsystem.domain.usecase.event

import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.repository.EventRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

private val event1 = Event(
    "123",
    "Шашлыки",
    "Ресторан",
    "123",
    false
)

class GetEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {

    suspend operator fun invoke(eventId: String): NetworkResult<Event> {
//        return eventRepository.getEvent(eventId)
        return NetworkResult.Success(event1)
    }
}