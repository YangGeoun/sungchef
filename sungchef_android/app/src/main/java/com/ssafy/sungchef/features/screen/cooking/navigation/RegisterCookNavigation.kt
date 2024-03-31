package com.ssafy.sungchef.features.screen.cooking.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.sungchef.features.screen.cooking.DeleteIngredientScreen
import com.ssafy.sungchef.features.screen.cooking.RegisterCookScreen

const val registerCookRoute = "registerCook_screen"

fun NavController.navigateRegisterCook(
    navOptions: NavOptions? = null,
    recipeId: Int
) {
    this.navigate(registerCookRoute.plus("/$recipeId"), navOptions)
}

@RequiresApi(Build.VERSION_CODES.P)
fun NavGraphBuilder.registerCookScreen(
) {
    composable(route = registerCookRoute.plus("/{recipeId}")) {
        val id = it.arguments?.getString("recipeId", "-1")
        if (id != null) {
            RegisterCookScreen(id.toInt())
        }
    }
}