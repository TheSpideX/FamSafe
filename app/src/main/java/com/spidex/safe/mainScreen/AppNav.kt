package com.spidex.safe.mainScreen

import android.view.View
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.spidex.safe.HomeScreen
import com.spidex.safe.LoginScreen
import com.spidex.safe.NavViewModel
import com.spidex.safe.NavigationRoute
import com.spidex.safe.ProfileScreen
import com.spidex.safe.R
import com.spidex.safe.SecurityScreen
import com.spidex.safe.SignUpScreen
import com.spidex.safe.authentication.AuthViewModel
import com.spidex.safe.group.GroupSelectionScreen
import com.spidex.safe.group.GroupViewModel
import com.spidex.safe.mapScreen.MapScreen
import com.spidex.safe.navigationItemCreator
import com.spidex.safe.ui.theme.background
import com.spidex.safe.ui.theme.green
import com.spidex.safe.ui.theme.purple
import com.spidex.safe.ui.theme.red
import com.spidex.safe.ui.theme.yellow

@Composable
fun AppNavigation(
    navViewModel : NavViewModel = viewModel(),
    groupViewModel: GroupViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val navigationItems = listOf(
        navigationItemCreator(0,"Home", NavigationRoute.Home.route, (R.drawable.ic_home_true), (R.drawable.ic_home_false)),
        navigationItemCreator(1,"Dashboard", NavigationRoute.Dashboard.route, (R.drawable.ic_dashboard_true), (R.drawable.ic_dashboard_false)),
        navigationItemCreator(2,"Security", NavigationRoute.Security.route, (R.drawable.ic_shield_true), (R.drawable.ic_shield_false)),
        navigationItemCreator(3,"Profile", NavigationRoute.Profile.route, (R.drawable.ic_profile_true), (R.drawable.ic_profile_false)),
    )

    val navController = rememberNavController()

    LaunchedEffect(key1 = authViewModel.currentUser) {
        if (authViewModel.currentUser.value == null) {
            navController.navigate(NavigationRoute.Login.route)
        }
    }

    val startDestination = NavigationRoute.Home.route


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var selectedIndex by remember { mutableIntStateOf(
        when (startDestination) {
            "home" -> 0
            "security" -> 2
            "dashboard" -> 1
            else -> 2
        }
    ) }

    val showBottomNav by navViewModel.showBottomNav.collectAsState()


    val systemUiController = rememberSystemUiController()
    val view = LocalView.current
    SideEffect {
        systemUiController.isNavigationBarVisible = false
        systemUiController.isStatusBarVisible = false
        view.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            navViewModel.setShowBottomNav(
                destination.route in listOf(
                    NavigationRoute.Home.route,
                    NavigationRoute.Dashboard.route,
                    NavigationRoute.Security.route,
                    NavigationRoute.Profile.route
                )
            )
        }
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
                        ballColor = when (currentRoute) {
                            NavigationRoute.Home.route -> yellow
                            NavigationRoute.Dashboard.route -> purple
                            NavigationRoute.Security.route -> green
                            else -> red
                        },
                        cornerRadius = shapeCornerRadius(cornerRadius = 32.dp),
                        ballAnimation = Parabolic(tween(300)),
                        indentAnimation = Height(tween(300)),
                        content = {
                            navigationItems.forEach { item ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .wrapContentHeight()
                                        .background(color = Color.White)
                                        .noRippleClickable {
                                            selectedIndex = when (item.route) {
                                                "home" -> 0
                                                "dashboard" -> 1
                                                "security" -> 2
                                                else -> 3
                                            }
                                            navController.navigate(item.route) { // Navigate only once when clicked
                                                launchSingleTop = true
                                                if (currentRoute != null) {
                                                    popUpTo(currentRoute) {
                                                        inclusive = true
                                                    }
                                                }
                                            }
                                        },
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .width(26.dp)
                                            .height(26.dp),
                                        painter = painterResource(
                                            id = if (currentRoute == item.route) item.selectedIcon
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

            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(innerPadding)
            ) {

                composable(NavigationRoute.Login.route) {
                    LoginScreen(authViewModel,onNavigateToSignUp = {
                        navController.navigate(NavigationRoute.SignUp.route){
                            popUpTo(navController.graph.startDestinationId){
                                inclusive = true
                            }
                        }
                    }, onSuccess = {
                        navController.navigate(NavigationRoute.Home.route){
                            popUpTo(navController.graph.startDestinationId){
                                inclusive = true
                            }
                        }
                    })
                }

                composable(NavigationRoute.SignUp.route){
                    SignUpScreen(authViewModel,onNavigateToLogin = {
                        navController.navigate(NavigationRoute.Login.route){
                            popUpTo(navController.graph.startDestinationId){
                                inclusive = true
                            }
                        }
                    }, onSuccess = {
                        navController.navigate(NavigationRoute.Home.route){
                            popUpTo(navController.graph.startDestinationId){
                                inclusive = true
                            }
                        }
                    })
                }
                
                composable(NavigationRoute.Group.route){
                    GroupSelectionScreen(groupViewModel, onBackClick = {
                        navController.navigateUp()
                    }) {
                        
                    }
                }

                composable(route = NavigationRoute.Permission.route) {

                }

                composable(route = NavigationRoute.Home.route) {
                    HomeScreen(emptyList(), navigateToMaps = {}){
                        navController.navigate(NavigationRoute.Group.route)
                    }
                }

                composable(NavigationRoute.Dashboard.route) {
                    DashboardScreen()
                }
                composable(NavigationRoute.Security.route) {
                    SecurityScreen()
                }
                composable(NavigationRoute.Profile.route) {
                    ProfileScreen()
                }
                composable(NavigationRoute.Map.route) {
                    MapScreen(onBackClick = {
                        navController.navigateUp()
                    })
                }
            }
        }
}

@Preview(showBackground = true)
@Composable
fun AppNavPreview(){
    AppNavigation()
}



