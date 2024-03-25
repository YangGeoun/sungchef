package com.ssafy.sungchef.data.datasource.user

import android.util.Log
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.api.UserService
import com.ssafy.sungchef.data.datasource.BaseRemoteDataSource
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.requestdto.BookMarkRequest
import com.ssafy.sungchef.data.model.responsedto.BookmarkRecipeList
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList
import com.ssafy.sungchef.data.model.responsedto.UserSettingInfo
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "UserDataSourceImpl_성식당"
class UserDataSourceImpl @Inject constructor(
    private val userService : UserService
) : UserDataSource, BaseRemoteDataSource(){
    override suspend fun duplicateNickname(nickname: String): DataState<APIError> {
        return try {
            getResult {
                userService.duplicateNickname(nickname)
            }
        } catch (e : Exception){
            Log.d(TAG, "duplicateNickname: ${e.message}")
            DataState.Error(APIError(500, ""))
        }
    }


    override suspend fun userSimple(): UserSimple {
        return userService.userSimple();
    }

    override suspend fun makeRecipeList(page : Int) : MakeRecipeList {
        return userService.makeRecipeList(page)
    }

    override suspend fun bookmarkRecipeList(page : Int) : BookmarkRecipeList {
        return userService.bookmarkRecipeList(page)
    }

    override suspend fun changeBookmarkRecipe(bookMarkRequest: BookMarkRequest): DataState<APIError> =
        getResult { userService.changeBookmarkRecipe(bookMarkRequest) }

    override suspend fun userSettingInfo(): UserSettingInfo {
        return userService.userSettingInfo()
    }

}