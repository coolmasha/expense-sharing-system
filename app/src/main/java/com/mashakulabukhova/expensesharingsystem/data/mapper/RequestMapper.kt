package com.mashakulabukhova.expensesharingsystem.data.mapper

import com.mashakulabukhova.expensesharingsystem.data.remote.model.request.EventRequestDto
import com.mashakulabukhova.expensesharingsystem.data.remote.model.request.ParticipantsRequestDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.entity.request.EventRequest

fun EventRequest.toEventRequestDto(): EventRequestDto{
    return EventRequestDto(
        title = this.title,
        iconId = this.iconId.toString()
    )
}

fun User.toParticipantsRequestDto(eventId: String): ParticipantsRequestDto {
    return ParticipantsRequestDto(
        eventId = eventId,
        userId = this.id
    )
}