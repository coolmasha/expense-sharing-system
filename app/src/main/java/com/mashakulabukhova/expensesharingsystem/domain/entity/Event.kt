package com.mashakulabukhova.expensesharingsystem.domain.entity

data class Event(
    val id: String,
    val title: String,
    val iconId: Int,
    val creatorId: String,
    val participants: List<User> = emptyList(),
    val isFinished: Boolean
)
