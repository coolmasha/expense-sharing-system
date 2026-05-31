package com.mashakulabukhova.expensesharingsystem.data.mapper

import com.mashakulabukhova.expensesharingsystem.data.remote.model.EventRequestDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.EventRequest

fun EventRequest.toEventRequestDto(): EventRequestDto{
    return EventRequestDto(
        title = this.title
    )
}