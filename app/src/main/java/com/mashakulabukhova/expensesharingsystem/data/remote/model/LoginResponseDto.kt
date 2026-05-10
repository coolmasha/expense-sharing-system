package com.mashakulabukhova.expensesharingsystem.data.remote.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class LoginResponseDto(
    @SerializedName("user")
    val user: UserDto,  // ← вложенный объект UserDto

    @SerializedName("token")
    val token: String    // ← отдельно токен
)
