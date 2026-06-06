package com.mashakulabukhova.expensesharingsystem.domain.entity

data class User(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val status: String? = null
)
