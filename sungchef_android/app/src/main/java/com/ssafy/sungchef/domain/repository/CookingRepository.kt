package com.ssafy.sungchef.domain.repository

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.ingredient.IngredientInfo
import com.ssafy.sungchef.domain.model.ingredient.LackIngredient
import kotlinx.coroutines.flow.Flow

interface CookingRepository {
    suspend fun getLackIngredient(id: Int): Flow<DataState<LackIngredient>>
    suspend fun getUsedIngredient(id:Int): Flow<DataState<LackIngredient>>
}