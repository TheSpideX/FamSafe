package com.spidex.safe


enum class NavigationRoute(val route: String) {
    Home("home"),
    Security("security"),
    Profile("profile"),
}


data class NavigationItem(
    val name: String,
    val route: String,
    val icon: Int,
    val selectedIcon: Int
)
