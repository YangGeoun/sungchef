package com.ssafy.sungchef.data.model.requestdto

data class UserRequestDTO(
    val userSnsId : String = "id", // sdk 제공 id, 임시 id
    val userSnsType : String = "Kakao", // Naver or Kakao -> String인데, 2개로 제한
    val userNickName : String, // 최소 2글자, 최대 10글자, 중복 확인 필요
    val userGender : String, // M or F -> 선택지 2개로 제한
    val userBirthdate : String // yyyy-MM-DD 형식
)
