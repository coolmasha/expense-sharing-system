package com.mashakulabukhova.expensesharingsystem.presentation.screen.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.usecase.event.GetMyEventsUseCase
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getMyEventsUseCase: GetMyEventsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<EventsState>(EventsState.Loading)

    val state = _state.asStateFlow()

    init {
        getAllEvents()
    }

    fun getAllEvents() {
        viewModelScope.launch {
            _state.update { EventsState.Loading }
            getMyEventsUseCase.invoke().let {
                processNetworkResult(it)
            }
        }
    }

    private fun processNetworkResult(result: NetworkResult<List<Event>>) {
        when (result) {
            is NetworkResult.Error -> _state.update { EventsState.Error("Не получилось загрузить список событий. Попробуйте позже") }
            is NetworkResult.Loading -> _state.update { EventsState.Loading }
            is NetworkResult.Success -> _state.update { EventsState.Success(result.result) }
        }
    }
}


sealed class EventsState {
    data object Loading : EventsState()
    data class Success(val data: List<Event>) : EventsState()
    data class Error(val message: String) : EventsState()
}