package com.mashakulabukhova.expensesharingsystem.data.model

import com.google.gson.annotations.SerializedName

data class ExpenseDto(
    @SerializedName("expenseId")
    val id: String,
    @SerializedName("eventId")
    val eventId: String,
    @SerializedName("name")
    val title: String = "",
    @SerializedName("payer")
    val payer: UserDto,
    @SerializedName("dateOfPayment")
    val dateOfPayment: String = "",
    @SerializedName("totalCost")
    val totalCost: Long = 0,
    @SerializedName("currency")
    val currency: String = "",
    @SerializedName("isActive")
    val isActive: Boolean = true,
    @SerializedName("category")
    val category: String = "",
    @SerializedName("splitType")
    val splitType: String = "",
)
