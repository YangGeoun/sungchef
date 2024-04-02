package com.ssafy.sungchef.domain.repository

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.requestdto.RegisterCookingDTO
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.model.ingredient.IngredientInfo
import com.ssafy.sungchef.domain.model.ingredient.LackIngredient
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface CookingRepository {
    suspend fun getLackIngredient(id: Int): Flow<DataState<LackIngredient>>
    suspend fun getUsedIngredient(id: Int): Flow<DataState<LackIngredient>>
    suspend fun registerCook(
        file: File,
        id: Int,
        review: String
    ): Flow<DataState<BaseModel>>
}