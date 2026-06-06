package com.mashakulabukhova.expensesharingsystem.presentation.screen.friends

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.presentation.component.ErrorMessage
import com.mashakulabukhova.expensesharingsystem.presentation.component.LoadingIndicator
import com.mashakulabukhova.expensesharingsystem.presentation.component.PrimaryGradient
import com.mashakulabukhova.expensesharingsystem.presentation.component.SecondaryTextField

@Composable
fun FriendsScreen(
    modifier: Modifier = Modifier,
    viewModel: FriendsViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState().value
    val actionState = viewModel.actionState.collectAsState().value

    val userList by viewModel.userList.collectAsState()
    val friendList by viewModel.friendList.collectAsState()
    val incomingRequests by viewModel.incomingRequests.collectAsState()
    val sentRequests by viewModel.sentRequests.collectAsState()

    val searchQuery by viewModel.searchQuery.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler() {
        viewModel.clearSearch()
        keyboardController?.hide()
    }

    PrimaryGradient(modifier)
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Друзья",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )
        SecondaryTextField(
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryUpdate(it) },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(20.dp),
                    clip = true
                ),
            textStyle = MaterialTheme.typography.bodyLarge,
            placeholder = {
                Text(
                    text = "Поиск",
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
                    modifier = Modifier
                        .size(32.dp)
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    viewModel.onSearchTriggered()
                    keyboardController?.hide()
                }
            ),
            singleLine = true
        )

        when (actionState) {
            is ActionState.Init -> {
                FriendsView(
                    friendList = friendList,
                    onFriendClick = { viewModel.deleteFriend(it) },
                    incomingRequests = incomingRequests,
                    sentRequests = sentRequests,
                    state = state,
                    onFriendsClick = { viewModel.getAllFriends() },
                    onIncomingRequestsClick = { viewModel.getIncomingRequests() },
                    onSentRequestsClick = {
                        viewModel.getOutgoingRequests()
                    },
                    onAcceptClick = { viewModel.acceptFriendshipRequest(it) },
                    onRejectClick = { viewModel.rejectFriendshipRequest(it) },
                    onCancelClick = { viewModel.cancelFriendshipRequest(it) }
                )
            }

            is ActionState.Search -> {
                SearchResultsView(
                    userList = userList,
                    onUserClick = { viewModel.sendFriendshipRequest(it) },
                    state = state
                )
            }
        }
    }
}

@Composable
fun SearchResultsView(
    userList: List<User>,
    onUserClick: (String) -> Unit,
    state: FriendsState = FriendsState.Loading
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Результаты поиска",
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleLarge
        )
        when (state) {
            is FriendsState.Error -> {
                ErrorMessage(
                    Modifier.fillMaxSize(),
                    state.message
                )
            }

            is FriendsState.Loading -> {
                LoadingIndicator(Modifier.fillMaxSize())
            }

            is FriendsState.Success -> {
                if (userList.isEmpty()) {
                    EmptySearchState(
                        modifier = Modifier.fillMaxSize(),
                        message = "Ничего не найдено"
                    )
                } else {
                    UserList(
                        userList = userList,
                        onUserClick = onUserClick
                    )
                }
            }
        }
    }
}


@Composable
fun FriendsView(
    friendList: List<User>,
    onFriendClick: (String) -> Unit,
    incomingRequests: List<User>,
    sentRequests: List<User>,
    state: FriendsState = FriendsState.Loading,
    onFriendsClick: () -> Unit,
    onIncomingRequestsClick: () -> Unit,
    onSentRequestsClick: () -> Unit,
    onAcceptClick: (String) -> Unit,
    onRejectClick: (String) -> Unit,
    onCancelClick: (String) -> Unit
) {

    var isIncomingRequestsMode by remember { mutableStateOf(false) }
    var isSentRequestsMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {
                    onFriendsClick()
                    isIncomingRequestsMode = false
                }
            ) {
                Text(
                    text = "Мои друзья",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            TextButton(
                onClick = {
                    onIncomingRequestsClick()
                    isIncomingRequestsMode = true
                }
            ) {
                Text(
                    text = "Заявки",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.titleLarge
                )
            }

        }

        if (isIncomingRequestsMode) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        onIncomingRequestsClick()
                        isSentRequestsMode = false
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = if (isSentRequestsMode)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
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
                        text = "Входящие",
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                TextButton(
                    onClick = {
                        onSentRequestsClick()
                        isSentRequestsMode = true
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = if (isSentRequestsMode)
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
                        text = "Отправленные",
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }

        when (state) {
            is FriendsState.Error -> {
                ErrorMessage(
                    Modifier.fillMaxSize(),
                    state.message
                )
            }

            is FriendsState.Loading -> {
                LoadingIndicator(Modifier.fillMaxSize())
            }

            is FriendsState.Success -> {
                if (!isIncomingRequestsMode) {
                    if (friendList.isEmpty()) {
                        EmptyState(
                            modifier = Modifier.fillMaxSize(),
                            message = "У вас пока нет друзей"
                        )
                    } else {
                        FriendList(
                            userList = friendList,
                            onUserClick = onFriendClick
                        )
                    }
                } else {
                    if (!isSentRequestsMode) {
                        if (incomingRequests.isEmpty()) {
                            EmptyState(
                                modifier = Modifier.fillMaxSize(),
                                message = "У вас нет входящих заявок в друзья"
                            )
                        } else {
                            IncomingRequestList(incomingRequests, onAcceptClick, onRejectClick)
                        }
                    } else {
                        if (sentRequests.isEmpty()) {
                            EmptyState(
                                modifier = Modifier.fillMaxSize(),
                                message = "У вас нет отправленных заявок в друзья"
                            )
                        } else {
                            SentRequestList(sentRequests, onCancelClick)
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun IncomingRequestList(
    incomingRequests: List<User>,
    onAcceptClick: (String) -> Unit,
    onRejectClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = incomingRequests,
            key = { user -> user.id }
        ) { user ->
            IncomingRequestCard(user, onAcceptClick, onRejectClick)
        }
    }
}

@Composable
fun SentRequestList(
    sentRequests: List<User>,
    onCancelClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = sentRequests,
            key = { user -> user.id }
        ) { user ->
            SentRequestCard(user, onCancelClick)
        }
    }
}


@Composable
fun UserList(
    userList: List<User>,
    onUserClick: (String) -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = userList,
            key = { friend -> friend.id }
        ) { friend ->
            UserCard(friend, onUserClick)
        }
    }
}

@Composable
fun FriendList(
    userList: List<User>,
    onUserClick: (String) -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = userList,
            key = { friend -> friend.id }
        ) { friend ->
            FriendCard(friend, onUserClick)
        }
    }
}

@Composable
fun EmptyState(modifier: Modifier = Modifier, message: String) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


@Composable
fun EmptySearchState(modifier: Modifier = Modifier, message: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}