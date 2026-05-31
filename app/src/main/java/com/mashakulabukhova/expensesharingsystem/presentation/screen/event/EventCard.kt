package com.mashakulabukhova.expensesharingsystem.presentation.screen.event

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mashakulabukhova.expensesharingsystem.R
import com.mashakulabukhova.expensesharingsystem.domain.entity.Event

@Composable
fun EventCard(
    event: Event,
    onEventClick: (String) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onEventClick(event.id)
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = (MaterialTheme.colorScheme.secondaryContainer.copy(0.2f)),
        )
//        colors = CardDefaults.cardColors(
//            containerColor = (MaterialTheme.colorScheme.background),
//        ),
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = 4.dp
//    )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(iconForEventCategory(event.category)),
                contentDescription = "Category Icon",
                modifier = Modifier
                    .size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = event.title,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodyLarge
            )
//            IconButton(
//                onClick = {
//                    onEventClick(event.id)
//                }
//            ) {
//
//            }

            Icon(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "Arrow",
                modifier = Modifier
                    .size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

    }
}


fun iconForEventCategory(category: String): Int {
    return when {
        category.contains("рест", true) -> R.drawable.restaurant_event_icon
        category.contains("каф", true) -> R.drawable.restaurant_event_icon
        category.contains("поход", true) -> R.drawable.hike_event_icon
        category.contains("поезд", true) -> R.drawable.trip_event_icon
        category.contains("путеш", true) -> R.drawable.trip_event_icon
        else -> R.drawable.trip_event_icon
    }
}