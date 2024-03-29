package com.ssafy.sungchef.data.model.responsedto.token

data class TokenResponse(
    val grantType : String,
    val accessToken : String,
    val refreshToken : String
)
