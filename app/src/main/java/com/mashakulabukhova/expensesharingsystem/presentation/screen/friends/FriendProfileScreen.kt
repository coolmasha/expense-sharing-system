package com.mashakulabukhova.expensesharingsystem.presentation.screen.friends

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.mashakulabukhova.expensesharingsystem.di.ApiModule
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.presentation.component.PrimaryGradient


@Composable
fun FriendProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: FriendProfileViewModel = hiltViewModel(),
    userId: String
) {
    val user by viewModel.user.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserProfile(userId)
    }

    PrimaryGradient(modifier)
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Профиль друга",
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )

        if (user != null) {
            FriendCard(user!!)
        }

        Text(
            text = "Общие события",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleLarge
        )
    }

}


@Composable
fun FriendCard(
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
            modifier = Modifier.fillMaxWidth()
                .padding(all = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
//                model = ApiModule.getBaseUrl() + "upload/" + user.id,
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
                text = user.username,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = user.email,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}