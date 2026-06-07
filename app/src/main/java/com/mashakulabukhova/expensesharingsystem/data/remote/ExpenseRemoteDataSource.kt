package com.mashakulabukhova.expensesharingsystem.data.remote

import com.mashakulabukhova.expensesharingsystem.data.model.request.ExpenseRequestDto
import com.mashakulabukhova.expensesharingsystem.data.model.request.UserExpenseRequestDto
import retrofit2.http.Path
import javax.inject.Inject

class ExpenseRemoteDataSource @Inject constructor(
    private val expenseService: ExpenseService
) {

    suspend fun getMyExpenses(event_id: String) = expenseService.getMyExpenses(event_id = event_id)
    suspend fun getExpenses(event_id: String) = expenseService.getExpenses(event_id = event_id)
    suspend fun postExpense(body: ExpenseRequestDto) = expenseService.postExpense(body = body)
    suspend fun deleteExpense(expense_id: String) = expenseService.deleteExpense(expense_id = expense_id)

    suspend fun postParticipant(body: UserExpenseRequestDto) = expenseService.postParticipant(body = body)
}