package com.ssafy.sungchef.data.model.requestdto

import okhttp3.MultipartBody
import okhttp3.RequestBody

data class RegisterCookingDTO(
    val recipeId: Int,
    val makeRecipeReview: String,
    val makeRecipeImage: MultipartBody.Part?
)