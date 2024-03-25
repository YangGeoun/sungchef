package com.ssafy.sungchef.features.screen.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.sungchef.features.screen.login.LoginScreen

const val login_route = "login_screen"

fun NavController.navigateLogin(
    navOptions: NavOptions? = null
) {
    this.navigate(login_route, navOptions)
}

fun NavGraphBuilder.loginScreen(
    navVisibility : () -> Unit
){
    composable(login_route) {
        navVisibility()
        LoginScreen()
    }
}