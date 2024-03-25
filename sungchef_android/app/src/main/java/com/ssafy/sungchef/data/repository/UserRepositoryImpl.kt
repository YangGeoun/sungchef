package com.ssafy.sungchef.data.repository

import android.util.Log
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.datasource.user.UserDataSource
import com.ssafy.sungchef.data.mapper.survey.toSurvey
import com.ssafy.sungchef.data.mapper.survey.toSurveyRequestDto
import com.ssafy.sungchef.data.mapper.user.toBaseModel
import com.ssafy.sungchef.data.model.requestdto.BookMarkRequest
import com.ssafy.sungchef.data.model.requestdto.SurveyRequestDTO
import com.ssafy.sungchef.data.model.responsedto.BookmarkRecipeList
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.model.survey.Survey
import com.ssafy.sungchef.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


import javax.inject.Inject

private const val TAG = "UserRepositoryImpl_성식당"
class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository{
    override suspend fun duplicateNickname(nickname: String): Flow<DataState<BaseModel>> {
        return flow {
            val isDuplicate = userDataSource.duplicateNickname(nickname)

            if (isDuplicate is DataState.Success) {
                emit(DataState.Success(isDuplicate.data.toBaseModel()))
            } else if (isDuplicate is DataState.Error) {
                Log.d(TAG, "apiError: ${isDuplicate.apiError}")
                emit(DataState.Error(isDuplicate.apiError))
            }
        }
    }

    override suspend fun userSimple(): UserSimple {
        return userDataSource.userSimple();
    }

    override suspend fun makeRecipeList(page: Int): MakeRecipeList {
        return userDataSource.makeRecipeList(page)
    }

    override suspend fun bookmarkRecipeList(page: Int): BookmarkRecipeList {
        return userDataSource.bookmarkRecipeList(page)
    }

    override suspend fun changeBookmarkRecipe(
        recipeId: Int,
        isBookmark: Boolean
    ): Flow<DataState<BaseModel>> = flow {
        val bookmarkState =
            userDataSource.changeBookmarkRecipe(BookMarkRequest(recipeId, isBookmark))
        when (bookmarkState) {
            is DataState.Success -> {
                emit(DataState.Success(bookmarkState.data.toBaseModel()))
            }
            is DataState.Error -> {

            }
            is DataState.Loading -> {

            }
        }
    }

    override suspend fun surveySubmit(selectSurveyList : List<Int>): Flow<DataState<Boolean>> {
        return flow {
            val isSuccess = userDataSource.surveySubmit(selectSurveyList.toSurveyRequestDto())

            if (isSuccess is DataState.Success) {
                emit(DataState.Success(true))
            } else if (isSuccess is DataState.Error) {
                emit(DataState.Error(isSuccess.apiError))
            }
        }
    }

    override suspend fun getSubmit(): Flow<DataState<Survey>> {
        return flow {
            val surveyList = userDataSource.getSurvey()

            if (surveyList is DataState.Success) {
                Log.d(TAG, "success: $surveyList")
                emit(DataState.Success(surveyList.data.data.toSurvey()))
            } else if (surveyList is DataState.Error) {
                Log.d(TAG, "fail: $surveyList")
                emit(DataState.Error(surveyList.apiError))
            }
        }
    }
}