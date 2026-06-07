package com.mashakulabukhova.expensesharingsystem.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mashakulabukhova.expensesharingsystem.presentation.screen.event.add.AddEventScreen
import com.mashakulabukhova.expensesharingsystem.presentation.screen.event.detail.EventDetailScreen
import com.mashakulabukhova.expensesharingsystem.presentation.screen.event.eventlist.EventsScreen
import com.mashakulabukhova.expensesharingsystem.presentation.screen.expenses.AddExpenseScreen

@Composable
fun EventsNavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "events_screen"
    ) {
        composable("events_screen") {
            EventsScreen(
                modifier = modifier,
                addNewEvent = {
                    navController.navigate("add_new_event")},
                onEventClick = { eventId ->
                    navController.navigate("event_detail_screen/$eventId")
                }
            )
        }
        composable("add_new_event") {
            AddEventScreen(
                modifier = modifier,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "event_detail_screen/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""

            EventDetailScreen(
                modifier = modifier,
                eventId = eventId,
                onAddExpense = {
                    navController.navigate("add_new_expense/$eventId")
                }
            )
        }
        composable(
            route = "add_new_expense/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""

            AddExpenseScreen(
                modifier = modifier,
                eventId = eventId,
                onBackClick = {
                    navController.navigate("event_detail_screen/$eventId")
                }
            )
        }
    }
}