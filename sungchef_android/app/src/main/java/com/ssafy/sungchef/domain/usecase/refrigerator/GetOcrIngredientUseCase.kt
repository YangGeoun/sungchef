package com.ssafy.sungchef.domain.usecase.refrigerator

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.responsedto.ocr.ConvertInfo
import com.ssafy.sungchef.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOcrIngredientUseCase @Inject constructor(
    private val refrigeratorRepository: RefrigeratorRepository
) {
    suspend fun getOcrIngredient(convertOCRKey : String) : Flow<DataState<Map<String, List<ConvertInfo>>>> {
        return refrigeratorRepository.getOcrConvert(convertOCRKey)
    }
}