package com.ssafy.sungchef.data.datasource.token

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.api.UserService
import com.ssafy.sungchef.data.datasource.BaseRemoteDataSource
import com.ssafy.sungchef.data.model.requestdto.UserRequestDTO
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.token.TokenResponse
import javax.inject.Inject

class TokenDataSourceImpl @Inject constructor(
    private val userService: UserService
) : TokenDataSource, BaseRemoteDataSource() {
    override suspend fun signupUser(userRequestDTO: UserRequestDTO): DataState<ResponseDto<TokenResponse>> {
        return getResult {
            userService.signupUser(userRequestDTO)
        }
    }
}