package com.mashakulabukhova.expensesharingsystem.domain.entity.request

import com.mashakulabukhova.expensesharingsystem.domain.entity.User

data class ExpenseRequest(
    val eventId: String,
    val title: String,
    val payerId: String,
    val totalCost: Long,
    val currency: String,
    val dateOfPayment: String,
    val users: List<User>
)
