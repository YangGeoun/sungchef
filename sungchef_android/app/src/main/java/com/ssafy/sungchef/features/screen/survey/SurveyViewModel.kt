package com.ssafy.sungchef.features.screen.survey

import androidx.lifecycle.ViewModel
import com.ssafy.sungchef.domain.usecase.survey.SubmitSurveyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val submitSurveyUseCase: SubmitSurveyUseCase
) : ViewModel() {

    private val _isNextPage = MutableStateFlow(false)
    val isNextPage : StateFlow<Boolean> = _isNextPage

    fun submitSurvey() {

    }
}