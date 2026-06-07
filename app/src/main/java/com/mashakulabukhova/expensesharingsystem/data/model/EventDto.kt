package com.mashakulabukhova.expensesharingsystem.data.model

import com.google.gson.annotations.SerializedName

data class EventDto(
    @SerializedName(value = "event_id",
        alternate = ["eventId", "id"])
    val id: String,
    @SerializedName("eventName")
    val title: String,
    @SerializedName("iconId")
    val iconId: String = "0",
    @SerializedName("creatorId")
    val creatorId: String = "",
    @SerializedName("finished")
    val isFinished: Boolean = false
)