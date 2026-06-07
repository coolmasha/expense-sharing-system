package com.mashakulabukhova.expensesharingsystem.presentation.screen.event.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashakulabukhova.expensesharingsystem.data.local.UserManager
import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.entity.Expense
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.usecase.event.GetEventUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.event.GetParticipantsUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.expense.DeleteExpenseUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.expense.GetAllExpensesUseCase
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
    private val getParticipantsUseCase: GetParticipantsUseCase,
    private val getAllExpensesUseCase: GetAllExpensesUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<EventDetailState>(EventDetailState.Init)
    val state = _state.asStateFlow()

    private val _event = MutableStateFlow<Event?>(null)
    val event = _event.asStateFlow()

    private val _participants = MutableStateFlow<List<User>>(emptyList())
    val participants = _participants.asStateFlow()

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses = _expenses.asStateFlow()

    private val _myExpenseAmount = MutableStateFlow<Long>(0L)
    val myExpenseAmount = _myExpenseAmount.asStateFlow()

    private val _allExpenseAmount = MutableStateFlow<Long>(0L)
    val allExpenseAmount = _allExpenseAmount.asStateFlow()

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

    fun calculateExpenseAmount() {
        calculateMyExpenseAmount()
        calculateAllExpenseAmount()
    }


    fun calculateMyExpenseAmount(){
        for (expense in _expenses.value) {
            if (expense.payer.id == UserManager.currentUser.id) {
                _myExpenseAmount.update { it + expense.totalCost }
            }
        }
    }

    fun calculateAllExpenseAmount(){
        for (expense in _expenses.value) {
            _allExpenseAmount.update { it + expense.totalCost }
        }
    }

    fun getEventRequest(eventId: String) {
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

    fun getParticipantsRequest(eventId: String) {
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

    fun getAllExpensesRequest(eventId: String) {
        viewModelScope.launch {
            Log.d("Expenses", "Here")
            _state.update { EventDetailState.Loading }
            getAllExpensesUseCase.invoke(eventId).let { result ->
                when (result) {
                    is NetworkResult.Error -> _state.update { EventDetailState.Error("Не удалось загрузить расходы в событии") }
                    is NetworkResult.Loading -> _state.update { EventDetailState.Loading }
                    is NetworkResult.Success -> {
                        Log.d("Expenses", "${result.result}")
                        _expenses.update { result.result }
                        _state.update { EventDetailState.Init }
                        calculateExpenseAmount()
                    }
                }
            }
        }
    }

    fun deleteExpense(expenseId: String) {
        viewModelScope.launch {
            _state.update { EventDetailState.Loading }
            deleteExpenseUseCase.invoke(expenseId).let { result ->
                when (result) {
                    is NetworkResult.Error -> _state.update { EventDetailState.Error("Не удалось удалить расход в событии") }
                    is NetworkResult.Loading -> _state.update { EventDetailState.Loading }
                    is NetworkResult.Success -> {
                        _expenses.update { currentList ->
                            currentList.filter { it.id != expenseId }
                        }
                        _state.update { EventDetailState.Init }
                        calculateExpenseAmount()
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
