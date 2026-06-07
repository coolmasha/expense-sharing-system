package com.mashakulabukhova.expensesharingsystem.domain.usecase.expense

import com.mashakulabukhova.expensesharingsystem.domain.repository.ExpenseRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

class DeleteExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {

    suspend operator fun invoke(expenseId: String): NetworkResult<Unit> {
//        return NetworkResult.Success(result = Unit)
        return expenseRepository.deleteExpense(expenseId)
    }

}