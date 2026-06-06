package com.mashakulabukhova.expensesharingsystem.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class ParticipantsRequestDto (
    @SerializedName("eventId")
    val eventId: String,
    @SerializedName("userId")
    val userId: String
)