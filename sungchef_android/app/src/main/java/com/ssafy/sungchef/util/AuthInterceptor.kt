package com.ssafy.sungchef.util

import android.util.Log
import com.ssafy.sungchef.domain.repository.UserDataStoreRepository
import com.ssafy.sungchef.domain.usecase.token.GetTokenUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

private const val TAG = "AuthInterceptor_성식당"
class AuthInterceptor @Inject constructor(
    private val getToken : GetTokenUseCase
) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        var accessToken = runBlocking {
            Log.d(TAG, "refreshToken: ${getToken.getToken()?.refreshToken}")
            getToken.getToken()?.accessToken ?: ""
        }
        Log.d(TAG, "intercept: $accessToken")
        accessToken = "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzNDA3MjM0Mzg5IiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTcxMjI0MTM0M30.9J82v9aRTs38wNqay4i9-jB9XNPWcDdJm57SMfQ21Ug"
        if (accessToken.isNotEmpty()) {
            request = chain.request()
                .newBuilder()
                .addHeader("Authorization", accessToken)
                .build()
        }

        return chain.proceed(request)
    }
}