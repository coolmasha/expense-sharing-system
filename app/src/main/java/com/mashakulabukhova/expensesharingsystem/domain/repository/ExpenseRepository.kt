package com.mashakulabukhova.expensesharingsystem.domain.repository

import com.mashakulabukhova.expensesharingsystem.domain.entity.Expense
import com.mashakulabukhova.expensesharingsystem.domain.entity.request.ExpenseRequest
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult

interface ExpenseRepository {

    suspend fun getMyExpenses(eventId: String): NetworkResult<List<Expense>>
    suspend fun getExpenses(eventId: String): NetworkResult<List<Expense>>
    suspend fun postExpense(body: ExpenseRequest): NetworkResult<Unit>
    suspend fun deleteExpense(expenseId: String): NetworkResult<Unit>

}