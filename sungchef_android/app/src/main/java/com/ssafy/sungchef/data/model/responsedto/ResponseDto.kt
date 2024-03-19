package com.ssafy.sungchef.data.model.responsedto

data class ResponseDto<T>(
    val code: Int,
    val message: String,
    val data: T
)
