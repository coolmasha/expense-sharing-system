package com.mashakulabukhova.expensesharingsystem.domain.usecase.expense

import com.mashakulabukhova.expensesharingsystem.domain.entity.Expense
import com.mashakulabukhova.expensesharingsystem.domain.repository.ExpenseRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

class GetAllExpensesUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {

    suspend operator fun invoke(eventId: String): NetworkResult<List<Expense>> {
        return expenseRepository.getExpenses(eventId)
    }
}