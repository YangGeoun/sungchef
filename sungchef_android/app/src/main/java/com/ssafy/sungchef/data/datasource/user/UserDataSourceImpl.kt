package com.ssafy.sungchef.data.datasource.user

import com.ssafy.sungchef.data.api.UserService
import com.ssafy.sungchef.data.model.APIError
import retrofit2.Response
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService : UserService
) : UserDataSource{
    override suspend fun duplicateNickname(nickname: String): Response<APIError> {
        return userService.duplicateNickname(nickname)
    }
}