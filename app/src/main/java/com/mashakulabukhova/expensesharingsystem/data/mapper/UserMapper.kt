package com.mashakulabukhova.expensesharingsystem.data.mapper

import com.mashakulabukhova.expensesharingsystem.data.remote.model.UserDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.User

fun UserDto.toUser(): User {
    return User(
        id = this.id,
        name = this.name,
        username = this.username,
        email = this.email,
    )
}