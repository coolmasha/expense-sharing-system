package com.mashakulabukhova.expensesharingsystem.domain.usecase.event

import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.EventRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject


class GetParticipantsUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {

    suspend operator fun invoke(eventId: String): NetworkResult<List<User>> {
        return eventRepository.getParticipants(eventId)
    }
}
