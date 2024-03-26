package com.ssafy.sungchef.domain.usecase.survey

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.survey.Survey
import com.ssafy.sungchef.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSurveyUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun getSurveyList() : Flow<DataState<Survey>> {
        return userRepository.getSurvey()
    }

}