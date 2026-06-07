package com.mashakulabukhova.expensesharingsystem.presentation.navigation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mashakulabukhova.expensesharingsystem.presentation.navigation.EventsNavGraph
import com.mashakulabukhova.expensesharingsystem.presentation.navigation.FriendsNavGraph
import com.mashakulabukhova.expensesharingsystem.presentation.screen.profile.ProfileScreen

@Composable
fun MainNavGraph() {

    val navController = rememberNavController()


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            MainNavigationBar(navController = navController)
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "event_screen"
        ) {
            composable("event_screen") {
                EventsNavGraph(Modifier
                    .padding(
                        start = 16.dp,
                        top = innerPadding.calculateTopPadding() + 24.dp,
                        end = 16.dp,
                        bottom = innerPadding.calculateBottomPadding()
                    ))
            }
            composable("friends_screen") {
                FriendsNavGraph(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = innerPadding.calculateTopPadding() + 24.dp,
                            end = 16.dp,
                            bottom = innerPadding.calculateBottomPadding()
                        )
                )
            }
            composable("profile_screen") {
                ProfileScreen(Modifier
                    .padding(
                        start = 16.dp,
                        top = innerPadding.calculateTopPadding() + 24.dp,
                        end = 16.dp,
                        bottom = innerPadding.calculateBottomPadding()
                    ))
            }
        }
    }
}