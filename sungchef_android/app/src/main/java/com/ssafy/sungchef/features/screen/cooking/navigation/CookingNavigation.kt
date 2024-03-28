package com.ssafy.sungchef.features.screen.cooking.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.sungchef.features.screen.cooking.CookingScreen
import com.ssafy.sungchef.features.screen.menu.navigation.menuDetailNavigationRoute
import com.ssafy.sungchef.features.screen.signup.navigation.nicknameNavigationRoute

const val cookingNavigationRoute = "cooking_screen"

fun NavController.navigateCooking(
    navOptions: NavOptions? = null,
    id: String,
) {
    this.navigate(cookingNavigationRoute.plus("/$id"), navOptions)
}

fun NavGraphBuilder.cookingScreen(
    navController: NavController,
    onNavigateBack: () -> (Unit),
    onNavigateDelete: () -> Unit,
    changeNav: () -> (Unit)
) {
    composable(route = cookingNavigationRoute.plus("/{recipeId}")) {
        val parentEntry = remember(it) {
            navController.getBackStackEntry(menuDetailNavigationRoute.plus("/{recipeId}"))
        }
        val id = it.arguments?.getString("recipeId", "-1")
        CookingScreen(
            viewModel = hiltViewModel(parentEntry),
            id = id!!.toInt(),
            onNavigateBack = { onNavigateBack() },
            onNavigateDelete = { onNavigateDelete() }
        ) {
            changeNav()
        }
    }
}