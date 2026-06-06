package com.mashakulabukhova.expensesharingsystem.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName(value = "user_id",
        alternate = ["userId", "id"] )
    val user_id: String = "",
    @SerializedName("username")
    val username: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("status")
    val status: String? = null
)