package com.ssafy.sungchef.features.screen.refrigerator.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.sungchef.features.screen.refrigerator.receipt.start.StartReceiptScreen

const val startReceiptNavigationRoute = "start_receipt_screen"

fun NavController.navigateStartReceipt(
    navOptions: NavOptions? = null
) {
    this.navigate(startReceiptNavigationRoute, navOptions)
}

fun NavGraphBuilder.startReceiptScreen(
    navController: NavController,
    navVisibility : () -> Unit
){
    composable(startReceiptNavigationRoute) {
        navVisibility()
        StartReceiptScreen(
            viewModel = hiltViewModel()
        ){
            navController.navigateRegisterReceipt()
        }

    }
}