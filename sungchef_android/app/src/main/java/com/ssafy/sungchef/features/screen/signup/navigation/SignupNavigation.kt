package com.ssafy.sungchef.features.screen.signup.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.ssafy.sungchef.features.screen.signup.SignupBirthScreen
import com.ssafy.sungchef.features.screen.signup.SignupCongratulationScreen
import com.ssafy.sungchef.features.screen.signup.SignupGenderScreen
import com.ssafy.sungchef.features.screen.signup.SignupScreen
import com.ssafy.sungchef.features.screen.survey.navigation.navigateSurvey


const val nicknameNavigationRoute = "signup_nickname_route"
const val birthNavigationRoute = "signup_birth_route"
const val genderNavigationRoute = "signup_gender_route"
const val congratulationRoute = "congratulation_route"

const val signupRoute = "signup_route"

private const val TAG = "SignupNavigation_성식당"
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.signupGraph(navController: NavController) {
    navigation(
        startDestination = nicknameNavigationRoute,
        route = signupRoute
    ) {
        composable(route = nicknameNavigationRoute) {
            SignupScreen(
                viewModel = hiltViewModel(),
                onMoveNextPage = {
                    navController.navigate(birthNavigationRoute)
                }
            ){
                navController.popBackStack()
            }
        }

        composable(route = birthNavigationRoute) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(nicknameNavigationRoute)
            }
            SignupBirthScreen(
                viewModel = hiltViewModel(parentEntry),
                onMoveNextPage = {
                    navController.navigate(genderNavigationRoute)
                }
            ){
                navController.popBackStack()
            }
        }

        composable(route = genderNavigationRoute) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(nicknameNavigationRoute)
            }
            SignupGenderScreen(
                viewModel = hiltViewModel(parentEntry),
                // 회원 가입 축하 페이지로 이동 및 모든 백스택 제거
                onMoveCongratulationPage = {
                    navController.navigate(
                        congratulationRoute,
                        navOptions {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }

                            // 새 화면을 백스택의 유일한 화면으로 만듭니다.
                            launchSingleTop = true
                        }
                    )
                }
            ) {
                navController.popBackStack()
            }
        }

        composable(route = congratulationRoute) {
            SignupCongratulationScreen(
                // 설문 조사 페이지로 이동 및 모든 백스택 제거
                onMoveSurveyPage = {
                    navController.navigateSurvey(
                        navOptions {
                            popUpTo(navController.graph.startDestinationId) {
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
}