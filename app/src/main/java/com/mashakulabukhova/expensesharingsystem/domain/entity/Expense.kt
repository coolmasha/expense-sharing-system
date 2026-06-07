package com.mashakulabukhova.expensesharingsystem.domain.entity

data class Expense(
    val id: String,
    val eventId: String,
    val title: String,
    val payer: User,
    val dateOfPayment: String,
    val totalCost: Long,
    val currency: String,
    val isActive: Boolean,
    val category: String,
    val splitType: String
)

