package com.ssafy.sungchef.data.api

import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.token.TokenResponse
import com.ssafy.sungchef.domain.model.token.JwtToken
import com.ssafy.sungchef.domain.model.token.RefreshToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {
    @POST("user/reissue")
    suspend fun reissueToken(
        @Body refreshToken: RefreshToken
    ) : Response<ResponseDto<TokenResponse>>
}