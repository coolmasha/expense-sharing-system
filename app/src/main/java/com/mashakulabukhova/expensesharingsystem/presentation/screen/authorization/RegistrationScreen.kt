package com.mashakulabukhova.expensesharingsystem.presentation.screen.authorization

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mashakulabukhova.expensesharingsystem.R
import com.mashakulabukhova.expensesharingsystem.presentation.component.VerticalGradientButton
import com.mashakulabukhova.expensesharingsystem.presentation.component.SecondaryButton
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
            verticalArrangement = Arrangement.spacedBy(40.dp),
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
                    style = MaterialTheme.typography.displayMedium
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
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                OutlinedTextField(
                    value = username,
                    onValueChange = { viewModel.updateUsername(it) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodySmall,
                    placeholder = { Text("Mashulka254", color = Grey500) },
                    shape = RoundedCornerShape(50.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedIndicatorColor = Grey500,
                        unfocusedTextColor = Grey500,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedIndicatorColor = Grey500,
                    )
                )
                Text(
                    text = "Email", color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { viewModel.updateEmail(it) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodySmall,
                    placeholder = { Text("mari@mail.ru", color = Grey500) },
                    shape = RoundedCornerShape(50.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedIndicatorColor = Grey500,
                        unfocusedTextColor = Grey500,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedIndicatorColor = Grey500,
                    )
                )
                Text(
                    text = "Пароль",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { viewModel.updatePassword(it) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodySmall,
                    placeholder = { Text("password123", color = Grey500) },
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
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = Grey500,
                                containerColor = MaterialTheme.colorScheme.background
                            )
                        ) {
                            Icon(
                                imageVector = image, contentDescription = null
                            )
                        }
                    },
                    shape = RoundedCornerShape(50.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedIndicatorColor = Grey500,
                        unfocusedTextColor = Grey500,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedIndicatorColor = Grey500,
                    )
                )
                Text(
                    text = "Повторите пароль",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                OutlinedTextField(
                    value = passwordCheck,
                    onValueChange = { viewModel.updatePasswordCheck(it) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodySmall,
                    placeholder = { Text("password123", color = Grey500) },
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
                            onClick = { viewModel.updatePasswordCheckVisible() },
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = Grey500,
                                containerColor = MaterialTheme.colorScheme.background
                            )
                        ) {
                            Icon(
                                imageVector = image, contentDescription = null
                            )
                        }
                    },
                    shape = RoundedCornerShape(50.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedIndicatorColor = Grey500,
                        unfocusedTextColor = Grey500,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedIndicatorColor = Grey500,
                    )
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
                VerticalGradientButton(
                    onClick = {
                        //регистрация
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

                SecondaryButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .height(56.dp),
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