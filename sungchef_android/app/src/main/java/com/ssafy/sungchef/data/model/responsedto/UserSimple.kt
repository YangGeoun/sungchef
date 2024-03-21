package com.ssafy.sungchef.data.model.responsedto

data class UserSimple (
    val code : Int,
    val message : String,
    val data : UserProfile
)

data class UserProfile(
    val userNickname : String,
    val userImage : String,
    val makeRecipeCount : Int,
    val bookmarkRecipeCount : Int
)