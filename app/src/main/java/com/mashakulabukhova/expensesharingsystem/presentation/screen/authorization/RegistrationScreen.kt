package com.mashakulabukhova.expensesharingsystem.presentation.screen.authorization

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mashakulabukhova.expensesharingsystem.R
import com.mashakulabukhova.expensesharingsystem.presentation.component.PrimaryButton
import com.mashakulabukhova.expensesharingsystem.presentation.component.PrimaryTextField
import com.mashakulabukhova.expensesharingsystem.presentation.component.TertiaryButton
import com.mashakulabukhova.expensesharingsystem.presentation.ui.theme.ExpenseSharingSystemTheme
import com.mashakulabukhova.expensesharingsystem.presentation.ui.theme.Grey500

@Preview
@Composable
fun Test1() {
    ExpenseSharingSystemTheme() {
//        RegistrationScreen(modifier = Modifier, onRegistrationClick = {}, onBackClick = {})
    }
}

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = viewModel(),
    onBackClick: () -> Unit
) {

    val state = viewModel.registrationState.collectAsState()
    val username by viewModel.usernameState.collectAsState()
    val email by viewModel.emailState.collectAsState()
    val password by viewModel.passwordState.collectAsState()
    val passwordCheck by viewModel.passwordCheckState.collectAsState()
    val passwordVisible by viewModel.passwordVisibleState.collectAsState()
    val passwordCheckVisible by viewModel.passwordCheckVisibleState.collectAsState()

    val usernameError by viewModel.usernameError.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val passwordCheckError by viewModel.passwordCheckError.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Hedgehog with dollar",
                    modifier = Modifier
                        .size(113.dp, 86.dp)
                )

                Text(
                    text = "Регистрация",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Имя пользователя",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                PrimaryTextField(
                    value = username,
                    onValueChange = { viewModel.updateUsername(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "Mashulka254",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    isError = usernameError != null,
                    supportingText = {
                        if (usernameError != null) {
                            Text(
                                text = usernameError!!,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                )
                Text(
                    text = "Email",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                PrimaryTextField(
                    value = email,
                    onValueChange = { viewModel.updateEmail(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "mari@mail.ru",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    isError = emailError != null,
                    supportingText = {
                        if (emailError != null) {
                            Text(
                                text = emailError!!,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                )
                Text(
                    text = "Пароль",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                PrimaryTextField(
                    value = password,
                    onValueChange = { viewModel.updatePassword(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "password123",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    isError = passwordError != null,
                    supportingText = {
                        if (passwordError != null) {
                            Text(
                                text = passwordError!!,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible) {
                            Icons.Outlined.Visibility
                        } else {
                            Icons.Outlined.VisibilityOff
                        }

                        IconButton(
                            onClick = { viewModel.updatePasswordVisible() },
                        ) {
                            Icon(
                                imageVector = image, contentDescription = null
                            )
                        }
                    }
                )
                Text(
                    text = "Повторите пароль",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                PrimaryTextField(
                    value = passwordCheck,
                    onValueChange = { viewModel.updatePasswordCheck(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "password123",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    isError = passwordCheckError != null,
                    supportingText = {
                        if (passwordCheckError != null) {
                            Text(
                                text = passwordCheckError!!,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    },
                    visualTransformation = if (passwordCheckVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordCheckVisible) {
                            Icons.Outlined.Visibility
                        } else {
                            Icons.Outlined.VisibilityOff
                        }

                        IconButton(
                            onClick = { viewModel.updatePasswordCheckVisible() }
                        ) {
                            Icon(
                                imageVector = image, contentDescription = null
                            )
                        }
                    }
                )
                val currentState = state.value
                if (currentState is RegistrationState.Error) {
                    Text(
                        text = currentState.message,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PrimaryButton(
                    onClick = {
                        viewModel.registration()
                    },
                    modifier = Modifier
                        .height(56.dp),
                    enabled = true,
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text(
                        text = "Зарегистрироваться",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                TertiaryButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .height(IntrinsicSize.Min),
                    enabled = true,
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text(
                        text = "Отмена",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

        }
    }
}