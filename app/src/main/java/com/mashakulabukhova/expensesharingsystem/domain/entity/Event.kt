package com.mashakulabukhova.expensesharingsystem.domain.entity

data class Event(
    val id: Int,
    val title: String,
    val creatorId: Int,
    val isFinished: Boolean
)
