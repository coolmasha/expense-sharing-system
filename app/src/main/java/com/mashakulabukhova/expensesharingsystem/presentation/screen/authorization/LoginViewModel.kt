package com.mashakulabukhova.expensesharingsystem.presentation.screen.authorization

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashakulabukhova.expensesharingsystem.data.remote.model.LoginRequest
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.usecase.authentication.LoginUseCase
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.String

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Initial)
    val state = _state.asStateFlow()

    private val _emailState = MutableStateFlow("")
    val emailState = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow("")
    val passwordState = _passwordState.asStateFlow()

    private val _passwordVisibleState = MutableStateFlow(false)
    val passwordVisibleState = _passwordVisibleState.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError = _passwordError.asStateFlow()

    fun updateEmail(newValue: String) {
        _emailError.update { null }
        _emailState.update { newValue }
    }

    fun updatePassword(newValue: String) {
        _passwordError.update { null }
        _passwordState.update { newValue }
    }

    fun updatePasswordVisible() = _passwordVisibleState.update { !_passwordVisibleState.value }

    fun login() {
        if (!isFormValid()) return

        val loginRequest = LoginRequest(_emailState.value.trim(), _passwordState.value.trim())
        Log.d("LoginViewModel", "Login: $loginRequest")

        viewModelScope.launch {
            _state.value = LoginState.Loading
            val result = loginUseCase.invoke(loginRequest)
            Log.d("LoginViewModel", "Result: $result")
            processNetworkResult(result)
        }
    }

    private fun clearFields() {
        _emailState.update { "" }
        _passwordState.update { "" }
        _passwordVisibleState.update { false }
    }

    private fun isFormValid(): Boolean {
        val isEmailValid = _emailState.value.isNotBlank()
        val isPasswordValid = _passwordState.value.isNotBlank()

        _emailError.update {
            if (isEmailValid) null else "Введите почту или username"
        }

        _passwordError.update {
            if (isPasswordValid) null else "Введите пароль"
        }

        return isEmailValid && isPasswordValid
    }

    private fun processNetworkResult(networkResult: NetworkResult<User>) {
        when (networkResult) {
            is NetworkResult.Error -> {
                setError(networkResult.message)
            }

            is NetworkResult.Loading -> {
                _state.value = LoginState.Loading
            }

            is NetworkResult.Success -> {
                _state.value = LoginState.Authorized(networkResult.result)
            }
        }
    }

    private fun setError(message: String) {
        _state.update { LoginState.Error(message) }
    }
}

sealed class LoginState {
    data object Initial : LoginState()
    data object Loading : LoginState()
    data class Authorized(val user: User) : LoginState()
    data class Error(val message: String) : LoginState()
}