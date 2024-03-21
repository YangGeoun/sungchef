package com.ssafy.sungchef.data.repository

import com.ssafy.sungchef.data.datasource.user.UserDataSource
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.domain.repository.UserRepository
import retrofit2.Response

import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository{
    override suspend fun duplicateNickname(nickname: String): Response<APIError> {
        return userDataSource.duplicateNickname(nickname)
    }
}