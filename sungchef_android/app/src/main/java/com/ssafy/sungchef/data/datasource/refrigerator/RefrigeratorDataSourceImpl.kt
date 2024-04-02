package com.ssafy.sungchef.data.datasource.refrigerator

import android.util.Log
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.api.RefrigeratorService
import com.ssafy.sungchef.data.datasource.BaseRemoteDataSource
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.responsedto.FridgeData
import com.ssafy.sungchef.data.model.requestdto.IngredientRequestDTO
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.search.SearchIngredientResponse
import com.ssafy.sungchef.data.model.responsedto.ocr.OcrResponse

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

    override suspend fun deleteFridgeIngredientList(): DataState<APIError> {
        return getResult {
            refrigeratorService.deleteFridgeIngredientList()
        }
    }

    override suspend fun registerIngredient(ingredientRequestDTO: IngredientRequestDTO): DataState<APIError> {
        return getResult {
            refrigeratorService.registerIngredient(ingredientRequestDTO)
        }
    }

    override suspend fun registerReceipt(convertImage: MultipartBody.Part?): DataState<ResponseDto<String>> {
        return getResult {
            refrigeratorService.registerReceipt(convertImage)
        }
    }

    override suspend fun getOcrConvert(convertOCRKey: String): DataState<ResponseDto<OcrResponse>> {
        return getResult {
            refrigeratorService.getOcrConvert(convertOCRKey)
        }
    }
}