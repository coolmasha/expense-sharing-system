package com.mashakulabukhova.expensesharingsystem.data.remote.model.request

import com.google.gson.annotations.SerializedName


data class LoginRequest(
    @SerializedName("email")
    val login: String,
    @SerializedName("password")
    val password: String
)