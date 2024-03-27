package com.ssafy.sungchef.features.screen.cooking.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.sungchef.features.screen.cooking.CookingScreen

const val cookingNavigationRoute = "cooking_screen"

fun NavController.navigateCooking(
    navOptions: NavOptions? = null,
    id: String,
) {
    this.navigate(cookingNavigationRoute.plus("/$id"), navOptions)
}

fun NavGraphBuilder.cookingScreen(onNavigateBack: () -> (Unit), changeNav: () -> (Unit)) {
    composable(route = cookingNavigationRoute.plus("/{recipeId}")) {
        val id = it.arguments?.getString("recipeId", "-1")
        CookingScreen(
            viewModel = hiltViewModel(),
            id = id!!.toInt(),
            onNavigateBack = {onNavigateBack()}
        ) {
            changeNav()
        }
    }
}