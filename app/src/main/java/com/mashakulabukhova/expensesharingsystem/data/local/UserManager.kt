package com.mashakulabukhova.expensesharingsystem.data.local

import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

//object UserManager {
//
//    private var _currentUser: String? = null
//    val currentUser: String
//        get() {
//            require(_currentUser != null) { "User must be logged in" }
//            return _currentUser!!
//        }
//
//    val currentUserOrNull: String? get() = _currentUser
//
//    private val _isLoggedIn = MutableStateFlow(false)
//    val isLoggedInState = _isLoggedIn.asStateFlow()
//
//    fun login(userCurrent: String) {
//        _currentUser = userCurrent
//        _isLoggedIn.value = true
//    }
//
//    fun logout() {
//        _currentUser = null
//        _isLoggedIn.value = false
//        TokenManager.clearToken()
//    }
//
//    fun isLoggedIn(): Boolean = _isLoggedIn.value
//}

object UserManager {

    private var _currentUser: User? = null
    val currentUser: User
        get() {
            require(_currentUser != null) { "User must be logged in" }
            return _currentUser!!
        }

    val currentUserOrNull: User? get() = _currentUser

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedInState = _isLoggedIn.asStateFlow()

    fun login(userCurrent: User) {
        _currentUser = userCurrent
        _isLoggedIn.value = true
    }

    fun logout() {
        _currentUser = null
        _isLoggedIn.value = false
        TokenManager.clearToken()
    }

    fun isLoggedIn(): Boolean = _isLoggedIn.value
}