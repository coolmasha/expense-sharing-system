package com.mashakulabukhova.expensesharingsystem.data.mapper

import com.mashakulabukhova.expensesharingsystem.data.model.ExpenseDto
import com.mashakulabukhova.expensesharingsystem.data.model.request.ExpenseRequestDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.Expense
import com.mashakulabukhova.expensesharingsystem.domain.entity.request.ExpenseRequest

fun ExpenseDto.toExpense(): Expense {
    return Expense(
        id = this.id,
        eventId = this.eventId,
        title = this.title,
        payer = this.payer.toUser(),
        dateOfPayment = this.dateOfPayment,
        totalCost = this.totalCost,
        currency = this.currency,
        isActive = this.isActive,
        category = this.category,
        splitType = this.splitType
    )
}

fun ExpenseRequest.toExpenseRequestDto(): ExpenseRequestDto {
    return ExpenseRequestDto(
        eventId = this.eventId,
        title = this.title,
        payerId = this.payerId,
        totalCost = this.totalCost,
        currency = this.currency,
        dateOfPayment = this.dateOfPayment,
    )
}