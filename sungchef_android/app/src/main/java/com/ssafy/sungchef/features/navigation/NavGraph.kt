package com.ssafy.sungchef.features.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.trace
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.ssafy.sungchef.features.component.IconComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.screen.home.navigation.homeNavigationRoute
import com.ssafy.sungchef.features.screen.home.navigation.homeScreen
import com.ssafy.sungchef.features.screen.home.navigation.navigateHome
import com.ssafy.sungchef.features.screen.menu.navigation.menuScreen
import com.ssafy.sungchef.features.screen.menu.navigation.navigateMenu
import com.ssafy.sungchef.features.screen.mypage.navigation.myPageScreen
import com.ssafy.sungchef.features.screen.mypage.navigation.navigateMyPage
import com.ssafy.sungchef.features.screen.refrigerator.navigation.navigateRefrigerator
import com.ssafy.sungchef.features.screen.refrigerator.navigation.refrigeratorScreen

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraph() {
    val navController = rememberNavController()
    val currentDestination = navController
        .currentBackStackEntryAsState().value?.destination
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                BottomNavigationItem.entries
                    .forEach {bottomNavigationItem ->
                        val navigationSelectedItem = currentDestination.isBottomNavDestinationInHierarchy(bottomNavigationItem)
                        NavigationBarItem(
                            selected = navigationSelectedItem,
                            icon = {
                                IconComponent(
                                    painter = painterResource(id = bottomNavigationItem.icon),
                                    contentDescription = bottomNavigationItem.label
                                )
                            },
                            onClick = {
                                navigateToBottomNavDestination(bottomNavigationItem, navController)
                            },
                            label = { TextComponent(text = bottomNavigationItem.label, fontSize = 12.sp) },
                        )
                    }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = homeNavigationRoute,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            homeScreen()
            menuScreen()
            refrigeratorScreen()
            myPageScreen(navController)
        }
    }
}

fun navigateToBottomNavDestination(bottomNav: BottomNavigationItem, navController: NavController) {
    trace("Navigation: ${bottomNav.name}") {
        val bottomNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (bottomNav) {
            BottomNavigationItem.Home -> navController.navigateHome(bottomNavOptions)
            BottomNavigationItem.Menu -> navController.navigateMenu(bottomNavOptions)
            BottomNavigationItem.Refrigerator -> navController.navigateRefrigerator(bottomNavOptions)
            BottomNavigationItem.Profile -> navController.navigateMyPage(bottomNavOptions)
        }
    }
}

private fun NavDestination?.isBottomNavDestinationInHierarchy(destination: BottomNavigationItem) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false