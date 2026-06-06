package com.mashakulabukhova.expensesharingsystem.presentation.screen.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.usecase.friends.GetAllFriendsUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.friends.GetFriendUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.users.SearchUserUseCase
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendProfileViewModel @Inject constructor(
    private val getFriendUseCase: GetFriendUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FriendState>(FriendState.Loading)
    val state = _state.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    fun getUserProfile(userId: String) {
        viewModelScope.launch {
            _state.update { FriendState.Loading }

            getFriendUseCase.invoke(userId = userId).let { result ->
                when (result) {
                    is NetworkResult.Error -> _state.update { FriendState.Error("Не удалось загрузить профиль друга") }
                    is NetworkResult.Loading -> _state.update { FriendState.Loading }
                    is NetworkResult.Success -> {
                        _user.update { result.result }
                        _state.update { FriendState.Success }
                    }
                }
            }
        }
    }

}

sealed class FriendState {
    data object Loading : FriendState()
    data object Success : FriendState()
    data class Error(val message: String) : FriendState()
}