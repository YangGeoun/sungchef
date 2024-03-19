package com.ssafy.sungchef.features.screen.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.sungchef.features.screen.home.HomeScreen

const val homeNavigationRoute = "home_screen"


fun NavController.navigateHome(
    navOptions: NavOptions? = null
) {
    this.navigate(homeNavigationRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(){
    composable(homeNavigationRoute) {
        HomeScreen()
    }
}