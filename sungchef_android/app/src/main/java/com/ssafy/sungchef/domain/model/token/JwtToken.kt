package com.ssafy.sungchef.domain.model.token

data class JwtToken(
    val accessToken : String,
    val refreshToken : String
)
