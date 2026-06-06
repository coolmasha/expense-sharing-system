package com.mashakulabukhova.expensesharingsystem.domain.usecase.event

import com.mashakulabukhova.expensesharingsystem.domain.entity.request.EventRequest
import com.mashakulabukhova.expensesharingsystem.domain.repository.EventRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

class CreateEventWithoutBodyUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {

    suspend operator fun invoke(body: EventRequest): NetworkResult<Unit> {
        return eventRepository.createEventWithoutBody(body)
    }
}