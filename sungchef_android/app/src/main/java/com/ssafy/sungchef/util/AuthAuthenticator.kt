package com.ssafy.sungchef.util

import android.util.Log
import com.ssafy.sungchef.data.api.TokenService
import com.ssafy.sungchef.domain.model.token.JwtToken
import com.ssafy.sungchef.domain.model.token.RefreshToken
import com.ssafy.sungchef.domain.repository.UserDataStoreRepository
import com.ssafy.sungchef.domain.usecase.token.DeleteTokenUseCase
import com.ssafy.sungchef.domain.usecase.token.GetTokenUseCase
import com.ssafy.sungchef.domain.usecase.token.SetTokenUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

private const val TAG = "AuthAuthenticator_성식당"
class AuthAuthenticator @Inject constructor(
    private val tokenService : TokenService,
    private val getTokenUseCase: GetTokenUseCase,
    private val setTokenUseCase: SetTokenUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = runBlocking {
            Log.d(TAG, "authenticate: ")
            getTokenUseCase.getToken()?.refreshToken
        }

        if (refreshToken == null) {
            response.close()
            return null
        }

        return runBlocking {
            val tokenResponse = tokenService.reissueToken(refreshToken)

            if (!tokenResponse.isSuccessful || tokenResponse.body() == null) {
                // 토큰 재갱신 실패하면 기존 토큰 삭제
                deleteTokenUseCase.deleteToken()
                null
            } else {
                val newAccessToken = tokenResponse.body()!!.data.accessToken
                val newRefreshToken = tokenResponse.body()!!.data.refreshToken
                Log.d(TAG, "재갱신 accessToken: $newAccessToken")
                Log.d(TAG, "재갱신 refreshToken: $newRefreshToken")

                // Datastore에 토큰 재 저장
                setTokenUseCase.setToken(
                    JwtToken(newAccessToken, newRefreshToken)
                )

                response.request.newBuilder()
                    .header("Authorization", tokenResponse.body()!!.data.accessToken)
                    .build()
            }
        }
    }
}