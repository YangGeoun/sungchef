package com.ssafy.sungchef.data.datasource.user

import com.ssafy.sungchef.data.api.UserService
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import retrofit2.Response
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService : UserService
) : UserDataSource{
    override suspend fun duplicateNickname(nickname: String): Response<APIError> {
        return userService.duplicateNickname(nickname)
    }

    override suspend fun userSimple(): UserSimple {
        return userService.userSimple();
    }

    override suspend fun makeRecipeList(page : Int) : MakeRecipeList {
        return userService.makeRecipeList(page)
    }
}