package com.ssafy.sungchef.domain.model

data class JwtToken(
    val accessToken : String,
    val refreshToken : String
)
