package com.mashakulabukhova.expensesharingsystem.presentation.screen.expenses

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashakulabukhova.expensesharingsystem.data.local.UserManager
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.entity.request.ExpenseRequest
import com.mashakulabukhova.expensesharingsystem.domain.usecase.event.GetParticipantsUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.expense.CreateExpenseUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.friends.GetAllFriendsUseCase
import com.mashakulabukhova.expensesharingsystem.presentation.model.CurrencyItem
import com.mashakulabukhova.expensesharingsystem.presentation.screen.event.add.AddEventState
import com.mashakulabukhova.expensesharingsystem.presentation.screen.event.detail.EventDetailState
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

private val testUser = User(id = "1", username = "alex_kuznetsov", email = "alex@example.com")

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val getParticipantsUseCase: GetParticipantsUseCase,
    private val createExpenseUseCase: CreateExpenseUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AddExpenseState>(AddExpenseState.Init)
    val state = _state.asStateFlow()

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _totalCost = MutableStateFlow("")
    val totalCost = _totalCost.asStateFlow()

    private val _currency = MutableStateFlow("RUB")
    val currency = _currency.asStateFlow()

    private val _payer = MutableStateFlow<User>(UserManager.currentUser)
    val payer = _payer.asStateFlow()

    private val _participants = MutableStateFlow<List<User>>(emptyList())
    val participants = _participants.asStateFlow()

    private val _availableUsers = MutableStateFlow<List<User>>(emptyList())
    val availableUsers = _availableUsers.asStateFlow()

    private val _availableCurrencies = MutableStateFlow<List<CurrencyItem>>(emptyList())
    val availableCurrencies = _availableCurrencies.asStateFlow()

    private val _pickedDate = MutableStateFlow<LocalDate>(LocalDate.now())
    val pickedDate = _pickedDate.asStateFlow()

    init {
        loadAvailableCurrencies()
    }

    fun updateTitle(newValue: String) {
        _title.update{ newValue }
    }

    fun updateTotalCost(newValue: String) {
        _totalCost.update { newValue }
    }

    fun updateCurrency(newValue: String) {
        _currency.update { newValue }
    }

    fun updatePayer(newValue: User) {
        _payer.update { newValue }
    }

    fun updatePickedDate(newValue: LocalDate) {
        _pickedDate.value = newValue
    }

    fun createExpense(eventId: String) {
        val body = ExpenseRequest(
            eventId = eventId,
            title = _title.value.trim(),
            payerId = payer.value.id,
            totalCost = _totalCost.value.toLong(),
            currency = _currency.value,
            dateOfPayment = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(_pickedDate.value),
            users = _participants.value
        )
        Log.d("Text body", "$body")
        viewModelScope.launch {
            createExpenseUseCase.invoke(body).let { result ->
                when (result) {
                    is NetworkResult.Error -> _state.update { AddExpenseState.Error(message = "Не удалось добавить расход") }
                    is NetworkResult.Loading -> _state.update { AddExpenseState.Loading }
                    is NetworkResult.Success -> {
                        clearFields()
                        _state.update { AddExpenseState.Success(message = "Новый расход добавлен!") }
                    }
                }
            }
        }
    }

    fun isValidFields(): Boolean {
        val isTitleValid = _title.value.isNotBlank()
        val isTotalCostValid = _totalCost.value.isNotBlank()
        val isCurrencyValid = _currency.value.isNotBlank()

        return isTitleValid &&
                isTotalCostValid &&
                isCurrencyValid
    }

    private fun clearFields() {
        _title.update { "" }
        _totalCost.update { "" }
        _currency.update { "" }
        _pickedDate.update { LocalDate.now() }
    }

    private fun loadAvailableCurrencies() {
        val currencies = listOf(
            CurrencyItem("RUB", "Рубль", "₽"),
            CurrencyItem("CNY", "Юань", "¥"),
            CurrencyItem("USD", "Доллар", "$"),
        )
        _availableCurrencies.update { currencies }
    }

    fun getParticipantsRequest(eventId: String) {
        viewModelScope.launch {
            _state.update { AddExpenseState.Loading }
            getParticipantsUseCase.invoke(eventId).let { result ->
                when (result) {
                    is NetworkResult.Error -> _state.update { AddExpenseState.Error("Не удалось загрузить участников события") }
                    is NetworkResult.Loading -> _state.update { AddExpenseState.Loading }
                    is NetworkResult.Success -> {
                        _availableUsers.update { result.result }
                        _state.update { AddExpenseState.Init }
                    }
                }
            }
        }
    }
}

sealed class AddExpenseState {
    data object Init : AddExpenseState()
    data object Loading : AddExpenseState()
    data class Success(val message: String) : AddExpenseState()
    data class Error(val message: String) : AddExpenseState()
}