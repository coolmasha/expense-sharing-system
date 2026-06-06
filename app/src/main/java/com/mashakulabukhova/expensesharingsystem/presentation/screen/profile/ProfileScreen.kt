package com.mashakulabukhova.expensesharingsystem.presentation.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.mashakulabukhova.expensesharingsystem.data.local.UserManager
import com.mashakulabukhova.expensesharingsystem.di.ApiModule
import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.presentation.component.ErrorMessage
import com.mashakulabukhova.expensesharingsystem.presentation.component.LoadingIndicator
import com.mashakulabukhova.expensesharingsystem.presentation.component.PrimaryGradient


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState().value

    PrimaryGradient(modifier)
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Мой профиль",
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            when (state) {
                is ProfileState.Error -> {
                    ErrorMessage(
                        modifier = Modifier.fillMaxWidth(),
                        message = state.message
                    )
                    Text(
                        text = "Настройки",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleLarge
                    )
                    LogoutButton()
                }

                is ProfileState.Init -> {}
                is ProfileState.Loading -> {
                    LoadingIndicator(modifier = Modifier.fillMaxSize())
                }

                is ProfileState.Success -> {
                    CurrentUserCard(
                        user = state.user
                    )
                    Text(
                        text = "Настройки",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleLarge
                    )
                    LogoutButton()
                }
            }
        }
    }
}

@Composable
fun CurrentUserCard(
    user: User
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                .padding(all = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
//                model = ApiModule.getBaseUrl() + "upload/" + UserManager.currentUser.id,
                model = ApiModule.getBaseUrl1() + "upload/",
                contentDescription = "Аватар",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                placeholder = rememberVectorPainter(Icons.Outlined.AccountCircle),
                error = rememberVectorPainter(Icons.Outlined.AccountCircle)
            )

            Text(
//                text = UserManager.currentUser.username,
                text = user.username,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
//                text = UserManager.currentUser.email,
                text = user.email,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Composable
fun EventGridList(
    eventList: List<Event>,
    onEventClick: (String) -> Unit
) {

}

@Composable
fun LogoutButton(
) {

    Button(
        onClick = { UserManager.logout() },
        modifier = Modifier,
        shape = RoundedCornerShape(12.dp),
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
            text = "Выйти из приложения",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
//    Button(
//        onClick = {
//            UserManager.logout()
//        },
//        modifier = Modifier,
//        shape = RoundedCornerShape(20.dp),
//
//        colors = ButtonDefaults.buttonColors(
//            containerColor = (MaterialTheme.colorScheme.primaryContainer),
//        ),
//        elevation = ButtonDefaults.buttonElevation(
//            defaultElevation = 4.dp
//        )
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(vertical = 8.dp, horizontal = 8.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = "Выйти из приложения",
//                modifier = Modifier,
//                color = MaterialTheme.colorScheme.onPrimaryContainer,
//                style = MaterialTheme.typography.bodyMedium
//            )
//        }
//    }
}