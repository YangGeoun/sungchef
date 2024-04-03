package com.ssafy.sungchef.data.repository

import com.google.gson.Gson
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.datasource.cooking.CookingDataSource
import com.ssafy.sungchef.data.mapper.ingredient.toLackIngredient
import com.ssafy.sungchef.data.mapper.user.toBaseModel
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.model.ingredient.LackIngredient
import com.ssafy.sungchef.domain.repository.CookingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


class CookingRepositoryImpl @Inject constructor(
    private val cookingDataSource: CookingDataSource
) : CookingRepository {
    override suspend fun getLackIngredient(id: Int): Flow<DataState<LackIngredient>> =
        flow {
            when (val ingredient = cookingDataSource.getLackIngredient(id)) {
                is DataState.Success -> {
                    emit(DataState.Success(ingredient.data.data.toLackIngredient()))
                }

                is DataState.Loading -> {
                    emit(DataState.Loading())
                }

                is DataState.Error -> {
                    emit(DataState.Error(APIError(ingredient.apiError.code,"부족한 재료 없음")))
                }
            }
        }.onStart { emit(DataState.Loading()) }

    override suspend fun getUsedIngredient(id: Int): Flow<DataState<LackIngredient>> =
        flow {
            when (val ingredient = cookingDataSource.getUsedIngredient(id)) {
                is DataState.Success -> {
                    emit(DataState.Success(ingredient.data.data.toLackIngredient()))
                }

                is DataState.Loading -> {
                    emit(DataState.Loading())
                }

                is DataState.Error -> {
                    emit(DataState.Error(ingredient.apiError))
                }
            }
        }.onStart { emit(DataState.Loading()) }

    override suspend fun registerCook(
        file: File,
        id: Int,
        review: String
    ): Flow<DataState<BaseModel>> {
        val image = file.asRequestBody("image/*".toMediaTypeOrNull())
        val requestImage = MultipartBody.Part.createFormData("makeRecipeImage", file.name, image)
        val recipeId:RequestBody =
            RequestBody.create("application/json".toMediaTypeOrNull(), id.toString())
        val makeRecipeReview =
            RequestBody.create("application/json".toMediaTypeOrNull(), review)
        val requestMap= hashMapOf("recipeId" to recipeId,"makeRecipeReview" to makeRecipeReview)
        return flow {
            when (val result =
                cookingDataSource.registerCook(requestImage, requestMap )) {
                is DataState.Success -> {
                    emit(DataState.Success(result.data.toBaseModel()))
                }

                is DataState.Loading -> {
                    emit(DataState.Loading())
                }

                is DataState.Error -> {
                    emit(DataState.Error(result.apiError))
                }
            }
        }.onStart { emit(DataState.Loading()) }
    }
}
