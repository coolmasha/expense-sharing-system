package com.mashakulabukhova.expensesharingsystem.domain.entity

data class Expense(
    val id: String,
    val title: String,
    val eventId: String,
    val payerUsername: String,
    val amount: String
)

