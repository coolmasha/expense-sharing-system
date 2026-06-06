package com.mashakulabukhova.expensesharingsystem.presentation.screen.event

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.mashakulabukhova.expensesharingsystem.data.local.UserManager
import com.mashakulabukhova.expensesharingsystem.di.ApiModule
import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.entity.Expense
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.presentation.component.ErrorMessage
import com.mashakulabukhova.expensesharingsystem.presentation.component.LoadingIndicator
import com.mashakulabukhova.expensesharingsystem.presentation.component.PrimaryGradient
import com.mashakulabukhova.expensesharingsystem.utils.transformIconIdToDrawable

@Composable
fun EventDetailScreen(
    modifier: Modifier = Modifier,
    eventId: String,
    viewModel: EventDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState().value
    val event by viewModel.event.collectAsState()
    val participants by viewModel.participants.collectAsState()

    val buttonState = viewModel.buttonState.collectAsState().value


    LaunchedEffect(Unit) {
        viewModel.getEvent(eventId)
        viewModel.getParticipants(eventId)
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
                ExpensesView()
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
fun ParticipantsItem(
    participant: User
) {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = (MaterialTheme.colorScheme.primaryContainer),
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Person",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
            if (participant.id == UserManager.currentUser.id) {
                Row(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = participant.username,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = " (Я)",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

            } else {
                Text(
                    text = participant.username,
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
fun ExpensesView(

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
                        text = "1880,00" + " ₽",
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
                        text = "1880,00" + " ₽",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
        LazyColumn() { }
    }

}

@Composable
fun ExpenseItem(
    expense: Expense
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = (MaterialTheme.colorScheme.primaryContainer),
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(

            ) {
                Text(
                    text = expense.title,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "оплачено" + expense.payerUsername,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Row(

            ) {
                Text(
                    text = expense.amount + " ₽",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium
                )

                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Удалить",
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun BalancesView() {

}

@Composable
fun EventCard(
    event: Event,
    userListSize: Int
) {
    Card(
        modifier = Modifier
            .width(320.dp)
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
                .wrapContentHeight()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(transformIconIdToDrawable(event.iconId)),
                contentDescription = "Category Icon",
                modifier = Modifier
                    .size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = event.title,
//                text = "event.titless dkvnjdvdw vjlvvkev evljenlkvev levnlkdmvklwev lvnkpevmdmv pdvmpVOV VNkdvmlVD",
                modifier = Modifier
                    .padding(top = 4.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 8.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Валюта:",
//                    color = MaterialTheme.colorScheme.onPrimaryContainer,
//                    textAlign = TextAlign.Center,
//                    style = MaterialTheme.typography.titleSmall
//                )
//                Text(
//                    text = event.currency,
//                    color = MaterialTheme.colorScheme.onPrimaryContainer,
//                    textAlign = TextAlign.Center,
//                    style = MaterialTheme.typography.bodyMedium
//                )
//            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Количество участников:",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "$userListSize",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

