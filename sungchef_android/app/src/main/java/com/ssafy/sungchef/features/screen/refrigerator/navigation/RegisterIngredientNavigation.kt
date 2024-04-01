package com.ssafy.sungchef.features.screen.refrigerator.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.sungchef.features.screen.refrigerator.receipt.register.RegisterIngredientScreen
import com.ssafy.sungchef.features.screen.refrigerator.receipt.register.RegisterReceiptScreen

const val registerIngredientNavigationRoute = "register_ingredient_screen"

fun NavController.navigateRegisterIngredient(
    navOptions: NavOptions? = null
) {
    this.navigate(registerIngredientNavigationRoute, navOptions)
}

fun NavGraphBuilder.registerIngredientScreen(navController: NavController){
    composable(registerIngredientNavigationRoute) {
        RegisterIngredientScreen(

        )
    }
}