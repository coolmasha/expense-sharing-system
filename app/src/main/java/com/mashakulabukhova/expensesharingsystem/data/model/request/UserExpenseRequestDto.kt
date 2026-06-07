package com.mashakulabukhova.expensesharingsystem.data.model.request

import com.google.gson.annotations.SerializedName

data class UserExpenseRequestDto(
    @SerializedName("expenseId")
    val expenseId: String,
    @SerializedName("userId")
    val userId: String
)
