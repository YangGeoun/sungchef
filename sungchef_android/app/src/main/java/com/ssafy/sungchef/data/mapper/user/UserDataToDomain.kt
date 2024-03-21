package com.ssafy.sungchef.data.mapper.user

import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.domain.model.base.BaseModel

fun APIError.toBaseModel() : BaseModel{
    return when (this.code) {
        200L -> BaseModel(
            true,
            "사용 가능한 닉네임 입니다."
        )
        409L -> BaseModel(
            false,
            "이미 존재하는 닉네임 입니다."
        )
        else -> BaseModel(
            false,
            "서버와의 연결이 불안정 합니다.\n잠시 후에 다시 시도해주세요."
        )
    }
}


