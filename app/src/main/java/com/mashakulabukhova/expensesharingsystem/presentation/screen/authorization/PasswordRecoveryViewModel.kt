package com.mashakulabukhova.expensesharingsystem.presentation.screen.authorization

import androidx.lifecycle.ViewModel
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.usecase.authentication.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class PasswordRecoveryViewModel @Inject constructor(
    //usecase
) : ViewModel() {

    private val _state = MutableStateFlow<PasswordRecoveryState>(PasswordRecoveryState.Initial)
    val state = _state.asStateFlow()

    private val _emailState = MutableStateFlow("")
    val emailState = _emailState.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError = _emailError.asStateFlow()

    fun updateEmail(value: String) {
        _emailError.update { null }
        _emailState.update { value }
    }

    private fun isFormValid(): Boolean {
        val isEmailValid = _emailState.value.isNotBlank()

        _emailError.update {
            if (isEmailValid) null else "Введите почту"
        }

        return isEmailValid
    }

    fun recoverPassword() {
        isFormValid()
    }
}

sealed class PasswordRecoveryState {
    data object Initial : PasswordRecoveryState()
    data object Loading : PasswordRecoveryState()
    data object Success : PasswordRecoveryState()
    data class Error(val message: String) : PasswordRecoveryState()
}