package com.mashakulabukhova.expensesharingsystem.domain.usecase.event

import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.repository.EventRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

class GetMyEventsUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {

    suspend operator fun invoke(): NetworkResult<List<Event>> {
        return eventRepository.getMyEvents()

//        return NetworkResult.Success(eventList1)
    }
}