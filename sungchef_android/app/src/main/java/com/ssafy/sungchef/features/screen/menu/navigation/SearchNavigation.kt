package com.ssafy.sungchef.features.screen.menu.navigation

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.ssafy.sungchef.features.screen.menu.MenuScreen
import com.ssafy.sungchef.features.screen.menu.SearchMenuScreen

const val searchNavigationRoute = "search_screen"

fun NavController.navigateSearch(
    navOptions: NavOptions? = null,
    menu: String
) {
    Log.d("여기임1", "MenuScreen: $menu")
    this.navigate(searchNavigationRoute.plus("/$menu"),navOptions)
}

fun NavGraphBuilder.searchScreen(
    changeNav: () -> (Unit),
    navigateToMenuDetail: (Int) -> (Unit)
) {
    composable(searchNavigationRoute.plus("/{menu}")) {
        var menu = it.arguments?.getString("menu")
        if (menu == "-1") {
            menu = ""
        }
        Log.d("여기임2", "MenuScreen: $menu")
        SearchMenuScreen(
            hiltViewModel(),
            menu = menu!!,
            changeNav = changeNav
        ) { recipeId ->
            navigateToMenuDetail(recipeId)
        }
    }
}