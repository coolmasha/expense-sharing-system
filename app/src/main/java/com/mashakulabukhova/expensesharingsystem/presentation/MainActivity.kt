package com.mashakulabukhova.expensesharingsystem.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.mashakulabukhova.expensesharingsystem.presentation.navigation.AuthorizationNavGraph
import com.mashakulabukhova.expensesharingsystem.presentation.ui.theme.ExpenseSharingSystemTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseSharingSystemTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    VerticalGradientButton({}, modifier = Modifier.padding(innerPadding)) {
//                        Text("evev")
//                    }
                    AuthorizationNavGraph(modifier = Modifier
                        .padding(top = innerPadding.calculateTopPadding(), bottom = innerPadding.calculateBottomPadding()))
                }
            }
        }
    }
}