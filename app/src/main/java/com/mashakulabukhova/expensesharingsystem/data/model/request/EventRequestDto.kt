package com.mashakulabukhova.expensesharingsystem.data.model.request

import com.google.gson.annotations.SerializedName

data class EventRequestDto(
    @SerializedName("eventName")
    val title: String,
    @SerializedName("iconId")
    val iconId: String
)
