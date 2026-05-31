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

    private val _usernameError = MutableStateFlow<String?>(null)
    val usernameError = _usernameError.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError = _passwordError.asStateFlow()

    private val _passwordCheckError = MutableStateFlow<String?>(null)
    val passwordCheckError = _passwordCheckError.asStateFlow()

    fun updateUsername(newValue: String){
        _usernameError.update { null }
        _usernameState.update { newValue }
    }

    fun updateEmail(newValue: String){
        _emailError.update { null }
        _emailState.update { newValue }
    }

    fun updatePassword(newValue: String){
        _passwordState.update { newValue }

        if (_passwordCheckState.value != _passwordState.value) {
            _passwordError.update {
                "Пароли не совпадают"
            }
            _passwordCheckError.update {
                "Пароли не совпадают"
            }
        } else {
            _passwordError.update {
                null
            }
            _passwordCheckError.update {
                null
            }
        }
    }

    fun updatePasswordCheck(newValue: String) {
        _passwordCheckState.update { newValue }

        if (_passwordCheckState.value != _passwordState.value) {
            _passwordError.update {
                "Пароли не совпадают"
            }
            _passwordCheckError.update {
                "Пароли не совпадают"
            }
        } else {
            _passwordError.update {
                null
            }
            _passwordCheckError.update {
                null
            }
        }
    }

    fun updatePasswordVisible() = _passwordVisibleState.update { !_passwordVisibleState.value }

    fun updatePasswordCheckVisible() = _passwordCheckVisibleState.update { !_passwordCheckVisibleState.value }

    private fun isFormValid(): Boolean {
        val isUsernameValid = _emailState.value.isNotBlank()
        val isEmailValid = _emailState.value.isNotBlank()
        val isPasswordValid = _passwordState.value.isNotBlank()
        val isPasswordCheckValid = _passwordCheckState.value.isNotBlank()

        val equal = _passwordCheckState.value == _passwordState.value


        _usernameError.update {
            if (isUsernameValid) null else "Введите username"
        }

        _emailError.update {
            if (isEmailValid) null else "Введите почту"
        }

        if (isPasswordValid && isPasswordCheckValid) {
            if (!equal) {
                _passwordError.update {
                    "Пароли не совпадают"
                }
                _passwordCheckError.update {
                    "Пароли не совпадают"
                }
            }
        } else {
            _passwordError.update {
                if (isPasswordValid) null else "Введите пароль"
            }

            _passwordCheckError.update {
                if (isPasswordCheckValid) null else "Повторите пароль"
            }
        }


        return isUsernameValid && isEmailValid && isPasswordValid && isPasswordCheckValid && equal
    }

    private fun setError(message: String){
        _registrationState.value = RegistrationState.Error(message)
    }

    fun registration() {
        isFormValid()
    }
}

sealed class RegistrationState {
    data object Initial : RegistrationState()
    data object Loading : RegistrationState()
    data class Registered(val user: User) : RegistrationState()
    data class Error(val message: String) : RegistrationState()
}