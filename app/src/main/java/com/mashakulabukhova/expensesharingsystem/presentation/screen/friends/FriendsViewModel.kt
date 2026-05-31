package com.mashakulabukhova.expensesharingsystem.presentation.screen.friends

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.usecase.friends.GetAllFriendsUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.friends.SearchUserUseCase
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val getAllFriendsUseCase: GetAllFriendsUseCase,
    private val searchUserUseCase: SearchUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FriendsState>(FriendsState.Loading)
    val state = _state.asStateFlow()

    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList = _userList.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val isSearching = _searchQuery.map { it.isNotBlank() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        getAllFriends()
    }

    fun onSearchQueryUpdate(query: String) {
        _searchQuery.update { query }
    }

    fun onSearchTriggered() {
        if (_searchQuery.value.isNotBlank()) {
            searchUsers()
        }
    }

    fun clearSearch() {
        _searchQuery.update { "" }
        getAllFriends()
    }

    fun getAllFriends() {
        viewModelScope.launch {
            _state.update { FriendsState.Loading }
            getAllFriendsUseCase.invoke(userId = "UserManager.currentUser.user_id").let {
                processNetworkResult(it, "Не получилось загрузить друзей. Попробуйте позже")
            }
        }
    }

    fun searchUsers() {
        viewModelScope.launch {
            _state.update { FriendsState.Loading }
            searchUserUseCase.invoke(username = "Username").let {
                processNetworkResult(it, "Не получилось загрузить пользователей. Попробуйте позже")
            }
        }

    }

    @OptIn(FlowPreview::class)
    private fun searchUsersDebounced() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500) // Ждём 500мс после последнего ввода
                .filter { it.isNotBlank() }
                .collect { query ->
                    searchUserUseCase.invoke(username = query).let {
                        processNetworkResult(it, "Не получилось загрузить пользователей. Попробуйте позже")
                    }
                }
        }
    }

    private fun processNetworkResult(result: NetworkResult<List<User>>, message: String) {
        when (result) {
            is NetworkResult.Error -> _state.update { FriendsState.Error(message) }
            is NetworkResult.Loading -> _state.update { FriendsState.Loading }
            is NetworkResult.Success -> {
                _userList.update { result.result }
                _state.update { FriendsState.Success }
            }
        }
    }
}


sealed class FriendsState {
    data object Loading : FriendsState()
    data object Success : FriendsState()
    data class Error(val message: String) : FriendsState()
}