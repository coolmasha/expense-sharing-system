package com.mashakulabukhova.expensesharingsystem.domain.usecase.event

import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.repository.EventRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

private val eventList1 = listOf(
    Event(
        id = "1",
        title = "Конференция по Kotlin",
        iconId = 76,
        creatorId = "user_101",
        isFinished = false
    )
)

class GetMyEventsUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {

    suspend operator fun invoke(): NetworkResult<List<Event>> {
        return eventRepository.getMyEvents()

//        return NetworkResult.Success(eventList1)
    }
}