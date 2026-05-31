package com.mashakulabukhova.expensesharingsystem.data.remote.model

import com.google.gson.annotations.SerializedName

data class EventDto(
    @SerializedName("event_id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("creatorId")
    val creatorId: String,
    @SerializedName("isFinished")
    val isFinished: Boolean
)