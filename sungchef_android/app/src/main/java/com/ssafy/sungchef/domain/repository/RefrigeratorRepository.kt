package com.ssafy.sungchef.domain.repository

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.requestdto.IngredientRequestDTO
import com.ssafy.sungchef.data.model.responsedto.FridgeData
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.search.SearchIngredientResponse
import com.ssafy.sungchef.data.model.responsedto.ocr.ConvertInfo
import com.ssafy.sungchef.domain.model.refrigerator.RegisterReceiptState
import com.ssafy.sungchef.domain.model.refrigerator.SearchIngredient
import kotlinx.coroutines.flow.Flow
import java.io.File

interface RefrigeratorRepository {

    suspend fun searchIngredient(ingredientName : String) : Flow<DataState<List<SearchIngredient>>>

    suspend fun registerIngredient(ingredientIdList : List<Int>) : Flow<DataState<APIError>>

    suspend fun registerReceipt(imageFile : File) : Flow<DataState<RegisterReceiptState>>

    suspend fun getFridgeIngredientList() : Flow<DataState<ResponseDto<FridgeData>>>
    suspend fun deleteFridgeIngredientList() : Flow<DataState<APIError>>

    suspend fun getOcrConvert(convertOCRKey : String) : Flow<DataState<Map<String, List<ConvertInfo>>>>
}