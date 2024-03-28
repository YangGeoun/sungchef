package com.ssafy.sungchef.features.screen.cooking.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.sungchef.features.screen.cooking.DeleteIngredientScreen

const val deleteIngredientRoute = "deleteIngredient_screen"

fun NavController.navigateDeleteIngredient(
    navOptions: NavOptions? = null
) {
    this.navigate(deleteIngredientRoute, navOptions)
}

fun NavGraphBuilder.deleteIngredientScreen(navController: NavController) {
    composable(route = deleteIngredientRoute) {
        DeleteIngredientScreen(hiltViewModel())
    }
}