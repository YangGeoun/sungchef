package com.ssafy.sungchef.data.datasource.refrigerator

import android.util.Log
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.api.RefrigeratorService
import com.ssafy.sungchef.data.datasource.BaseRemoteDataSource
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.responsedto.FridgeData
import com.ssafy.sungchef.data.model.requestdto.IngredientRequestDTO
import com.ssafy.sungchef.data.model.requestdto.RecipeRequest
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.search.SearchIngredientResponse

import okhttp3.MultipartBody

import retrofit2.Response

import javax.inject.Inject

class RefrigeratorDataSourceImpl @Inject constructor(
    private val refrigeratorService: RefrigeratorService
) : RefrigeratorDataSource, BaseRemoteDataSource() {
    override suspend fun searchIngredient(ingredientName: String): DataState<ResponseDto<List<SearchIngredientResponse>>> {
        return getResult {
            refrigeratorService.searchIngredient(ingredientName)
        }
    }

    override suspend fun getFridgeIngredientList(): DataState<ResponseDto<FridgeData>> {
        return getResult {
            Log.d("TAG", "Data getFridgeIngredientList: ")
            refrigeratorService.getFridgeIngredientList()
        }
    }


    override suspend fun registerIngredient(ingredientRequestDTO: IngredientRequestDTO): DataState<APIError> {
        return getResult {
            refrigeratorService.registerIngredient(ingredientRequestDTO)
        }
    }

    override suspend fun deleteIngredient(ingredientRequestDTO: IngredientRequestDTO): DataState<APIError> =
        getResult { refrigeratorService.deleteIngredient(ingredientRequestDTO) }

    override suspend fun registerNeedIngredient(recipeRequest: RecipeRequest): DataState<APIError> =
        getResult { refrigeratorService.registerNeedIngredient(recipeRequest) }


    override suspend fun registerReceipt(convertImage: MultipartBody.Part?): DataState<ResponseDto<String>> {
        return getResult {
            refrigeratorService.registerReceipt(convertImage)
        }
    }
}