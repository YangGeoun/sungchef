package com.ssafy.sungchef.data.datasource.token

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.requestdto.UserRequestDTO
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.token.TokenResponse

interface TokenDataSource {
    // 회원가입 api
    suspend fun signupUser(userRequestDTO: UserRequestDTO) : DataState<ResponseDto<TokenResponse>>
}