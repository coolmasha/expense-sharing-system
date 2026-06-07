package com.mashakulabukhova.expensesharingsystem.data.model.request

import com.google.gson.annotations.SerializedName

data class ExpenseRequestDto(
    @SerializedName("eventId")
    val eventId: String,
    @SerializedName("name")
    val title: String = "",
    @SerializedName("payerId")
    val payerId: String = "",
    @SerializedName("totalCost")
    val totalCost: Long = 0,
    @SerializedName("currency")
    val currency: String = "",
    @SerializedName("dateOfPayment")
    val dateOfPayment: String = "",
)

