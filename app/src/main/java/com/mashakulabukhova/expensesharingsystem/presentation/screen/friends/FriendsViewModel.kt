package com.mashakulabukhova.expensesharingsystem.presentation.screen.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashakulabukhova.expensesharingsystem.data.remote.model.request.FriendRequest
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.usecase.friends.DeleteFriendUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.friends.GetAllFriendsUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.friendship.AcceptFriendshipUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.friendship.CancelFriendshipUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.friendship.GetIncomingFriendshipUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.friendship.GetOutgoingFriendshipUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.friendship.SendFriendshipUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.friendship.RejectFriendshipUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.users.SearchUserUseCase
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val getAllFriendsUseCase: GetAllFriendsUseCase,
    private val searchUserUseCase: SearchUserUseCase,
    private val deleteFriendUseCase: DeleteFriendUseCase,

    private val getIncomingFriendshipUseCase: GetIncomingFriendshipUseCase,
    private val getOutgoingFriendshipUseCase: GetOutgoingFriendshipUseCase,

    private val sendFriendshipUseCase: SendFriendshipUseCase,
    private val acceptFriendshipUseCase: AcceptFriendshipUseCase,
    private val rejectFriendshipUseCase: RejectFriendshipUseCase,
    private val cancelFriendshipUseCase: CancelFriendshipUseCase

) : ViewModel() {

    private val _state = MutableStateFlow<FriendsState>(FriendsState.Loading)
    val state = _state.asStateFlow()

    private val _actionState = MutableStateFlow<ActionState>(ActionState.Init)
    val actionState = _actionState.asStateFlow()

    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList = _userList.asStateFlow()

    private val _friendList = MutableStateFlow<List<User>>(emptyList())
    val friendList = _friendList.asStateFlow()

    private val _incomingRequests = MutableStateFlow<List<User>>(emptyList())
    val incomingRequests = _incomingRequests.asStateFlow()

    private val _sentRequests = MutableStateFlow<List<User>>(emptyList())
    val sentRequests = _sentRequests.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val isSearching = _searchQuery.map { it.isNotBlank() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        _actionState.update { ActionState.Init }
        getAllFriends()
    }

    fun onSearchQueryUpdate(query: String) {
        _searchQuery.update { query }
    }

    fun onSearchTriggered() {
        val username = _searchQuery.value
        if (username.isNotBlank()) {
            _actionState.update { ActionState.Search }

            viewModelScope.launch {
                _state.update { FriendsState.Loading }

                searchUserUseCase.invoke(username = username).let { result ->
                    when (result) {
                        is NetworkResult.Error -> _state.update { FriendsState.Error("Не получилось загрузить пользователей. Попробуйте позже") }
                        is NetworkResult.Loading -> _state.update { FriendsState.Loading }
                        is NetworkResult.Success -> {
                            _userList.update { result.result }
                            _state.update { FriendsState.Success }
                        }
                    }
                }
            }
        }
    }

    fun clearSearch() {
        _searchQuery.update { "" }
        _actionState.update { ActionState.Init }
        getAllFriends()
    }

    fun getAllFriends() {
        viewModelScope.launch {
            _state.update { FriendsState.Loading }

//            getAllUsersUseCase.invoke().let { result ->
//                when (result) {
//                    is NetworkResult.Error -> _state.update { FriendsState.Error("Не получилось загрузить друзей. Попробуйте позже") }
//                    is NetworkResult.Loading -> _state.update { FriendsState.Loading }
//                    is NetworkResult.Success -> {
//                        _friendList.update { result.result }
//                        _state.update { FriendsState.Success }
//                    }
//                }
//            }

            getAllFriendsUseCase.invoke().let { result ->
                when (result) {
                    is NetworkResult.Error -> _state.update { FriendsState.Error("Не получилось загрузить друзей. Попробуйте позже") }
                    is NetworkResult.Loading -> _state.update { FriendsState.Loading }
                    is NetworkResult.Success -> {
                        _friendList.update { result.result }
                        _state.update { FriendsState.Success }
                    }
                }
            }
        }
    }

    fun getIncomingRequests() {
        viewModelScope.launch {
            _state.update { FriendsState.Loading }

            getIncomingFriendshipUseCase.invoke().let { result ->
                when (result) {
                    is NetworkResult.Error -> _state.update { FriendsState.Error("Не удалось найти входящие заявки") }
                    is NetworkResult.Loading -> _state.update { FriendsState.Loading }
                    is NetworkResult.Success -> {
                        _incomingRequests.update { result.result }
                        _state.update { FriendsState.Success }
                    }
                }
            }
        }
    }

    fun getOutgoingRequests() {
        viewModelScope.launch {
            _state.update { FriendsState.Loading }

            getOutgoingFriendshipUseCase.invoke().let { result ->
                when (result) {
                    is NetworkResult.Error -> _state.update { FriendsState.Error("Не удалось найти отправленные заявки") }
                    is NetworkResult.Loading -> _state.update { FriendsState.Loading }
                    is NetworkResult.Success -> {
                        _sentRequests.update { result.result }
                        _state.update { FriendsState.Success }
                    }
                }
            }
        }
    }

    fun deleteFriend(userId: String) {
        viewModelScope.launch {
            _state.update { FriendsState.Loading }

            deleteFriendUseCase.invoke(userId).let { result ->
                when (result) {
                    is NetworkResult.Error -> _state.update { FriendsState.Error("Не удалось удалить друга из друзей") }
                    is NetworkResult.Loading -> _state.update { FriendsState.Loading }
                    is NetworkResult.Success -> {
                        _friendList.update { currentList ->
                            currentList.filter { it.id != userId }
                        }
                        _state.update { FriendsState.Success }
                    }
                }
            }
        }
    }

    fun sendFriendshipRequest(userId: String) {
        val body = FriendRequest(
            toUserId = userId
        )
        viewModelScope.launch {
            _state.update { FriendsState.Loading }

            sendFriendshipUseCase.invoke(body).let { result ->
                when (result) {
                    is NetworkResult.Error -> _state.update { FriendsState.Error("Не удалось отправить заявку в друзья") }
                    is NetworkResult.Loading -> _state.update { FriendsState.Loading }
                    is NetworkResult.Success -> {
//                        _sentRequests.update { currentList ->
//                            currentList + result.result
//                        }
                        _userList.update { currentList ->
                            currentList.filter { it.id != userId }
                        }
                        _state.update { FriendsState.Success }
                    }
                }
            }
        }
    }


    fun acceptFriendshipRequest(userId: String) {
        viewModelScope.launch {
            _state.update { FriendsState.Loading }

            acceptFriendshipUseCase.invoke(userId).let { result ->
                when (result) {
                    is NetworkResult.Error -> _state.update { FriendsState.Error("Не удалось принять заявку в друзья") }
                    is NetworkResult.Loading -> _state.update { FriendsState.Loading }
                    is NetworkResult.Success -> {
                        _incomingRequests.update { currentList ->
                            currentList.filter { it.id != userId }
                        }
                        _state.update { FriendsState.Success }
                    }
                }
            }
        }
    }

    fun rejectFriendshipRequest(userId: String) {
        viewModelScope.launch {
            _state.update { FriendsState.Loading }

            rejectFriendshipUseCase.invoke(userId).let { result ->
                when (result) {
                    is NetworkResult.Error -> _state.update { FriendsState.Error("Не удалось отклонить заявку в друзья") }
                    is NetworkResult.Loading -> _state.update { FriendsState.Loading }
                    is NetworkResult.Success -> {
                        _incomingRequests.update { currentList ->
                            currentList.filter { it.id != userId }
                        }
                        _state.update { FriendsState.Success }
                    }
                }
            }
        }
    }

    fun cancelFriendshipRequest(userId: String) {
        viewModelScope.launch {
            _state.update { FriendsState.Loading }

            cancelFriendshipUseCase.invoke(userId).let { result ->
                when (result) {
                    is NetworkResult.Error -> _state.update { FriendsState.Error("Не удалось отменить отправленную заявку") }
                    is NetworkResult.Loading -> _state.update { FriendsState.Loading }
                    is NetworkResult.Success -> {
                        _sentRequests.update { currentList ->
                            currentList.filter { it.id != userId }
                        }
                        _state.update { FriendsState.Success }
                    }
                }
            }
        }
    }

}


sealed class FriendsState {
    data object Loading : FriendsState()
    data object Success : FriendsState()
    data class Error(val message: String) : FriendsState()
}

sealed class ActionState {
    data object Search : ActionState()
    data object Init : ActionState()
}