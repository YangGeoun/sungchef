package com.ssafy.sungchef.data.datasource

import android.util.Log
import com.google.gson.Gson
import com.ssafy.sungchef.commons.ALREADY_NICKNAME
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.commons.NEED_SIGNUP
import com.ssafy.sungchef.commons.NEED_SURVEY
import com.ssafy.sungchef.commons.SERVER_INSTABILITY
import retrofit2.Response

private const val TAG = "BaseRemoteDataSource_성식당"

open class BaseRemoteDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): DataState<T> {
        val response = call()
        return try {
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null)DataState.Success(body)
//                    DataState.Error(APIError(
//                        404L,
//                        NEED_SURVEY
//                    ))
                else {
                    DataState.Error(APIError(response.code().toLong(), "오류가 발생했습니다."))
                }
            } else {
                when (response.code()) {
                    403 -> {
                        val apiError = APIError(
                            403L,
                            NEED_SURVEY
                        )
                        DataState.Error(apiError)
                    }

                    404 -> {
                        val apiError = APIError(
                            404L,
                            NEED_SIGNUP
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

                    500 -> {
                        val apiError = APIError(
                            500L,
                            SERVER_INSTABILITY
                        )
                        DataState.Error(apiError)
                    }

                    else -> {
                        DataState.Error(APIError(response.code().toLong(), "오류가 발생했습니다."))
                    }
                }
            }
        } catch (e: Exception) {
            DataState.Error(APIError(-1, "오류가 발생했습니다."))
        }
    }
}