package com.ssafy.sungchef.data.datasource

import android.util.Log
import com.google.gson.Gson
import com.ssafy.sungchef.commons.ALREADY_NICKNAME
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.commons.DataState
import retrofit2.Response

private const val TAG = "BaseRemoteDataSource_성식당"
open class BaseRemoteDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): DataState<T> {
        val response = call()
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) DataState.Success(body)
            else {
                val apiError: APIError =
                    Gson().fromJson(response.errorBody()?.charStream(), APIError::class.java)
                DataState.Error(apiError)
            }
        } else {
            when (response.code()) {
                400 -> {
                    val apiError = APIError(
                        400L,
                        "올바른 데이터를 입력해주세요."
                    )
                    DataState.Error(apiError)
                }
                409 -> {
                    val apiError = APIError(
                        409L,
                        ALREADY_NICKNAME
                    )
                    DataState.Error(apiError)
                }
                else -> {
                    val apiError: APIError =
                        Gson().fromJson(response.errorBody()?.charStream(), APIError::class.java)
                    DataState.Error(apiError)
                }
            }
        }
    }
}