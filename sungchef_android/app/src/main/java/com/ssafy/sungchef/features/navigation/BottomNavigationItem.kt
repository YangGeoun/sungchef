package com.ssafy.sungchef.features.navigation

import com.ssafy.sungchef.R
import com.ssafy.sungchef.features.screen.home.navigation.homeNavigationRoute
import com.ssafy.sungchef.features.screen.menu.navigation.menuNavigationRoute
import com.ssafy.sungchef.features.screen.mypage.navigation.myPageNavigationRoute
import com.ssafy.sungchef.features.screen.mypage.navigation.myPageRoute
import com.ssafy.sungchef.features.screen.refrigerator.navigation.refrigeratorNavigationRoute

enum class BottomNavigationItem(
    val label: String = "",
    val icon: Int = R.drawable.home,
    val route: String = ""
) {
    Home(
        label = "Home",
        icon = R.drawable.home,
        route = homeNavigationRoute
    ),
    Menu(
        label = "Menu",
        icon = R.drawable.skillet,
        route = menuNavigationRoute
    ),
    Refrigerator(
        label = "Refrigerator",
        icon = R.drawable.kitchen,
        route = refrigeratorNavigationRoute
    ),
    Profile(
        label = "Profile",
        icon = R.drawable.account_circle,
        route = myPageRoute
    )
}