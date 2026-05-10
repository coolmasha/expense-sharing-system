package com.mashakulabukhova.expensesharingsystem.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mashakulabukhova.expensesharingsystem.presentation.screen.authorization.LoginScreen
import com.mashakulabukhova.expensesharingsystem.presentation.screen.authorization.PasswordRecoveryScreen
import com.mashakulabukhova.expensesharingsystem.presentation.screen.authorization.RegistrationScreen

@Composable
fun AuthorizationNavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AuthorizationScreen.Login.route
    ) {
        composable(AuthorizationScreen.Login.route) {
            LoginScreen(
                modifier,
                onPasswordRecoveryClick = {
                    navController.navigate(AuthorizationScreen.PasswordRecovery.route)
                },
                onRegistrationClick = {
                    navController.navigate(AuthorizationScreen.Registration.route)
                }
            )
        }
        composable(AuthorizationScreen.Registration.route) {
            RegistrationScreen(
                modifier,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(AuthorizationScreen.PasswordRecovery.route) {
            PasswordRecoveryScreen(
                modifier,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = AuthorizationScreen.Test.route) {
            Testing()
        }
    }
}

sealed class AuthorizationScreen(val route: String) {
    data object Login : AuthorizationScreen(route = "login")
    data object Registration : AuthorizationScreen(route = "registration")
    data object PasswordRecovery : AuthorizationScreen(route = "password_recovery")
    data object Test : AuthorizationScreen(route = "test")
}

@Composable
fun Testing() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Testing",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displayMedium
        )
    }

}