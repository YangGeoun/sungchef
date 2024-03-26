package com.ssafy.sungchef.features.screen.cooking.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.sungchef.features.screen.cooking.CookingScreen

const val cookingNavigationRoute="cooking_screen"

fun NavController.navigateCooking(
    navOptions: NavOptions?=null,
    id: Int,
){
    this.navigate(cookingNavigationRoute,navOptions)
}

fun NavGraphBuilder.cookingScreen(){
    composable(route = cookingNavigationRoute){
        CookingScreen()
    }
}