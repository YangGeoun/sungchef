package com.ssafy.sungchef.data.model.requestdto

import okhttp3.MultipartBody

data class UserUpdateRequestDTO (
//    val userImage : MultipartBody,
    val userNickName : String,
    val userGender : String,
    val userBirthDate : String //yyyy-MM-DD 형식
) {
}