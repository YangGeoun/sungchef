package com.ssafy.sungchef.features.screen.login.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.ssafy.sungchef.features.screen.home.navigation.homeNavigationRoute
import com.ssafy.sungchef.features.screen.home.navigation.navigateHome
import com.ssafy.sungchef.features.screen.login.LoginScreen
import com.ssafy.sungchef.features.screen.signup.navigation.signupRoute
import com.ssafy.sungchef.features.screen.survey.navigation.survey_route

const val login_route = "login_screen"

fun NavController.navigateLogin(
    navOptions: NavOptions? = null
) {
    this.navigate(login_route, navOptions)
}

fun NavGraphBuilder.loginScreen(
    navController: NavController,
    navVisibility : () -> Unit
){
    composable(login_route) {
        navVisibility()
        LoginScreen(
            viewModel = hiltViewModel(),
            onMoveSignupPage = {
                navController.navigate(signupRoute)
            },
            onMoveSurveyPage = {
                navController.navigate(survey_route)
            },
            onMoveHomePage = {
                navController.navigate(
                    homeNavigationRoute,
                    navOptions {
                        popUpTo(login_route) {
                            inclusive = true
                        }

                        // 새 화면을 백스택의 유일한 화면으로 만듭니다.
                        launchSingleTop = true
                    }
                )
            }
        )
    }
}