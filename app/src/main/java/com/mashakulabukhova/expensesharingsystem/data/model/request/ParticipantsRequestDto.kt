package com.mashakulabukhova.expensesharingsystem.data.model.request

import com.google.gson.annotations.SerializedName

data class ParticipantsRequestDto (
    @SerializedName("eventId")
    val eventId: String,
    @SerializedName("userId")
    val userId: String
)