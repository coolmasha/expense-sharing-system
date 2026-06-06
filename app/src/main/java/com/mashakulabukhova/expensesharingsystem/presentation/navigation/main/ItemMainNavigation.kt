package com.mashakulabukhova.expensesharingsystem.presentation.navigation.main

import com.mashakulabukhova.expensesharingsystem.R


sealed class ItemMainNavigation(val title: String, val iconId: Int, val route: String){
    object EventScreen: ItemMainNavigation("События", R.drawable.event_icon, "event_screen")
    object FriendsScreen: ItemMainNavigation("Друзья", R.drawable.friends_icon, "friends_screen")
//    object ExpensesScreen: ItemMainNavigation("Расходы", R.drawable.fund_icon, "expenses_screen")
    object ProfileScreen: ItemMainNavigation("Профиль", R.drawable.profile_icon, "profile_screen")

    companion object {
        val allScreens = listOf(EventScreen, FriendsScreen, ProfileScreen)

        fun fromRoute(route: String): ItemMainNavigation? =
            allScreens.find { it.route == route }
    }
}