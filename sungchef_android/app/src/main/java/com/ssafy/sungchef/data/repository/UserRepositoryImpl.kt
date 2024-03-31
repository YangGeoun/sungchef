package com.ssafy.sungchef.data.repository

import android.util.Log
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.datasource.user.UserDataSource
import com.ssafy.sungchef.data.mapper.survey.toSurvey
import com.ssafy.sungchef.data.mapper.survey.toSurveyRequestDto
import com.ssafy.sungchef.data.mapper.user.toBaseModel
import com.ssafy.sungchef.data.mapper.user.toJwtToken
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.mapper.user.toLoginState
import com.ssafy.sungchef.data.model.requestdto.BookMarkRequest
import com.ssafy.sungchef.data.model.requestdto.ContactRequestDTO
import com.ssafy.sungchef.data.model.requestdto.UserRequestDTO
import com.ssafy.sungchef.data.model.requestdto.UserSnsIdRequestDTO
import com.ssafy.sungchef.data.model.requestdto.SurveyRequestDTO
import com.ssafy.sungchef.data.model.requestdto.UserUpdateRequestDTO
import com.ssafy.sungchef.data.model.responsedto.BookmarkRecipeList
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList
import com.ssafy.sungchef.data.model.responsedto.UserSettingInfo
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.model.survey.Survey
import com.ssafy.sungchef.domain.model.user.LoginState
import com.ssafy.sungchef.domain.repository.UserDataStoreRepository
import com.ssafy.sungchef.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.Response


import javax.inject.Inject

private const val TAG = "UserRepositoryImpl_성식당"
class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val userDataStoreRepository: UserDataStoreRepository
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
            val token = userDataSource.surveySubmit(selectSurveyList.toSurveyRequestDto())

            if (token is DataState.Success) {
                Log.d(TAG, "surveySubmit: 설문제출성공")
                userDataStoreRepository.setToken(token.data.data.toJwtToken())
                emit(DataState.Success(true))
            } else if (token is DataState.Error) {
                Log.d(TAG, "surveySubmit: 설문 제출 실패, ${token.apiError}")
                emit(DataState.Error(token.apiError))
            }
        }
    }

    override suspend fun getSurvey(): Flow<DataState<Survey>> {
        return flow {
            val surveyList = userDataSource.getSurvey()

            if (surveyList is DataState.Success) {
                emit(DataState.Success(surveyList.data.data.toSurvey()))
            } else if (surveyList is DataState.Error) {
                Log.d(TAG, "fail: ${surveyList.apiError}")
                emit(DataState.Error(surveyList.apiError))
            }
        }
    }
    override suspend fun userSettingInfo(): UserSettingInfo {
        return userDataSource.userSettingInfo()
    }

    override suspend fun inquire(contactRequestDTO: ContactRequestDTO) : Response<APIError> {
        return userDataSource.inquire(contactRequestDTO)
    }
    override suspend fun updateUserInfo(userImage : MultipartBody.Part?, userUpdateRequestDTO: UserUpdateRequestDTO) : Response<APIError> {
        return userDataSource.updateUserInfo(userImage, userUpdateRequestDTO)
    }

    override suspend fun signupUser(userRequestDTO: UserRequestDTO): Flow<DataState<Int>> {
        return flow {
            val token = userDataSource.signupUser(userRequestDTO)

            if (token is DataState.Success) {
                userDataStoreRepository.setToken(token.data.data.toJwtToken())
                emit(DataState.Success(token.data.code))
            } else if (token is DataState.Error){
                emit(DataState.Error(token.apiError))
            }
        }
    }

    override suspend fun login(userSnsIdRequestDTO: UserSnsIdRequestDTO): Flow<DataState<LoginState>> {
        return flow {
            val tokenResponse = userDataSource.login(userSnsIdRequestDTO)

            if (tokenResponse is DataState.Success) {
                Log.d(TAG, "login: 로그인 성공 : ${tokenResponse.data.code}}")
                userDataStoreRepository.setToken(tokenResponse.data.data.toJwtToken())
                emit(DataState.Success(tokenResponse.data.toLoginState()))
            } else if (tokenResponse is DataState.Error) {
                emit(DataState.Error(tokenResponse.apiError))
            }
        }

    }
}