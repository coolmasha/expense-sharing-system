package com.mashakulabukhova.expensesharingsystem.data.remote

import com.mashakulabukhova.expensesharingsystem.data.remote.model.AmountDto
import com.mashakulabukhova.expensesharingsystem.data.remote.model.ExpenseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ExpenseService {

    @GET("")
    suspend fun getMyExpenses(@Path("event_id") event_id: String): Response<List<ExpenseDto>>


    @GET("")
    suspend fun getMyExpenseAmount(@Path("event_id") event_id: String): Response<AmountDto>


}