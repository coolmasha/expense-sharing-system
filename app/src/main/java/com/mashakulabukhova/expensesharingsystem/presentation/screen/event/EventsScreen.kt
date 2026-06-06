package com.mashakulabukhova.expensesharingsystem.presentation.screen.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.presentation.component.ErrorMessage
import com.mashakulabukhova.expensesharingsystem.presentation.component.LoadingIndicator
import com.mashakulabukhova.expensesharingsystem.presentation.component.PrimaryGradient

@Composable
fun EventsScreen(
    modifier: Modifier = Modifier,
    viewModel: EventsViewModel = hiltViewModel(),
    addNewEvent: () -> Unit,
    onEventClick: (String) -> Unit
) {

    val state = viewModel.state.collectAsState().value

    PrimaryGradient(modifier = modifier)
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Мои события",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )

        AddEventButton(addNewEvent = { addNewEvent() })

        when (state) {
            is EventsState.Error -> {
                ErrorMessage(
                    Modifier.fillMaxSize(),
                    state.message
                )
            }

            is EventsState.Loading -> {
                LoadingIndicator(Modifier.fillMaxSize())
            }

            is EventsState.Success -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Актуальные:",
                        modifier = Modifier.padding(start = 16.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    EventList(state.data, { onEventClick(it) })
                }
            }
        }
    }
}

@Composable
fun EventList(
    eventList: List<Event>,
    onEventClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = eventList,
            key = { event -> event.id }
        ) { event ->
            EventItem(event, onEventClick)
        }
    }
}

@Composable
fun AddEventButton(
    addNewEvent: () -> Unit
) {
    Button(
        onClick = addNewEvent,
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
                text = "Добавить событие",
                modifier = Modifier,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}