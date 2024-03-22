package com.ssafy.sungchef.features.screen.menu.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.sungchef.features.screen.menu.MenuDetailScreen

const val menuDetailNavigationRoute="menuDetail_screen"

fun NavController.navigateMenuDetail(
    recipeId:Int,
    navOptions: NavOptions ?= null
){
    this.navigate(menuDetailNavigationRoute.plus("/$recipeId"),navOptions)
}

fun NavGraphBuilder.menuDetailScreen(){
    composable(menuDetailNavigationRoute.plus("/{recipeId}")){
        it.arguments?.getString("recipeId","")?.let { it1 -> MenuDetailScreen(it1) }
    }
}