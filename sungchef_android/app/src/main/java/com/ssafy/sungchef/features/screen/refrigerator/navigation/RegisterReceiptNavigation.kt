package com.ssafy.sungchef.features.screen.refrigerator.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.sungchef.features.screen.refrigerator.receipt.register.RegisterReceiptScreen
import com.ssafy.sungchef.features.screen.refrigerator.receipt.start.StartReceiptScreen

const val registerReceiptNavigationRoute = "register_receipt_screen"

fun NavController.navigateRegisterReceipt(
    navOptions: NavOptions? = null
) {
    this.navigate(registerReceiptNavigationRoute, navOptions)
}

fun NavGraphBuilder.registerReceiptScreen(navController: NavController){
    composable(registerReceiptNavigationRoute) {
        RegisterReceiptScreen(

        )
    }
}