package com.ssafy.sungchef.domain.model.user

data class LoginState(
    val code : Int = 0,
    val message : String = "",
    val needSurvey : Boolean = false
)
