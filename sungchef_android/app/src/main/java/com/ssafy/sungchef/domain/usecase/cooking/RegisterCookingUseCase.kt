package com.ssafy.sungchef.domain.usecase.cooking

import com.google.gson.Gson
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.requestdto.RegisterCookingDTO
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.repository.CookingRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class RegisterCookingUseCase @Inject constructor(
    private val cookingRepository: CookingRepository
) {
    suspend operator fun invoke(file: File, id: Int, review: String): Flow<DataState<BaseModel>> {
        return cookingRepository.registerCook(file, id, review)
    }
}