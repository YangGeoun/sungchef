package com.ssafy.sungchef.data.model.responsedto.user

import com.ssafy.sungchef.data.model.responsedto.token.TokenResponse

data class LoginResponse(
    val jwtToken : TokenResponse,
    val needSurvey : Boolean
)
