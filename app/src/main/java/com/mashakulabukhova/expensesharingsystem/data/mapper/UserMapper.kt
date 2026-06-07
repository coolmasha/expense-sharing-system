package com.mashakulabukhova.expensesharingsystem.data.mapper

import com.mashakulabukhova.expensesharingsystem.data.model.UserDto
import com.mashakulabukhova.expensesharingsystem.data.model.request.ParticipantsRequestDto
import com.mashakulabukhova.expensesharingsystem.data.model.request.UserExpenseRequestDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.User

fun UserDto.toUser(): User {
    return User(
        id = this.user_id,
        username = this.username,
        email = this.email,
        status = this.status
    )
}

fun User.toParticipantsRequestDto(eventId: String): ParticipantsRequestDto {
    return ParticipantsRequestDto(
        eventId = eventId,
        userId = this.id
    )
}

fun User.toUserExpenseRequestDto(expenseId: String): UserExpenseRequestDto {
    return UserExpenseRequestDto(
        expenseId = expenseId,
        userId = this.id
    )
}