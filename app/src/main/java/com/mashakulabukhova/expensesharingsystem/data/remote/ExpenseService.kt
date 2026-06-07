package com.mashakulabukhova.expensesharingsystem.data.remote

import com.mashakulabukhova.expensesharingsystem.data.model.AmountDto
import com.mashakulabukhova.expensesharingsystem.data.model.ExpenseDto
import com.mashakulabukhova.expensesharingsystem.data.model.request.ExpenseRequestDto
import com.mashakulabukhova.expensesharingsystem.data.model.request.ParticipantsRequestDto
import com.mashakulabukhova.expensesharingsystem.data.model.request.UserExpenseRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ExpenseService {

    @GET("")
    suspend fun getMyExpenses(@Path("event_id") event_id: String): Response<List<ExpenseDto>>

    @GET("expenses/{event_id}")
    suspend fun getExpenses(@Path("event_id") event_id: String): Response<List<ExpenseDto>>

    @POST("expenses")
    suspend fun postExpense(@Body body: ExpenseRequestDto): Response<ExpenseDto>

    @DELETE("expenses/{expense_id}")
    suspend fun deleteExpense(@Path("expense_id") expense_id: String): Response<Unit>


    @POST("expense-participants")
    suspend fun postParticipant(@Body body: UserExpenseRequestDto): Response<Unit>


    @GET("")
    suspend fun getMyExpenseAmount(@Path("event_id") event_id: String): Response<AmountDto>


}