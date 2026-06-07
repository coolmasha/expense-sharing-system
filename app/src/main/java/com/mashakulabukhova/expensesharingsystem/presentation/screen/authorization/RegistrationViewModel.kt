package com.mashakulabukhova.expensesharingsystem.presentation.screen.authorization

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashakulabukhova.expensesharingsystem.data.local.UserManager
import com.mashakulabukhova.expensesharingsystem.data.model.request.RegistrationRequest
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.usecase.authentication.RegistrationUseCase
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {

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

    fun updateUsername(newValue: String) {
        _usernameError.update { null }
        _usernameState.update { newValue }
    }

    fun updateEmail(newValue: String) {
        _emailState.update { newValue }

        if (!Patterns.EMAIL_ADDRESS.matcher(_emailState.value.trim()).matches()) {
            _emailError.update {
                "Введите корректный email"
            }
        } else {
            _emailError.update { null }
        }
    }

    fun updatePassword(newValue: String) {
        _passwordState.update { newValue }

        if (_passwordCheckState.value != _passwordState.value) {
            _passwordError.update {
                "Пароли не совпадают"
            }
            _passwordCheckError.update {
                "Пароли не совпадают"
            }
        } else if (_passwordCheckState.value.length < 6) {
            _passwordError.update {
                "Пароль должен содержать 6 и более символов"
            }
            _passwordCheckError.update {
                "Пароль должен содержать 6 и более символов"
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
        } else if (_passwordCheckState.value.length < 6) {
            _passwordError.update {
                "Пароль должен содержать 6 и более символов"
            }
            _passwordCheckError.update {
                "Пароль должен содержать 6 и более символов"
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

    fun updatePasswordCheckVisible() =
        _passwordCheckVisibleState.update { !_passwordCheckVisibleState.value }

    private fun isFormValid(): Boolean {
        val isUsernameValid = _emailState.value.isNotBlank()
        val isEmailValid = _emailState.value.isNotBlank()
        val isEmailMatch = Patterns.EMAIL_ADDRESS.matcher(_emailState.value.trim()).matches()
        val isPasswordValid = _passwordState.value.isNotBlank()
        val isPasswordCheckValid = _passwordCheckState.value.isNotBlank()

        val equal = _passwordCheckState.value == _passwordState.value

        val lengthPassword = _passwordCheckState.value.length >= 6


        _usernameError.update {
            if (isUsernameValid) null else "Введите имя пользователя"
        }

        _emailError.update {
            if (isEmailValid) {
                if (isEmailMatch) {
                    null
                } else {
                    "Введите корректный email"
                }
            } else {
                "Введите почту"
            }
        }

        if (isPasswordValid && isPasswordCheckValid) {
            if (!equal) {
                _passwordError.update {
                    "Пароли не совпадают"
                }
                _passwordCheckError.update {
                    "Пароли не совпадают"
                }
            } else if (!lengthPassword) {
                _passwordError.update {
                    "Пароль должен содержать 6 и более символов"
                }
                _passwordCheckError.update {
                    "Пароль должен содержать 6 и более символов"
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


        return isUsernameValid && isEmailValid && isEmailMatch && isPasswordValid && isPasswordCheckValid && equal && lengthPassword
    }

    private fun setError(message: String) {
        _registrationState.value = RegistrationState.Error(message)
    }

    fun registration() {
        if (!isFormValid()) return

        val registrationRequest = RegistrationRequest(
            username = _usernameState.value.trim(),
            name = _usernameState.value.trim(),
            email = _emailState.value.trim(),
            password = _passwordState.value.trim(),
            password2 = _passwordState.value.trim()
        )

        viewModelScope.launch {
            _registrationState.value = RegistrationState.Loading
            val result = registrationUseCase.invoke(registrationRequest)
            Log.d("LoginViewModel", "Result: $result")
            processNetworkResult(result)
        }
    }

    private fun processNetworkResult(networkResult: NetworkResult<User>) {
        when (networkResult) {
            is NetworkResult.Error -> {
                _registrationState.value = RegistrationState.Error("Не удалось зарегистрироваться. Попробуйте позже")
            }

            is NetworkResult.Loading -> {
                _registrationState.value = RegistrationState.Loading
            }

            is NetworkResult.Success -> {
                _registrationState.value = RegistrationState.Registered(networkResult.result)
                UserManager.login(userCurrent = networkResult.result)
            }
        }
    }
}

sealed class RegistrationState {
    data object Initial : RegistrationState()
    data object Loading : RegistrationState()
    data class Registered(val user: User) : RegistrationState()
    data class Error(val message: String) : RegistrationState()
}