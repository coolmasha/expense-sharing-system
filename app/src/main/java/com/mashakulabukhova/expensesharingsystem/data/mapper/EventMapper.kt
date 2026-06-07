package com.mashakulabukhova.expensesharingsystem.data.mapper

import com.mashakulabukhova.expensesharingsystem.data.model.EventDto
import com.mashakulabukhova.expensesharingsystem.data.model.request.EventRequestDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.entity.request.EventRequest

fun EventDto.toEvent(): Event {
    return Event(
        id = this.id,
        title = this.title,
        iconId = this.iconId.toIntOrNull() ?: 0,
        creatorId = this.creatorId,
        isFinished = this.isFinished,
    )
}

fun EventRequest.toEventRequestDto(): EventRequestDto{
    return EventRequestDto(
        title = this.title,
        iconId = this.iconId.toString()
    )
}