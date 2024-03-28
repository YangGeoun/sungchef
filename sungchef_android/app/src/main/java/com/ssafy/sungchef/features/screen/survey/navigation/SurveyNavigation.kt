package com.ssafy.sungchef.features.screen.survey.navigation

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.ssafy.sungchef.features.screen.home.navigation.homeNavigationRoute
import com.ssafy.sungchef.features.screen.home.navigation.navigateHome
import com.ssafy.sungchef.features.screen.refrigerator.RefrigeratorScreen
import com.ssafy.sungchef.features.screen.refrigerator.navigation.refrigeratorNavigationRoute
import com.ssafy.sungchef.features.screen.survey.SurveyScreen

const val survey_route = "survey_screen"

private const val TAG = "SurveyNavigation_성식당"

fun NavController.navigateSurvey(
    isRestart : Boolean,
    navOptions: NavOptions? = null,
) {
    this.navigate(survey_route.plus("/$isRestart"), navOptions)
}

fun NavGraphBuilder.surveyScreen(
    navController: NavController,
    navVisibility : (Boolean) -> Unit
){
    composable(survey_route.plus("/{isRestart}")) {
        val restart = it.arguments?.getString("isRestart", "false")

        var isRestart = restart == "true"
        navVisibility(false)

        SurveyScreen(
            isRestart,
            viewModel = hiltViewModel()
        ){
            navController.navigateHome(
                navOptions {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }

                    // 새 화면을 백스택의 유일한 화면으로 만듭니다.
                    launchSingleTop = true
                }
            )
            navVisibility(true)
        }
    }
}