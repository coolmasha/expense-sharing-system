package com.mashakulabukhova.expensesharingsystem.data.remote.model

import com.google.gson.annotations.SerializedName

data class EventRequestDto(
    @SerializedName("title")
    val title: String
)
