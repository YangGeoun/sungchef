package com.ssafy.sungchef.data.datasource.cooking

import android.util.Log
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.api.CookingService
import com.ssafy.sungchef.data.datasource.BaseRemoteDataSource
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.requestdto.RegisterCookingDTO
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.IngredientListResponse
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeStepResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class CookingDataSourceImpl @Inject constructor(
    private val cookingService: CookingService
) : CookingDataSource, BaseRemoteDataSource() {
    override suspend fun getLackIngredient(id: Int): DataState<ResponseDto<IngredientListResponse>> =
        getResult { cookingService.getLackIngredient(id) }

    override suspend fun getUsedIngredient(id: Int): DataState<ResponseDto<IngredientListResponse>> =
        getResult { cookingService.getUsedIngredient(id) }

    override suspend fun getRecipeStep(id: Int): DataState<ResponseDto<RecipeStepResponse>> =
        getResult { cookingService.getRecipeStep(id) }

    override suspend fun registerCook(
        file: MultipartBody.Part?,
        map: HashMap<String,RequestBody>
    ): DataState<APIError> {
        val data = cookingService.registerCooking(file, params = map)
        return if (data.isSuccessful) {
            if (data.body() != null) DataState.Success(data.body()!!)
            else DataState.Error(APIError(-1, "오류입니다."))
        } else {
            DataState.Error(APIError(-1, "오류입니다."))
        }
    }
}