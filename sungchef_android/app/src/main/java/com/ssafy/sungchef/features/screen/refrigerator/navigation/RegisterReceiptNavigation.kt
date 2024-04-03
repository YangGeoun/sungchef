package com.ssafy.sungchef.features.screen.refrigerator.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.ssafy.sungchef.features.screen.refrigerator.receipt.register.RegisterReceiptScreen
import com.ssafy.sungchef.features.screen.refrigerator.receipt.start.StartReceiptScreen

const val registerReceiptNavigationRoute = "register_receipt_screen"

fun NavController.navigateRegisterReceipt(
    imageUrl : String,
    navOptions: NavOptions? = null
) {
    this.navigate(registerReceiptNavigationRoute.plus("/$imageUrl"), navOptions)
}

fun NavGraphBuilder.registerReceiptScreen(
    navController: NavController,
    navVisibility : (Boolean) -> Unit
){
    composable(registerReceiptNavigationRoute.plus("/{imageUrl}")) {

        val imageUrl = it.arguments?.getString("imageUrl")
        RegisterReceiptScreen(
            viewModel = hiltViewModel(),
            imageUrl = imageUrl!!,
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