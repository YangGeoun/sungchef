package com.ssafy.sungchef.data.datasource

import com.google.gson.Gson
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.commons.DataState
import retrofit2.Response

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
            val apiError: APIError =
                Gson().fromJson(response.errorBody()?.charStream(), APIError::class.java)
            DataState.Error(apiError)
        }
    }
}