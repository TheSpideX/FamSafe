package com.spidex.safe

import androidx.compose.runtime.Composable

sealed class NavigationRoute(val route: String) {
    data object Home : NavigationRoute("home")
    data object Security : NavigationRoute("security")
    data object Profile : NavigationRoute("profile")
    data object Map : NavigationRoute("map")
}

data class NavigationItem(
    val id : Int,
    val name: String,
    val route: String,
    val icon: Int,
    val selectedIcon: Int
)

@Composable
fun navigationItemCreator(id : Int,title: String, screenRoute: String, activeIcon: Int, inactiveIcon: Int) : NavigationItem {
    val navigationItem = NavigationItem(
        id = id,
        name = title,
        route = screenRoute,
        icon = (inactiveIcon),
        selectedIcon = (activeIcon),
    )
    return navigationItem
}