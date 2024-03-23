package com.ssafy.sungchef.features.screen.survey.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.sungchef.features.screen.refrigerator.RefrigeratorScreen
import com.ssafy.sungchef.features.screen.refrigerator.navigation.refrigeratorNavigationRoute
import com.ssafy.sungchef.features.screen.survey.SurveyScreen

const val survey_route = "survey_screen"


fun NavController.navigateSurvey(
    navOptions: NavOptions? = null
) {
    this.navigate(survey_route, navOptions)
}

fun NavGraphBuilder.surveyScreen(){
    composable(survey_route) {
        SurveyScreen(
            viewModel = hiltViewModel()
        )
    }
}