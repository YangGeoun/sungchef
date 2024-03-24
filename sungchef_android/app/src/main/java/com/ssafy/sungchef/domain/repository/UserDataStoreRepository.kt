package com.ssafy.sungchef.domain.repository

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.requestdto.UserRequestDTO
import com.ssafy.sungchef.data.model.responsedto.token.TokenResponse
import com.ssafy.sungchef.domain.model.JwtToken
import kotlinx.coroutines.flow.Flow

interface UserDataStoreRepository {
    suspend fun setToken(token : JwtToken)
    suspend fun getToken() : JwtToken?

    suspend fun signupUser(userRequestDTO: UserRequestDTO) : Flow<DataState<Int>>
}