package com.mashakulabukhova.expensesharingsystem.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mashakulabukhova.expensesharingsystem.data.local.UserManager
import com.mashakulabukhova.expensesharingsystem.presentation.navigation.AuthorizationNavGraph
import com.mashakulabukhova.expensesharingsystem.presentation.navigation.main.MainNavGraph
import com.mashakulabukhova.expensesharingsystem.presentation.ui.theme.ExpenseSharingSystemTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val userManager = UserManager
        setContent {
            ExpenseSharingSystemTheme {
                val isLoggedIn by userManager.isLoggedInState.collectAsState()

//                MainNavGraph()
                if (isLoggedIn) {
                    MainNavGraph()
                } else {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        AuthorizationNavGraph(modifier = Modifier
                            .padding(top = innerPadding.calculateTopPadding(), bottom = innerPadding.calculateBottomPadding()))
                    }
                }

            }
        }
    }
}