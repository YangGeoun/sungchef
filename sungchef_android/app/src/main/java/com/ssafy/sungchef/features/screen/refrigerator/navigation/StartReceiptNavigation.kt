package com.ssafy.sungchef.features.screen.refrigerator.navigation

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.sungchef.features.screen.refrigerator.receipt.start.StartReceiptScreen

const val startReceiptNavigationRoute = "start_receipt_screen"
private const val TAG = "StartReceiptNavigation_성식당"
fun NavController.navigateStartReceipt(
    navOptions: NavOptions? = null
) {
    this.navigate(startReceiptNavigationRoute, navOptions)
}

fun NavGraphBuilder.startReceiptScreen(
    navController: NavController,
    onBackNavigate : () -> Unit
){
    composable(startReceiptNavigationRoute) {
        Log.d(TAG, "startReceiptScreen: 호출")
        StartReceiptScreen(
            viewModel = hiltViewModel(),
            onMoveRegisterReceiptPage = {
                navController.navigateRegisterReceipt(it)
            },
            onMoveRegisterIngredientPage = {
                navController.navigateRegisterIngredient()
            },
            onBackNavigate
        )
    }
}