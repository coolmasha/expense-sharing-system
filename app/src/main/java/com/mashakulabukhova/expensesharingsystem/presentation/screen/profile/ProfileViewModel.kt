package com.mashakulabukhova.expensesharingsystem.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.usecase.users.GetMyProfileUseCase
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileState>(ProfileState.Init)
    val state = _state.asStateFlow()

    init {
        getMyProfile()
    }


    fun getMyProfile() {
        viewModelScope.launch {
            getMyProfileUseCase.invoke().let { result ->
                when (result) {
                    is NetworkResult.Error -> _state.update { ProfileState.Error("") }
                    is NetworkResult.Loading -> _state.update { ProfileState.Loading }
                    is NetworkResult.Success -> _state.update { ProfileState.Success(result.result) }
                }
            }
        }
    }
}

sealed class ProfileState {
    data object Init : ProfileState()
    data object Loading : ProfileState()
    data class Success(val user: User) : ProfileState()
    data class Error(val message: String) : ProfileState()
}
