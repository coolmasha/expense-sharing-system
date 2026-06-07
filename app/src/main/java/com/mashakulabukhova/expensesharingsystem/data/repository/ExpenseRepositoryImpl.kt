package com.mashakulabukhova.expensesharingsystem.data.repository

import android.util.Log
import com.mashakulabukhova.expensesharingsystem.data.mapper.toExpense
import com.mashakulabukhova.expensesharingsystem.data.mapper.toExpenseRequestDto
import com.mashakulabukhova.expensesharingsystem.data.mapper.toParticipantsRequestDto
import com.mashakulabukhova.expensesharingsystem.data.mapper.toUserExpenseRequestDto
import com.mashakulabukhova.expensesharingsystem.data.remote.ExpenseRemoteDataSource
import com.mashakulabukhova.expensesharingsystem.data.model.ExpenseDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.Expense
import com.mashakulabukhova.expensesharingsystem.domain.entity.request.ExpenseRequest
import com.mashakulabukhova.expensesharingsystem.domain.repository.ExpenseRepository
import com.mashakulabukhova.expensesharingsystem.utils.BaseApiResponse
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseRemoteDataSource: ExpenseRemoteDataSource,
    private val baseApiResponse: BaseApiResponse
) : ExpenseRepository {

    override suspend fun getMyExpenses(eventId: String): NetworkResult<List<Expense>> {
        return baseApiResponse.safeApiCall(
            api = { expenseRemoteDataSource.getMyExpenses(event_id = eventId) },
            mapper = { expenseListDto: List<ExpenseDto> -> expenseListDto.map { it.toExpense() }}
        )
    }

    override suspend fun getExpenses(eventId: String): NetworkResult<List<Expense>> {
        return baseApiResponse.safeApiCall(
            api = { expenseRemoteDataSource.getExpenses(event_id = eventId) },
            mapper = { expenseListDto: List<ExpenseDto> -> expenseListDto.map { it.toExpense() }}
        )
    }

    override suspend fun postExpense(body: ExpenseRequest): NetworkResult<Unit> {
        val expenseResult = baseApiResponse.safeApiCall(
            api = { expenseRemoteDataSource.postExpense(body.toExpenseRequestDto()) },
            mapper = { expenseDto: ExpenseDto -> expenseDto.toExpense() }
        )

        Log.d("CreateExpense", "${expenseResult}")

        return when (expenseResult) {
            is NetworkResult.Error -> {
                Log.d("CreateExpense", "Error here")
                NetworkResult.Error(code = expenseResult.code, message = "Не удалось создать расход")
            }

            is NetworkResult.Loading -> {
                Log.d("CreateExpense", "Loading here")
                NetworkResult.Loading
            }

            is NetworkResult.Success -> {
                Log.d("CreateExpense", "Success here")
                val expenseId = expenseResult.result.id
                var allParticipantsSuccess = true

                for (user in body.users) {
                    Log.d("CreateExpense", "User: $user")
                    val participantsResult = baseApiResponse.safeApiCallIgnoreBody(
                        api = {expenseRemoteDataSource.postParticipant(user.toUserExpenseRequestDto(expenseId))}
                    )

                    Log.d("CreateEvent", "ParticipantsResult: $participantsResult")
                    if (participantsResult is NetworkResult.Error) {
                        allParticipantsSuccess = false
                        break
                    }
                }

                if (allParticipantsSuccess) {
                    NetworkResult.Success(Unit)
                } else {
                    NetworkResult.Error(
                        message = "Расход создан, но не удалось добавить участников"
                    )
                }
            }
        }
    }

    override suspend fun deleteExpense(expenseId: String): NetworkResult<Unit> {
        return baseApiResponse.safeApiCallIgnoreBody(
            api = { expenseRemoteDataSource.deleteExpense(expenseId)}
        )
    }
}