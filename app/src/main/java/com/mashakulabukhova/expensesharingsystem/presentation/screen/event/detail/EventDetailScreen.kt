package com.mashakulabukhova.expensesharingsystem.presentation.screen.event.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mashakulabukhova.expensesharingsystem.domain.entity.Expense
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.presentation.component.ErrorMessage
import com.mashakulabukhova.expensesharingsystem.presentation.component.LoadingIndicator
import com.mashakulabukhova.expensesharingsystem.presentation.component.PrimaryGradient
import com.mashakulabukhova.expensesharingsystem.presentation.screen.event.eventlist.EventCard

@Composable
fun EventDetailScreen(
    modifier: Modifier = Modifier,
    eventId: String,
    viewModel: EventDetailViewModel = hiltViewModel(),
    onAddExpense: () -> Unit
) {

    val state = viewModel.state.collectAsState().value
    val event by viewModel.event.collectAsState()
    val participants by viewModel.participants.collectAsState()
    val expenses by viewModel.expenses.collectAsState()

    val myExpenseAmount by viewModel.myExpenseAmount.collectAsState()
    val allExpenseAmount by viewModel.allExpenseAmount.collectAsState()

    val buttonState = viewModel.buttonState.collectAsState().value


    LaunchedEffect(Unit) {
        viewModel.getEventRequest(eventId)
        viewModel.getParticipantsRequest(eventId)
        viewModel.getAllExpensesRequest(eventId)
    }

    PrimaryGradient(modifier)

    when (state) {
        is EventDetailState.Error -> {}
        EventDetailState.Init -> {}
        EventDetailState.Loading -> {
            LoadingIndicator(modifier.fillMaxSize())
        }

        EventDetailState.Success -> {}
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (event != null) {
            EventCard(
                event = event!!,
                participants.size
            )
        } else {
            ErrorMessage(
                modifier = Modifier.fillMaxWidth(),
                message = "Не удалось загрузить событие"
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = { viewModel.buttonExpense() },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = if (buttonState is ButtonState.Expense)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    else
                        MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp
                ),
                border = BorderStroke(
                    width = 4.dp,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = "Расходы",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            TextButton(
                onClick = { viewModel.buttonBalance() },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = if (buttonState is ButtonState.Balance)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    else
                        MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp
                ),
                border = BorderStroke(
                    width = 4.dp,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = "Балансы",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            TextButton(
                onClick = { viewModel.buttonParticipants() },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = if (buttonState is ButtonState.Participants)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    else
                        MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp
                ),
                border = BorderStroke(
                    width = 4.dp,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = "Участники",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        when (buttonState) {
            is ButtonState.Balance -> {
                BalancesView()
            }

            is ButtonState.Expense -> {
                ExpensesView(
                    expenses = expenses,
                    myExpenseAmount = myExpenseAmount,
                    allExpenseAmount = allExpenseAmount,
                    onExpenseDelete = {viewModel.deleteExpense(it)},
                    onAddExpense = onAddExpense
                )
            }

            is ButtonState.Participants -> {
                ParticipantsView(participants)
            }
        }

    }
}

@Composable
fun ParticipantsView(
    participants: List<User>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = participants,
            key = { user -> user.id }
        ) { user ->
            ParticipantsItem(
                participant = user
            )
        }
    }

}

@Composable
fun ExpensesView(
    expenses: List<Expense>,
    myExpenseAmount: Long,
    allExpenseAmount: Long,
    onExpenseDelete: (String) -> Unit,
    onAddExpense: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .width(176.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = (MaterialTheme.colorScheme.primaryContainer),
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Мои расходы",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium

                    )
                    Text(
                        text = "${myExpenseAmount}" + " ₽",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Card(
                modifier = Modifier
                    .width(176.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = (MaterialTheme.colorScheme.primaryContainer),
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Общие расходы",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium

                    )
                    Text(
                        text = "${allExpenseAmount}" + " ₽",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = expenses,
                key = {expense -> expense.id}
            ) { expense ->
                ExpenseItem(
                    expense = expense,
                    onExpenseDelete = onExpenseDelete
                )
            }
            item {
                AddExpenseButton(
                    addNewExpense = onAddExpense
                )
            }
        }
    }
}



@Composable
fun BalancesView() {

}


@Composable
fun AddExpenseButton(
    addNewExpense: () -> Unit
) {
    Button(
        onClick = addNewExpense,
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
                .padding(vertical = 8.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Event",
                modifier = Modifier
                    .size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Добавить расход",
                modifier = Modifier,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

