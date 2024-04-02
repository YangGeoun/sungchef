package com.ssafy.sungchef.domain.model.refrigerator

data class RegisterReceiptState(
    val isLoading :Boolean = false,
    val code : Int = 0,
    val dialogTitle : String = "",
    val imageUrl : String = ""
)
