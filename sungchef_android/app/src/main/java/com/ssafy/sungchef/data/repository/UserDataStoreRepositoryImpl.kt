package com.ssafy.sungchef.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ssafy.sungchef.domain.model.token.JwtToken
import com.ssafy.sungchef.domain.repository.UserDataStoreRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "UserDataStoreRepository_성식당"
class UserDataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserDataStoreRepository {

    // JWT Token 관리
    override suspend fun setToken(token: JwtToken) {
        Log.d(TAG, "setToken: 토큰 저장 $token")
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

    override suspend fun deleteToken() {
        dataStore.edit { preference ->
            preference.remove(ACCESS_TOKEN_KEY)
            preference.remove(REFRESH_TOKEN_KEY)
        }
    }

    // 소셜 로그인 타입 관리
    override suspend fun setLoginType(loginType: String) {
        dataStore.edit {
            it[LOGIN_TYPE] = loginType
        }
    }

    override suspend fun getLoginType(): String? {
        return dataStore.data.map { preference ->
            val loginType = preference[LOGIN_TYPE] ?: ""
            loginType
        }.firstOrNull()
    }
    override suspend fun setEmail(email: String) {
        dataStore.edit {
            it[EMAIL_KEY] = email
        }
    }

    override suspend fun getEmail(): String? {
        return dataStore.data.map{ preference ->
            val email = preference[EMAIL_KEY] ?: ""
            email
        }.firstOrNull()
    }
    companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")

        val LOGIN_TYPE = stringPreferencesKey("login_type")
        val EMAIL_KEY = stringPreferencesKey("email")
    }
}