package com.ssafy.sungchef.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.datasource.token.TokenDataSource
import com.ssafy.sungchef.data.mapper.user.toJwtToken
import com.ssafy.sungchef.data.model.requestdto.UserRequestDTO
import com.ssafy.sungchef.data.model.responsedto.token.TokenResponse
import com.ssafy.sungchef.domain.model.JwtToken
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.repository.UserDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "UserDataStoreRepository_성식당"
class UserDataStoreRepositoryImpl @Inject constructor(
    private val tokenDataSource: TokenDataSource,
    private val dataStore: DataStore<Preferences>
) : UserDataStoreRepository {
    override suspend fun setToken(token: JwtToken) {
        Log.d(TAG, "setToken: 토큰 저장")
        dataStore.edit {
            it[ACCESS_TOKEN_KEY] = token.accessToken
            it[REFRESH_TOKEN_KEY] = token.refreshToken
        }
    }

    // firstOrNull() : 첫번째 값만 방출
    override suspend fun getToken(): JwtToken? {
        return dataStore.data.map{ preference ->
            val accessToken = preference[ACCESS_TOKEN_KEY] ?: ""
            val refreshToken = preference[REFRESH_TOKEN_KEY] ?: ""
            JwtToken(accessToken, refreshToken)
        }.firstOrNull()
    }

    override suspend fun signupUser(userRequestDTO: UserRequestDTO): Flow<DataState<Int>> {
        return flow {
            val token = tokenDataSource.signupUser(userRequestDTO)

            if (token is DataState.Success) {
                setToken(token.data.data.toJwtToken())
                emit(DataState.Success(token.data.code))
            } else if (token is DataState.Error){
                emit(DataState.Error(token.apiError))
            }
        }
    }

    companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }
}