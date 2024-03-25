package com.ssafy.sungchef.features.screen.survey.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.ssafy.sungchef.features.screen.home.navigation.homeNavigationRoute
import com.ssafy.sungchef.features.screen.refrigerator.RefrigeratorScreen
import com.ssafy.sungchef.features.screen.refrigerator.navigation.refrigeratorNavigationRoute
import com.ssafy.sungchef.features.screen.survey.SurveyScreen

const val survey_route = "survey_screen"


fun NavController.navigateSurvey(
    navOptions: NavOptions? = null,
) {
    this.navigate(survey_route, navOptions)
}

fun NavGraphBuilder.surveyScreen(
    navController: NavController,
    navVisibility : (Boolean) -> Unit
){
    composable(survey_route) {
        SurveyScreen(
            viewModel = hiltViewModel()
        ){
            navVisibility(true)
            navController.navigate(
                homeNavigationRoute,
                navOptions {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }

                    // 새 화면을 백스택의 유일한 화면으로 만듭니다.
                    launchSingleTop = true
                }
            )
        }
    }
}