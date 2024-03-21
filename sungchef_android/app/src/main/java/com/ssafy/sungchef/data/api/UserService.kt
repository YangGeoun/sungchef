package com.ssafy.sungchef.data.api

import com.ssafy.sungchef.data.model.APIError
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("user/exist/{nickname}")
    suspend fun duplicateNickname(@Path("nickname") nickname: String): Response<APIError>
}