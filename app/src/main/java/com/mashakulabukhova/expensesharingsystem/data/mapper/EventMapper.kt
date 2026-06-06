package com.mashakulabukhova.expensesharingsystem.data.mapper

import com.mashakulabukhova.expensesharingsystem.data.remote.model.EventDto
import com.mashakulabukhova.expensesharingsystem.data.remote.model.UserDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.entity.User

fun EventDto.toEvent(): Event {
    return Event(
        id = this.id,
        title = this.title,
        iconId = this.iconId.toIntOrNull() ?: 0,
        creatorId = this.creatorId,
        isFinished = this.isFinished,
    )
}