package com.mashakulabukhova.expensesharingsystem.data.local

object TokenManager {
    var token: String? = null

    fun saveToken(newToken: String) {
        token = newToken
    }

    fun clearToken() {
        token = null
    }

    fun hasToken(): Boolean = token != null
}