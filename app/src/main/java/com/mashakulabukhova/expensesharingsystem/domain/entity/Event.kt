package com.mashakulabukhova.expensesharingsystem.domain.entity

data class Event(
    val id: String,
    val title: String,
    val category: String,
    val creatorId: String,
    val isFinished: Boolean
)
