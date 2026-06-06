package com.mashakulabukhova.expensesharingsystem.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mashakulabukhova.expensesharingsystem.presentation.screen.friends.FriendProfileScreen
import com.mashakulabukhova.expensesharingsystem.presentation.screen.friends.FriendsScreen

@Composable
fun FriendsNavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "friends_screen"
    ) {
        composable("friends_screen") {
            FriendsScreen(
                modifier = modifier
//                onUserClick = { userId ->
//                    navController.navigate("friend_profile_screen/$userId")
//                }
            )
        }
        composable(
            route = "friend_profile_screen/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            FriendProfileScreen(
                modifier = modifier,
                userId = userId
            )
        }
    }
}