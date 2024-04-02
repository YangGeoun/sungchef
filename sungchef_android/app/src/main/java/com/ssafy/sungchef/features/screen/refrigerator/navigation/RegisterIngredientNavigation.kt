package com.ssafy.sungchef.features.screen.refrigerator.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.ssafy.sungchef.features.screen.home.navigation.navigateHome
import com.ssafy.sungchef.features.screen.refrigerator.receipt.register.RegisterIngredientScreen
import com.ssafy.sungchef.features.screen.refrigerator.receipt.register.RegisterReceiptScreen

const val registerIngredientNavigationRoute = "register_ingredient_screen"

fun NavController.navigateRegisterIngredient(
    navOptions: NavOptions? = null
) {
    this.navigate(registerIngredientNavigationRoute, navOptions)
}

fun NavGraphBuilder.registerIngredientScreen(
    navController: NavController,
    navVisibility : (Boolean) -> Unit
){
    composable(registerIngredientNavigationRoute) {
        RegisterIngredientScreen(
            viewModel = hiltViewModel(),
            onMovePage = {
                navController.navigateRefrigerator(
                    navOptions {
                        popUpTo(refrigeratorNavigationRoute)
                    }
                )
                navVisibility(true)
            }
        )
    }
}