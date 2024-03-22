package com.ssafy.sungchef.features.screen.menu.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.sungchef.features.screen.menu.MenuScreen

const val menuNavigationRoute = "menu_screen"

fun NavController.navigateMenu(
    navOptions: NavOptions? = null
) {
    this.navigate(menuNavigationRoute, navOptions)
}

fun NavGraphBuilder.menuScreen(navigateToMenuDetail: (Int)->(Unit)){
    composable(menuNavigationRoute){
        MenuScreen(hiltViewModel()){
            navigateToMenuDetail(it)
        }
    }
}