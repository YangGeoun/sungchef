package com.ssafy.sungchef.domain.repository

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.requestdto.UserRequestDTO
import com.ssafy.sungchef.data.model.requestdto.UserSnsIdRequestDTO
import com.ssafy.sungchef.domain.model.token.JwtToken
import com.ssafy.sungchef.domain.model.user.LoginState
import kotlinx.coroutines.flow.Flow

interface UserDataStoreRepository {
    suspend fun setToken(token : JwtToken)
    suspend fun getToken() : JwtToken?

    suspend fun deleteToken()

    suspend fun setLoginType(loginType : String)

    suspend fun getLoginType() : String?

    suspend fun setEmail(email: String)
    suspend fun getEmail() : String?
}