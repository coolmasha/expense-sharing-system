package com.mashakulabukhova.expensesharingsystem.utils

import com.mashakulabukhova.expensesharingsystem.R


fun transformIconIdToDrawable(iconId: Int): Int {
    return when (iconId) {
        0 -> R.drawable.business_event_icon
        1 -> R.drawable.restaurant_event_icon
        2 -> R.drawable.hike_event_icon
        3 -> R.drawable.trip_event_icon
        4 -> R.drawable.bar_event_icon
        5 -> R.drawable.birthday_event_icon
        6 -> R.drawable.cinema_event_icon
        7 -> R.drawable.coffee_event_icon
        8 -> R.drawable.favorite_event_icon
        9 -> R.drawable.photoshoot_event_icon
        10 -> R.drawable.relaxation_event_icon
        11 -> R.drawable.shopping
        else -> R.drawable.business_event_icon
    }
}

fun transformDrawableToIconId(drawable: Int): Int {
    return when (drawable) {
        R.drawable.business_event_icon -> 0
        R.drawable.restaurant_event_icon -> 1
        R.drawable.hike_event_icon -> 2
        R.drawable.trip_event_icon -> 3
        R.drawable.bar_event_icon -> 4
        R.drawable.birthday_event_icon -> 5
        R.drawable.cinema_event_icon -> 6
        R.drawable.coffee_event_icon -> 7
        R.drawable.favorite_event_icon -> 8
        R.drawable.photoshoot_event_icon -> 9
        R.drawable.relaxation_event_icon -> 10
        R.drawable.shopping -> 11
        else -> 0
    }
}