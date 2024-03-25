package com.ssafy.sungchef.data.model.responsedto

data class UserSettingInfo (
    val code : Int,
    val message : String,
    val data : UserSettingInfoData

)

data class UserSettingInfoData (
    val userNickName : String,
    val userGender : String,
    val userImage : String,
    val userBirthdate : String
)