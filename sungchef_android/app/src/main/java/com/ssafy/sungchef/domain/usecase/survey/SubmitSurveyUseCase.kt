package com.ssafy.sungchef.domain.usecase.survey

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.requestdto.SurveyRequestDTO
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubmitSurveyUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun submitSurvey(surveyRequestDTO: SurveyRequestDTO) : Flow<DataState<Boolean>> {
        return userRepository.surveySubmit(surveyRequestDTO)
    }
}