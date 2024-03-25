package com.ssafy.sungchef.domain.repository

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.responsedto.BookmarkRecipeList
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList
import com.ssafy.sungchef.data.model.responsedto.UserSettingInfo
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import com.ssafy.sungchef.domain.model.base.BaseModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UserRepository {
    suspend fun duplicateNickname(nickname : String) : Flow<DataState<BaseModel>>
    suspend fun userSimple() : UserSimple

    suspend fun makeRecipeList(page : Int) : MakeRecipeList
    suspend fun bookmarkRecipeList(page : Int) : BookmarkRecipeList
    suspend fun changeBookmarkRecipe(recipeId:Int, isBookmark:Boolean): Flow<DataState<BaseModel>>
    suspend fun userSettingInfo() : UserSettingInfo

}