package com.ssafy.sungchef.features.screen.mypage.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.ssafy.sungchef.features.screen.mypage.MyPageScreen
import com.ssafy.sungchef.features.screen.mypage.SettingScreen
import com.ssafy.sungchef.features.screen.signup.navigation.signupRoute
import com.ssafy.sungchef.features.screen.survey.navigation.navigateSurvey
import com.ssafy.sungchef.features.screen.survey.navigation.survey_route

const val myPageNavigationRoute = "mypage_screen"
const val settingNavigationRoute = "setting_screen"
const val myPageRoute = "mypage_route"

fun NavController.navigateMyPage(
    navOptions: NavOptions? = null
) {
    this.navigate(myPageRoute, navOptions)
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.myPageScreen(navController : NavController){

    navigation(startDestination = myPageNavigationRoute, route = myPageRoute) {
        composable(myPageNavigationRoute) { MyPageScreen(navController, hiltViewModel()) }
        composable(settingNavigationRoute) { SettingScreen(navController, hiltViewModel()){
            navController.navigateSurvey(it)
        } }
    }


//    composable(myPageNavigationRoute){
//        val navController = rememberNavController()
//        NavHost(navController = navController, startDestination = "mypage") {
//
//        }
////        MyPageScreen(navController)
//    }
}