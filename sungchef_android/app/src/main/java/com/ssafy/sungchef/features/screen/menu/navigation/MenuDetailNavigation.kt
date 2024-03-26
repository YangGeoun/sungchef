package com.ssafy.sungchef.features.screen.menu.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.sungchef.features.screen.cooking.navigation.navigateCooking
import com.ssafy.sungchef.features.screen.menu.MenuDetailScreen

const val menuDetailNavigationRoute = "menuDetail_screen"

fun NavController.navigateMenuDetail(
    recipeId: Int,
    navOptions: NavOptions? = null
) {
    this.navigate(menuDetailNavigationRoute.plus("/$recipeId"), navOptions)
}

fun NavGraphBuilder.menuDetailScreen(
    navController: NavController,
    onNavigateCooking: ()->(Unit),
    onBackNavigate: () -> (Unit)
) {
    composable(menuDetailNavigationRoute.plus("/{recipeId}")) {
        val parentEntry = remember(it) {
            navController.getBackStackEntry(menuNavigationRoute)
        }
        it.arguments?.getString("recipeId", "")?.let { it1 ->
            MenuDetailScreen(
                it1,
                hiltViewModel(parentEntry),
                onBackNavigate = { onBackNavigate() }
            ) {
                onNavigateCooking()
                navController.navigateCooking(id = it)
            }
        }
    }
}