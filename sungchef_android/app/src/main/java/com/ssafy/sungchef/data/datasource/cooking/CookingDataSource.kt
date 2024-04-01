package com.ssafy.sungchef.data.datasource.cooking

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.requestdto.RegisterCookingDTO
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.IngredientListResponse
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeStepResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface CookingDataSource {
    suspend fun getLackIngredient(id: Int): DataState<ResponseDto<IngredientListResponse>>
    suspend fun getUsedIngredient(id: Int): DataState<ResponseDto<IngredientListResponse>>
    suspend fun getRecipeStep(id:Int):DataState<ResponseDto<RecipeStepResponse>>
    suspend fun registerCook(
        file: MultipartBody.Part?,
        map: HashMap<String,RequestBody>
    ): DataState<APIError>
}