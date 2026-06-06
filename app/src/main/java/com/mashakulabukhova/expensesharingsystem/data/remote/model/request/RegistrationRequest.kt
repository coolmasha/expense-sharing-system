package com.mashakulabukhova.expensesharingsystem.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("password2")
    val password2: String
)
