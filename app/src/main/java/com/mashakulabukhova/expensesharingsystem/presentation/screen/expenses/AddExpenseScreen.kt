package com.mashakulabukhova.expensesharingsystem.presentation.screen.expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mashakulabukhova.expensesharingsystem.data.local.UserManager
import com.mashakulabukhova.expensesharingsystem.presentation.component.PrimaryGradient
import com.mashakulabukhova.expensesharingsystem.presentation.component.SecondaryTextField
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun AddExpenseScreen(
    modifier: Modifier = Modifier,
    viewModel: AddExpenseViewModel = hiltViewModel(),
    eventId: String,
    onBackClick: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getParticipantsRequest(eventId)
    }

    val title by viewModel.title.collectAsState()
    val totalCost by viewModel.totalCost.collectAsState()

    val currency by viewModel.currency.collectAsState()
    var currencyExpanded by remember { mutableStateOf(false) }
    val availableCurrencies by viewModel.availableCurrencies.collectAsState()
    val selectedCurrency = availableCurrencies.find { it.code == currency }

    val payer by viewModel.payer.collectAsState()
    val availableUsers by viewModel.availableUsers.collectAsState()
    var payerExpanded by remember { mutableStateOf(false) }
    val selectedPayer = availableUsers.find { it.id == payer.id }

    val pickedDate by viewModel.pickedDate.collectAsState()

    var showDatePicker by remember { mutableStateOf(false) }
    var pickedDateMillis by remember {
        mutableStateOf(System.currentTimeMillis())
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = pickedDateMillis
    )

    val formattedDate by remember(pickedDate) {
        derivedStateOf {
            DateTimeFormatter.ofPattern("dd.MM.yyyy").format(pickedDate)
        }
    }

    PrimaryGradient(modifier = modifier)
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Новый расход",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Название",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium
        )
        SecondaryTextField(
            value = title,
            onValueChange = { viewModel.updateTitle(it) },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(20.dp),
                    clip = true
                ),
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = {
                Text(
                    text = "Название",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        )

        Text(
            text = "Сумма",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            SecondaryTextField(
                value = totalCost,
                onValueChange = { viewModel.updateTotalCost(it) },
                modifier = Modifier
                    .weight(0.7f)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(20.dp),
                        clip = true
                    ),
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = {
                    Text(
                        text = "0",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onAny = {})
            )
            Box(
                modifier = Modifier
                    .weight(0.2f)
            ) {
                Button(
                    onClick = { currencyExpanded = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = selectedCurrency?.symbol ?: "₽",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .height(34.dp),
                        textAlign = TextAlign.Center
                    )
                }

                DropdownMenu(
                    expanded = currencyExpanded,
                    onDismissRequest = { currencyExpanded = false },
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.background
                        )
                        .width(88.dp)
                ) {
                    availableCurrencies.forEach { currency ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = currency.name,
                                        color = MaterialTheme.colorScheme.primary,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            },
                            onClick = {
                                viewModel.updateCurrency(currency.code)
                                currencyExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Оплачено",
                    modifier = Modifier.padding(start = 16.dp, bottom = 4.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = { payerExpanded = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Text(
                            text = selectedPayer?.username ?: UserManager.currentUser.username,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    DropdownMenu(
                        expanded = payerExpanded,
                        onDismissRequest = { payerExpanded = false },
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.background
                            )
                            .width(186.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 200.dp)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            availableUsers.forEach { user ->
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            if (user.id == UserManager.currentUser.id) {
                                                Text(
                                                    text = user.username + " (Я)",
                                                    color = MaterialTheme.colorScheme.primary,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            } else {
                                                Text(
                                                    text = user.username,
                                                    color = MaterialTheme.colorScheme.primary,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }
                                        }
                                    },
                                    onClick = {
                                        viewModel.updatePayer(user)
                                        payerExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Дата",
                    modifier = Modifier.padding(start = 16.dp, bottom = 4.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium
                )

                Button(
                    onClick = { showDatePicker = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = formattedDate,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Выбрать дату",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        Text(
            text = "Разделить",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) { }
        }

        AddExpense(
            isValid = viewModel.isValidFields(),
            onAddExpense = { viewModel.createExpense(eventId) },
            onCancelClick = onBackClick
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            val selectedDate = Instant.ofEpochMilli(it)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            viewModel.updatePickedDate(selectedDate)
                        }
                        showDatePicker = false
                    },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Text("Выбрать дату")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Text("Отмена")
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.Black,
            )
        )
        {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background,                          // фон всего диалога
                    titleContentColor = MaterialTheme.colorScheme.onBackground,           // цвет заголовка
                    headlineContentColor = MaterialTheme.colorScheme.onBackground,          // цвет выбранной даты в шапке
                    weekdayContentColor = MaterialTheme.colorScheme.onBackground,           // цвет дней недели (Пн, Вт...)
                    subheadContentColor = MaterialTheme.colorScheme.onBackground,           // цвет подзаголовка

                    // === НАВИГАЦИЯ ===
                    navigationContentColor = MaterialTheme.colorScheme.primary,                 // цвет стрелок переключения

                    // === ЦВЕТА ГОДА ===
                    yearContentColor = MaterialTheme.colorScheme.onBackground,                     // цвет года в режиме выбора
                    disabledYearContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f), // неактивный год
                    currentYearContentColor = MaterialTheme.colorScheme.primary,                // цвет текущего года
                    selectedYearContentColor = MaterialTheme.colorScheme.onPrimary,             // цвет текста выбранного года
                    disabledSelectedYearContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = 0.38f
                    ), // неактивный выбранный год
                    selectedYearContainerColor = MaterialTheme.colorScheme.primary,             // фон выбранного года
                    disabledSelectedYearContainerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = 0.38f
                    ), // фон неактивного выбранного года

                    // === ЦВЕТА ДНЕЙ ===
                    dayContentColor = MaterialTheme.colorScheme.onSurface,                      // цвет чисел дней
                    disabledDayContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f), // неактивные дни
                    selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,              // цвет текста выбранного дня
                    disabledSelectedDayContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = 0.38f
                    ), // неактивный выбранный день
                    selectedDayContainerColor = MaterialTheme.colorScheme.primary,              // фон выбранного дня
                    disabledSelectedDayContainerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = 0.38f
                    ), // фон неактивного выбранного дня

                    // === СЕГОДНЯШНИЙ ДЕНЬ ===
                    todayContentColor = MaterialTheme.colorScheme.primary,                      // цвет текста сегодняшнего дня
                    todayDateBorderColor = MaterialTheme.colorScheme.primary,                   // цвет рамки сегодняшнего дня

                    // === ДИАПАЗОН ДАТ ===
                    dayInSelectionRangeContentColor = MaterialTheme.colorScheme.primary,        // цвет дней в диапазоне
                    dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.primaryContainer, // фон дней в диапазоне

                    // === РАЗДЕЛИТЕЛИ ===
                    dividerColor = MaterialTheme.colorScheme.outlineVariant,
                ),
                title = null
            )
        }
    }
}

@Composable
fun AddExpense(
    isValid: Boolean,
    onAddExpense: () -> Unit,
    onCancelClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)

    ) {
        Button(
            onClick = onCancelClick,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = (MaterialTheme.colorScheme.tertiary),
                contentColor = MaterialTheme.colorScheme.onTertiary
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp
            )
        ) {
            Text(
                text = "Отменить",
                color = MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.labelMedium
            )
        }
        Button(
            onClick = onAddExpense,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            enabled = isValid,
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = (MaterialTheme.colorScheme.primary),
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.8f),
                disabledContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp
            )
        ) {
            Text(
                text = "Добавить",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun AddFriendToExpense(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = (MaterialTheme.colorScheme.primaryContainer),
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Person",
                modifier = Modifier
                    .size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = "Добавить участника",
                modifier = Modifier,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
