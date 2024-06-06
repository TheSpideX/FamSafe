@file:Suppress("DEPRECATION")

package com.spidex.safe

import android.annotation.SuppressLint
import android.view.View
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.spidex.safe.ui.theme.background
import com.spidex.safe.ui.theme.green
import com.spidex.safe.ui.theme.red
import com.spidex.safe.ui.theme.yellow

@Composable
fun AppNavigation() {

    val newItem0 = PersonData(
        name = "Satyam",
        location = "Chankya Nagar, Kumhrar,Patna,Bihar,800026,India",
        battery = 90,
        distance = 233,
        network = "4G",
        mobile = "9142299756",
        icon = R.drawable.ic_man,
        cond = 2,
        time = "12:00",
        id = 0
    )
    val newItem1 = PersonData(
        name = "Satyam",
        location = "Chankya Nagar, Kumhrar,Patna,Bihar,800026,India",
        battery = 60,
        distance = 233,
        network = "4G",
        mobile = "9142299756",
        icon = R.drawable.ic_man,
        cond = 0,
        time = "11:00",
        id = 1
    )
    val tempListData : List<PersonData> = listOf(newItem0,newItem1)

    val navigationItems = listOf(
        navigationItemCreator(0,"Home", NavigationRoute.Home.route, (R.drawable.ic_home_true), (R.drawable.ic_home_false)),
        navigationItemCreator(1,"Security", NavigationRoute.Security.route, (R.drawable.ic_shield_true), (R.drawable.ic_shield_false)),
        navigationItemCreator(2,"Profile", NavigationRoute.Profile.route, (R.drawable.ic_profile_true), (R.drawable.ic_profile_false))
    )


    val startDestination = NavigationRoute.Home.route

    val systemUiController = rememberSystemUiController()
    var showBottomNav by remember { mutableStateOf(true) }

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var selectedIndex by remember { mutableStateOf(when{
        startDestination == "home" -> 0
        startDestination == "security" -> 1
        else -> 2
    }) }

    val view = LocalView.current
    SideEffect {
        systemUiController.isNavigationBarVisible = false
        systemUiController.isStatusBarVisible = false
        view.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }


    Scaffold(
        containerColor = background,
        bottomBar = {
            if (showBottomNav) {
                AnimatedNavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(color = background)
                        .wrapContentHeight()
                        .navigationBarsPadding(),
                    selectedIndex = navigationItems.find { it.route == currentRoute }?.id ?: 0,
                    barColor = Color.White,
                    ballColor = when (selectedIndex) {
                        0 -> yellow
                        1 -> green
                        else -> red
                    },
                    cornerRadius = shapeCornerRadius(cornerRadius = 32.dp),
                    ballAnimation = Parabolic(tween(300)),
                    indentAnimation = Height(tween(300)),
                    content = {
                        navigationItems.forEachIndexed { index, item ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .wrapContentHeight()
                                    .background(color = Color.White)
                                    .noRippleClickable {
                                        selectedIndex = when(item.route){
                                            "home" -> 0
                                            "security" -> 1
                                            else -> 2
                                        }
                                        navController.navigate(item.route) {
                                            popUpTo(
                                                navController.graph.startDestinationId
                                            ) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .width(26.dp)
                                        .height(26.dp),
                                    painter = painterResource(
                                        id = if (selectedIndex == index) item.selectedIcon
                                        else item.icon
                                    ),
                                    contentDescription = item.name,
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }
                )
            }
        },
    ) { innerPadding ->

        NavHost(navController = navController, startDestination = startDestination, modifier = Modifier.padding(innerPadding)) {
            composable(NavigationRoute.Home.route) {
                HomeScreen(tempListData){
                    showBottomNav = false
                    navController.navigate(NavigationRoute.Map.route){
                        popUpTo(NavigationRoute.Home.route){
                            saveState = true
                        }
                    }
                }
            }
            composable(NavigationRoute.Security.route) {
                SecurityScreen()
            }
            composable(NavigationRoute.Profile.route) {
                ProfileScreen()
            }
            composable(NavigationRoute.Map.route){
                MapScreen(navController)
                DisposableEffect(Unit) {
                    onDispose {
                        showBottomNav = true
                    }
                }
            }
        }
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.noRippleClickable(onClick: () -> Unit) : Modifier = composed{
    clickable(
        indication = null,
        interactionSource = remember{ MutableInteractionSource() },
        onClick = onClick
    )
}