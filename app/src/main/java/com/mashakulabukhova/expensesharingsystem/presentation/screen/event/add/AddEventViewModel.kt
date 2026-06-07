package com.mashakulabukhova.expensesharingsystem.presentation.screen.event.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashakulabukhova.expensesharingsystem.R
import com.mashakulabukhova.expensesharingsystem.domain.entity.request.EventRequest
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.usecase.event.CreateEventWithoutBodyUseCase
import com.mashakulabukhova.expensesharingsystem.domain.usecase.friends.GetAllFriendsUseCase
import com.mashakulabukhova.expensesharingsystem.presentation.model.CurrencyItem
import com.mashakulabukhova.expensesharingsystem.presentation.model.IconItem
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import com.mashakulabukhova.expensesharingsystem.utils.transformDrawableToIconId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val getAllFriendsUseCase: GetAllFriendsUseCase,
    private val createEventWithoutBodyUseCase: CreateEventWithoutBodyUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AddEventState>(AddEventState.Init)
    val state = _state.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _participants = MutableStateFlow<List<User>>(emptyList())
    val participants = _participants.asStateFlow()

    private val _iconId = MutableStateFlow(R.drawable.business_event_icon)
    val iconId = _iconId.asStateFlow()

    private val _availableIcons = MutableStateFlow<List<IconItem>>(emptyList())
    val availableIcons = _availableIcons.asStateFlow()

    private val _availableCurrencies = MutableStateFlow<List<CurrencyItem>>(emptyList())
    val availableCurrencies = _availableCurrencies.asStateFlow()

    private val _showUserSelectionDialog = MutableStateFlow(false)
    val showUserSelectionDialog = _showUserSelectionDialog.asStateFlow()

    private val _availableUsers = MutableStateFlow<List<User>>(emptyList())
    val availableUsers = _availableUsers.asStateFlow()


    private val _showSuccessDialog = MutableStateFlow(false)
    val showSuccessDialog = _showSuccessDialog.asStateFlow()


    private val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog = _showErrorDialog.asStateFlow()


    init {
        loadAvailableIcons()
        loadAvailableCurrencies()
    }

    fun updateShowSuccessDialog() {
        _showSuccessDialog.update { !_showSuccessDialog.value }
    }


    fun updateShowErrorDialog() {
        _showErrorDialog.update { !_showErrorDialog.value }
    }

    fun loadAvailableUsers() {
        viewModelScope.launch {
            _state.update { AddEventState.Loading }
            getAllFriendsUseCase.invoke().let { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        _state.update { AddEventState.Error("Не удалось загрузить список друзей") }
                        _showErrorDialog.update { true }
                    }

                    is NetworkResult.Loading -> _state.update { AddEventState.Loading }
                    is NetworkResult.Success -> {
                        _availableUsers.update { result.result }
                        _state.update { AddEventState.Init }
                    }
                }
            }
        }
    }

    fun toggleUserSelectionDialog(show: Boolean) {
        _showUserSelectionDialog.update { show }
        if (show) {
            loadAvailableUsers()
        }
    }

    private fun loadAvailableIcons() {
        val icons = listOf(
            IconItem(R.drawable.business_event_icon, "Бизнес"),
            IconItem(R.drawable.restaurant_event_icon, "Ресторан"),
            IconItem(R.drawable.hike_event_icon, "Поход"),
            IconItem(R.drawable.trip_event_icon, "Путешествие"),
            IconItem(R.drawable.bar_event_icon, "Бар"),
            IconItem(R.drawable.birthday_event_icon, "День рождения"),
            IconItem(R.drawable.cinema_event_icon, "Кино"),
            IconItem(R.drawable.coffee_event_icon, "Кофе"),
            IconItem(R.drawable.favorite_event_icon, "Избранное"),
            IconItem(R.drawable.photoshoot_event_icon, "Фотосессия"),
            IconItem(R.drawable.relaxation_event_icon, "Релаксация"),
            IconItem(R.drawable.shopping, "Шоппинг")
        )
        _availableIcons.update { icons }
    }

    private fun loadAvailableCurrencies() {
        val currencies = listOf(
            CurrencyItem("RUB", "Рубль", "₽"),
            CurrencyItem("CNY", "Юань", "¥"),
            CurrencyItem("USD", "Доллар", "$"),
        )
        _availableCurrencies.update { currencies }
    }

    fun createEvent() {
        val body = EventRequest(
            title = _name.value,
            iconId = transformDrawableToIconId(_iconId.value),
            participants = _participants.value
        )
        viewModelScope.launch {
            _state.update { AddEventState.Loading }
            createEventWithoutBodyUseCase.invoke(body).let { result ->
                clearFields()
                when (result) {
                    is NetworkResult.Error -> {
                        _state.update { AddEventState.Error(result.message) }
                        _showErrorDialog.update { true }
                    }
                    is NetworkResult.Loading -> _state.update { AddEventState.Loading }
                    is NetworkResult.Success -> {
                        _state.update { AddEventState.Success }
                        _showSuccessDialog.update { true }
                    }
                }
            }
        }
    }

    fun isFormValid(): Boolean {
        return _name.value.isNotBlank()
    }


    fun updateName(newValue: String) {
        _name.update { newValue }
    }

    fun updateIconId(newValue: Int) {
        _iconId.update { newValue }
    }

    fun clearFields() {
        _name.update { "" }
        _iconId.update { R.drawable.business_event_icon }
        _participants.update { emptyList() }
    }

    fun addParticipant(user: User) {
        _participants.update { currentList ->
            if (!currentList.contains(user)) {
                currentList + user
            } else {
                currentList
            }
        }
    }

    fun removeParticipant(user: User) {
        _participants.update { currentList ->
            currentList.filter { it.id != user.id }
        }
    }
}

sealed class AddEventState {
    data object Init : AddEventState()
    data object Loading : AddEventState()
    data object Success : AddEventState()
    data class Error(val message: String) : AddEventState()
}
