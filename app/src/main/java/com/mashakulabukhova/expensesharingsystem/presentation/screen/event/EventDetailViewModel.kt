package com.mashakulabukhova.expensesharingsystem.presentation.screen.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.usecase.event.GetEventUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.event.GetParticipantsUseCase
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val getEventUseCase: GetEventUseCase,
    private val getParticipantsUseCase: GetParticipantsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<EventDetailState>(EventDetailState.Init)
    val state = _state.asStateFlow()

    private val _event = MutableStateFlow<Event?>(null)
    val event = _event.asStateFlow()

    private val _participants = MutableStateFlow<List<User>>(emptyList())
    val participants = _participants.asStateFlow()

    private val _buttonState = MutableStateFlow<ButtonState>(ButtonState.Expense)
    val buttonState = _buttonState.asStateFlow()

    fun buttonExpense() {
        _buttonState.update { ButtonState.Expense }
    }

    fun buttonBalance() {
        _buttonState.update { ButtonState.Balance }
    }

    fun buttonParticipants() {
        _buttonState.update { ButtonState.Participants }
    }

    fun getEvent(eventId: String) {
        viewModelScope.launch {
            _state.update { EventDetailState.Loading }
            getEventUseCase.invoke(eventId).let { result ->
                when (result) {
                    is NetworkResult.Error -> _state.update { EventDetailState.Error("Не удалось загрузить событие") }
                    is NetworkResult.Loading -> _state.update { EventDetailState.Loading }
                    is NetworkResult.Success -> {
                        _state.update { EventDetailState.Init }
                        _event.update { result.result }
                    }
                }
            }
        }
    }

    fun getParticipants(eventId: String) {
        viewModelScope.launch {
            _state.update { EventDetailState.Loading }
            getParticipantsUseCase.invoke(eventId).let { result ->
                when (result) {
                    is NetworkResult.Error -> _state.update { EventDetailState.Error("Не удалось загрузить участников события") }
                    is NetworkResult.Loading -> _state.update { EventDetailState.Loading }
                    is NetworkResult.Success -> {
                        _participants.update { result.result }
                        _state.update { EventDetailState.Init }
                    }
                }
            }
        }
    }
}

sealed class EventDetailState {
    data object Init : EventDetailState()
    data object Loading : EventDetailState()
    data object Success : EventDetailState()
    data class Error(val message: String) : EventDetailState()
}

sealed class ButtonState {
    data object Balance : ButtonState()
    data object Expense : ButtonState()
    data object Participants : ButtonState()
}
