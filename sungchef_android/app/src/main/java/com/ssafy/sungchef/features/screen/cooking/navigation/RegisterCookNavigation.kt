package com.ssafy.sungchef.features.screen.cooking.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.ssafy.sungchef.features.screen.cooking.RegisterCookScreen
import com.ssafy.sungchef.features.screen.home.navigation.homeNavigationRoute
import com.ssafy.sungchef.features.screen.home.navigation.navigateHome

const val registerCookRoute = "registerCook_screen"

fun NavController.navigateRegisterCook(
    navOptions: NavOptions? = null,
    id: Int
) {
    this.navigate(registerCookRoute.plus("/$id"), navOptions)
}

@RequiresApi(Build.VERSION_CODES.P)
fun NavGraphBuilder.registerCookScreen(
    navController: NavController,
) {
    composable(route = registerCookRoute.plus("/{recipeId}")) {
        val id = it.arguments!!.getString("recipeId")
        val parentEntry =
            remember(it) { navController.getBackStackEntry(deleteIngredientRoute.plus("/id")) }
        RegisterCookScreen(hiltViewModel(parentEntry)) {
            navController.navigateHome(navOptions = navOptions {
                popUpTo(homeNavigationRoute) {
                }
                launchSingleTop = true
            }
            )
        }
    }
}