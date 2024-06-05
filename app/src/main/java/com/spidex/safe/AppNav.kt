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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.compose.rememberNavController
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.balltrajectory.Straight
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

    val systemUiController = rememberSystemUiController()

    val navigationItems = listOf(
        navigationItemCreator("Home", NavigationRoute.Home.route, (R.drawable.ic_home_true), (R.drawable.ic_home_false)),
        navigationItemCreator("Security", NavigationRoute.Security.route, (R.drawable.ic_shield_true), (R.drawable.ic_shield_false)),
        navigationItemCreator("Profile", NavigationRoute.Profile.route, (R.drawable.ic_profile_true), (R.drawable.ic_profile_false))
    )

    val navController = rememberNavController()
    val navigationViewModel : NavigationViewModel = viewModel()
    val selectedIndex by remember { mutableStateOf(navigationViewModel.currentScreen) }

    val view = LocalView.current

    SideEffect {
        systemUiController.isNavigationBarVisible = false
        systemUiController.isStatusBarVisible = false
        view.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }


    Scaffold(
        containerColor = background,
        bottomBar = {
            AnimatedNavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(color = background)
                    .wrapContentHeight()
                    .navigationBarsPadding(),
                selectedIndex = selectedIndex.value,
                barColor = Color.White,
                ballColor = when{
                                selectedIndex.value == 0 -> yellow
                                selectedIndex.value == 1 -> green
                                else -> red
                                },
                cornerRadius = shapeCornerRadius(cornerRadius = 32.dp),
                ballAnimation = Parabolic(tween(300)),
                indentAnimation = Height(tween(300)),
                content = {
                    navigationItems.forEachIndexed {index, item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .wrapContentHeight()
                                .background(color = Color.White)
                                .noRippleClickable {
                                    navigationViewModel.setCurrentScreen(index)
                                    navController.navigate(item.route) {
                                        popUpTo(
                                            navController.graph.startDestinationId
                                        ){
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                            contentAlignment = Alignment.Center,
                        ){
                            Icon(
                                modifier = Modifier
                                    .width(26.dp)
                                    .height(26.dp),
                                painter = painterResource(
                                    id = if (selectedIndex.value == index) item.selectedIcon
                                            else item.icon),
                                contentDescription = item.name,
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            )
        },
    ) { innerPadding ->

        NavHost(navController = navController, startDestination = NavigationRoute.Security.route, modifier = Modifier.padding(innerPadding)) {
            composable(NavigationRoute.Home.route) {
                HomeScreen()
            }
            composable(NavigationRoute.Security.route) {
                SecurityScreen()
            }
            composable(NavigationRoute.Profile.route) {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun navigationItemCreator(title: String, screen_route: String, active_icon: Int, inactive_icon: Int) : NavigationItem {
    val navigationItem = NavigationItem(
        name = title,
        route = screen_route,
        icon = (inactive_icon),
        selectedIcon = (active_icon),
    )
    return navigationItem
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.noRippleClickable(onClick: () -> Unit) : Modifier = composed{
    clickable(
        indication = null,
        interactionSource = remember{ MutableInteractionSource() },
        onClick = onClick
    )
}