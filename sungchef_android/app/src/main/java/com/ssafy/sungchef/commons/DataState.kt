package com.ssafy.sungchef.commons

import com.ssafy.sungchef.data.model.APIError

sealed class DataState<T> {
    class Success<T>(val data: T): DataState<T>()
    class Loading<T>: DataState<T>()
    class Error<T>(val apiError: APIError): DataState<T>()
}