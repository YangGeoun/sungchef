package com.ssafy.sungchef.data.repository

import com.ssafy.sungchef.data.datasource.user.UserDataSource
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import com.ssafy.sungchef.domain.repository.UserRepository
import retrofit2.Response

import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository{
    override suspend fun duplicateNickname(nickname: String): Response<APIError> {
        return userDataSource.duplicateNickname(nickname)
    }

    override suspend fun userSimple(): UserSimple {
        return userDataSource.userSimple();
    }

    override suspend fun makeRecipeList(page : Int) : MakeRecipeList{
        return userDataSource.makeRecipeList(page)
    }
}