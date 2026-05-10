package com.mashakulabukhova.expensesharingsystem.presentation.screen.authorization

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mashakulabukhova.expensesharingsystem.R
import com.mashakulabukhova.expensesharingsystem.presentation.component.VerticalGradientButton
import com.mashakulabukhova.expensesharingsystem.presentation.ui.theme.ExpenseSharingSystemTheme
import com.mashakulabukhova.expensesharingsystem.presentation.ui.theme.Grey500
import com.mashakulabukhova.expensesharingsystem.presentation.ui.theme.Red200
import com.mashakulabukhova.expensesharingsystem.presentation.ui.theme.Red800

@Preview
@Composable
fun Test() {
    ExpenseSharingSystemTheme() {
//        LoginScreen(onAuthorizationClick = {}, onRegistrationClick = {})
    }

}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onPasswordRecoveryClick: () -> Unit,
    onRegistrationClick: () -> Unit
) {

    val state = viewModel.state.collectAsState()
    val email by viewModel.emailState.collectAsState()
    val password by viewModel.passwordState.collectAsState()
    val passwordVisible by viewModel.passwordVisibleState.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()

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
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Hedgehog with dollar",
                    modifier = Modifier
                        .size(113.dp, 86.dp)
                )
                Text(
                    text = "Авторизация",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.displayMedium
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val hasError = emailError != null
                    val placeholderColor = when {
                        hasError -> MaterialTheme.colorScheme.onError
                        else -> Grey500
                    }
                    Text(
                        text = "Логин",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
//                    Surface(
//                        modifier = Modifier.fillMaxWidth(),
//                        shadowElevation = 4.dp,
//                        shape = RoundedCornerShape(50.dp)
//                    ) {
                        OutlinedTextField(
                            value = email,
                            onValueChange = { viewModel.updateEmail(it) },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodySmall,
                            placeholder = { Text("mari@mail.ru", color = placeholderColor) },
                            isError = hasError,
                            supportingText = {
                                if (hasError) {
                                    Text(
                                        text = emailError!!,
                                        color = MaterialTheme.colorScheme.onError,
                                        style = MaterialTheme.typography.bodySmall
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

                                errorTextColor = MaterialTheme.colorScheme.onError,
                                errorContainerColor = MaterialTheme.colorScheme.errorContainer,
                                errorIndicatorColor = MaterialTheme.colorScheme.onError,
                            )
                        )
//                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val hasError = passwordError != null
                    val placeholderColor = when {
                        hasError -> MaterialTheme.colorScheme.onError
                        else -> Grey500
                    }

                    val iconColor = when {
                        hasError -> MaterialTheme.colorScheme.onError
                        else -> Grey500
                    }

                    val iconBackgroundColor = when {
                        hasError -> MaterialTheme.colorScheme.errorContainer
                        else -> MaterialTheme.colorScheme.background
                    }

                    Text(
                        text = "Пароль",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
//                    Surface(
//                        modifier = Modifier.fillMaxWidth(),
//                        shadowElevation = 4.dp,
//                        shape = RoundedCornerShape(50.dp)
//                    ) {
                        OutlinedTextField(
                            value = password,
                            onValueChange = { viewModel.updatePassword(it) },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodySmall,
                            placeholder = { Text("password123", color = placeholderColor) },
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
                                        contentColor = iconColor,
                                        containerColor = iconBackgroundColor
                                    )
                                ) {
                                    Icon(
                                        imageVector = image, contentDescription = null
                                    )
                                }
                            },
                            isError = hasError,
                            supportingText = {
                                if (hasError) {
                                    Text(
                                        text = passwordError!!,
                                        color = MaterialTheme.colorScheme.onError,
                                        style = MaterialTheme.typography.bodySmall
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

                                errorTextColor = MaterialTheme.colorScheme.onError,
                                errorContainerColor = MaterialTheme.colorScheme.errorContainer,
                                errorIndicatorColor = MaterialTheme.colorScheme.onError,
                            )
                        )
//                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Checkbox(
                        checked = false,
                        onCheckedChange = {},
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Text(
                        text = "Запомнить пароль",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
//                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Забыли пароль?", color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Восстановить ",
                        modifier = Modifier
                            .clickable {
                                onPasswordRecoveryClick()
                            },
                        color = MaterialTheme.colorScheme.tertiary,
                        textDecoration = TextDecoration.Underline,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

            }

            VerticalGradientButton(
                onClick = {
                    viewModel.login()
                },
                modifier = Modifier
                    .width(160.dp)
                    .height(56.dp),
                enabled = true
            ) {
                Text(
                    text = "Войти",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Нет аккаунта?",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall
                )
                OutlinedButton(
                    onClick = onRegistrationClick,
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .height(56.dp),
//                        .shadow(
//                            elevation = 4.dp, // высота тени
//                            shape = RoundedCornerShape(50.dp), // форма должна совпадать с формой кнопки
//                            clip = false // чтобы тень не обрезалась
//                        ),
                    enabled = true,
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Зарегистрироваться",
                        style = MaterialTheme.typography.labelMedium
                    )
                }

            }
        }
    }
}