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
        label = "홈",
        icon = R.drawable.home,
        route = homeNavigationRoute
    ),
    Menu(
        label = "메뉴",
        icon = R.drawable.skillet,
        route = menuNavigationRoute
    ),
    Refrigerator(
        label = "냉장고",
        icon = R.drawable.kitchen,
        route = refrigeratorNavigationRoute
    ),
    Profile(
        label = "마이페이지",
        icon = R.drawable.account_circle,
        route = myPageRoute
    )
}