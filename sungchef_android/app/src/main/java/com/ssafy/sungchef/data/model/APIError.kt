package com.ssafy.sungchef.data.model

import androidx.compose.runtime.Stable

@Stable
data class APIError(val code: Long, val message:String)
