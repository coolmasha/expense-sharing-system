package com.mashakulabukhova.expensesharingsystem.data.remote.model

import com.google.gson.annotations.SerializedName

data class ExpenseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("eventId")
    val eventId: String,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("payerId")
    val payerUsername: String = "",
    @SerializedName("amount")
    val amount: String = ""
)
