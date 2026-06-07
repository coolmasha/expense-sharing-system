package com.mashakulabukhova.expensesharingsystem.data.model.request

import com.google.gson.annotations.SerializedName

data class FriendRequest(
    @SerializedName("toUserId")
    val toUserId: String,
)