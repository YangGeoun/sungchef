package com.ssafy.sungchef.features.screen.signup.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ssafy.sungchef.features.screen.signup.SignupBirthScreen
import com.ssafy.sungchef.features.screen.signup.SignupGenderScreen
import com.ssafy.sungchef.features.screen.signup.SignupScreen


const val nicknameNavigationRoute = "signup_nickname_route"
const val birthNavigationRoute = "signup_birth_route"
const val genderNavigationRoute = "signup_gender_route"

const val signupRoute = "signup_route"

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.signupGraph(navController: NavController) {
    navigation(
        startDestination = nicknameNavigationRoute,
        route = signupRoute
    ) {
        composable(route = nicknameNavigationRoute) {
            SignupScreen(
                viewModel = hiltViewModel()
            ){
                navController.navigate(birthNavigationRoute){

                }
            }
        }

        composable(route = birthNavigationRoute) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(nicknameNavigationRoute)
            }
            SignupBirthScreen(
                viewModel = hiltViewModel(parentEntry)
            ){
                navController.navigate(genderNavigationRoute){

                }
            }
        }

        composable(route = genderNavigationRoute) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(birthNavigationRoute)
            }
            SignupGenderScreen(
                viewModel = hiltViewModel(parentEntry)
            ) {

            }
        }
    }
}