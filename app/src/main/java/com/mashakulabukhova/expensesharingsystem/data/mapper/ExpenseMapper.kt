package com.mashakulabukhova.expensesharingsystem.data.mapper

import com.mashakulabukhova.expensesharingsystem.data.remote.model.ExpenseDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.Expense

fun ExpenseDto.toExpense(): Expense {
    return Expense(
        id = this.id,
        eventId = this.eventId,
        title = this.title,
        payerUsername = this.payerUsername,
        amount = this.amount
    )
}