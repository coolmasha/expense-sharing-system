package com.mashakulabukhova.expensesharingsystem.domain.entity.request

import com.mashakulabukhova.expensesharingsystem.domain.entity.User

data class EventRequest(
    val title: String,
    val iconId: Int,
    val participants: List<User> = emptyList()
)