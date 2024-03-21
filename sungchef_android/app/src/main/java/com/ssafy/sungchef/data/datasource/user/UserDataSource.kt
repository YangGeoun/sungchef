package com.ssafy.sungchef.data.datasource.user

import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import retrofit2.Response

interface UserDataSource {

    suspend fun duplicateNickname(nickname : String) : Response<APIError>
    suspend fun userSimple() : UserSimple
    suspend fun makeRecipeList(page : Int) : MakeRecipeList;
}