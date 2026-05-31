package com.mashakulabukhova.expensesharingsystem.presentation.screen.authorization

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mashakulabukhova.expensesharingsystem.R
import com.mashakulabukhova.expensesharingsystem.presentation.component.CustomTextField
import com.mashakulabukhova.expensesharingsystem.presentation.component.PrimaryButton
import com.mashakulabukhova.expensesharingsystem.presentation.component.PrimaryTextField
import com.mashakulabukhova.expensesharingsystem.presentation.component.TertiaryButton
import com.mashakulabukhova.expensesharingsystem.presentation.ui.theme.ExpenseSharingSystemTheme

@Preview
@Composable
fun Test2() {
    ExpenseSharingSystemTheme() {
//        PasswordRecoveryScreen({})
    }
}

@Composable
fun PasswordRecoveryScreen(
    modifier: Modifier = Modifier,
    viewModel: PasswordRecoveryViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {

    val email by viewModel.emailState.collectAsState()
    val emailError by viewModel.emailError.collectAsState()

    val hasError = emailError != null

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
                        .size(102.dp, 86.dp)
                )
                Text(
                    text = "Восстановление пароля",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Text(
                text = "Укажите Ваш email, используемый для входа. \n\n Мы вышлем на него письмо для смены пароля.",
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Email",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium
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
                    isError = hasError,
                    supportingText = {
                        if (hasError) {
                            Text(
                                text = emailError!!,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PrimaryButton(
                    onClick = {
                        viewModel.recoverPassword()
                    },
                    modifier = Modifier
                        .height(56.dp),
                    enabled = true,
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text(
                        text = "Восстановить",
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