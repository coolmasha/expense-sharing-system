package com.mashakulabukhova.expensesharingsystem.utils

import com.mashakulabukhova.expensesharingsystem.domain.entity.User

//sealed class NetworkResult {
//    data object Loading : NetworkResult()
//    data class Success(val result: Any) : NetworkResult()
//    data class Error(val code: Int? = null, val message: String) : NetworkResult()
//}

sealed class NetworkResult<out T> {
    data object Loading : NetworkResult<Nothing>()
    data class Success<T>(val result: T) : NetworkResult<T>()
    data class Error(val code: Int? = null, val message: String) : NetworkResult<Nothing>()
}