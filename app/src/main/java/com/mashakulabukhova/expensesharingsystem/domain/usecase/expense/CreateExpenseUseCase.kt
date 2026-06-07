package com.mashakulabukhova.expensesharingsystem.domain.usecase.expense

import com.mashakulabukhova.expensesharingsystem.domain.entity.request.ExpenseRequest
import com.mashakulabukhova.expensesharingsystem.domain.repository.ExpenseRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

class CreateExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {

    suspend operator fun invoke(body: ExpenseRequest): NetworkResult<Unit> {
        return expenseRepository.postExpense(body)
    }
}