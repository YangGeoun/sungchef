package com.ssafy.sungchef.features.screen.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.sungchef.features.screen.mypage.MyPageScreen

const val myPageNavigationRoute = "mypage_screen"

fun NavController.navigateMyPage(
    navOptions: NavOptions? = null
) {
    this.navigate(myPageNavigationRoute, navOptions)
}

fun NavGraphBuilder.myPageScreen(){
    composable(myPageNavigationRoute){
        MyPageScreen()
    }
}