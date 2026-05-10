package com.mashakulabukhova.expensesharingsystem.presentation.screen.authorization

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegistrationViewModel : ViewModel() {

    private val _registrationState: MutableStateFlow<RegistrationState> =
        MutableStateFlow(RegistrationState.Initial)
    val registrationState = _registrationState.asStateFlow()

    private val _usernameState: MutableStateFlow<String> = MutableStateFlow("")
    val usernameState = _usernameState.asStateFlow()

    private val _emailState: MutableStateFlow<String> = MutableStateFlow("")
    val emailState = _emailState.asStateFlow()

    private val _passwordState: MutableStateFlow<String> = MutableStateFlow("")
    val passwordState = _passwordState.asStateFlow()

    private val _passwordCheckState: MutableStateFlow<String> = MutableStateFlow("")
    val passwordCheckState = _passwordCheckState.asStateFlow()

    private val _passwordVisibleState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val passwordVisibleState = _passwordVisibleState.asStateFlow()

    private val _passwordCheckVisibleState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val passwordCheckVisibleState = _passwordCheckVisibleState.asStateFlow()

    fun updateUsername(newValue: String) = _usernameState.update { newValue }

    fun updateEmail(newValue: String) = _emailState.update { newValue }

    fun updatePassword(newValue: String) = _passwordState.update { newValue }

    fun updatePasswordCheck(newValue: String) {
        _passwordCheckState.update { newValue }

        Log.d(
            "RegistrationViewModel",
            "Comparison: ${_passwordState.value} - ${_passwordCheckState.value}"
        )
        if (_passwordCheckState.value != _passwordState.value) {
            Log.d("RegistrationViewModel", "Comparison: Eror")
            setError("Пароли не совпадают")
        } else {
            Log.d("RegistrationViewModel", "Comparison: Initial")
            _registrationState.value = RegistrationState.Initial
        }
    }

    fun updatePasswordVisible() = _passwordVisibleState.update { !_passwordVisibleState.value }

    fun updatePasswordCheckVisible() = _passwordCheckVisibleState.update { !_passwordCheckVisibleState.value }

    private fun isFormValid(): Boolean {
        return when {
            _usernameState.value.isBlank() -> {
                setError("Введите username")
                false
            }
            _emailState.value.isBlank() -> {
                setError("Введите почту")
                false
            }
            _passwordState.value.isBlank() -> {
                setError("Введите пароль")
                false
            }
            _passwordCheckState.value.isBlank() -> {
                setError("Повторите пароль")
                false
            }
            _passwordCheckState.value != _passwordState.value -> {
                setError("Пароли не совпадают")
                false
            }
            else -> true
        }
    }

    private fun setError(message: String){
        _registrationState.value = RegistrationState.Error(message)
    }
}

sealed class RegistrationState {
    data object Initial : RegistrationState()
    data object Loading : RegistrationState()
    data class Registered(val user: User) : RegistrationState()
    data class Error(val message: String) : RegistrationState()
}