package com.ssafy.sungchef.features.screen.cooking

import com.ssafy.sungchef.domain.viewstate.ViewEvent

sealed class CookingEvent: ViewEvent {
    class getRecipeInfo(val id: Int): CookingEvent()
}