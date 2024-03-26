package com.ssafy.sungchef.features.screen.survey

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.ALREADY_NICKNAME
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.commons.SERVER_INSTABILITY
import com.ssafy.sungchef.commons.SURVEY_CERTIFICATION_NUMBER
import com.ssafy.sungchef.commons.WRONG_NICKNAME_FORMAT
import com.ssafy.sungchef.domain.model.survey.Survey
import com.ssafy.sungchef.domain.model.survey.SurveyInfo
import com.ssafy.sungchef.domain.usecase.survey.GetSurveyUseCase
import com.ssafy.sungchef.domain.usecase.survey.SubmitSurveyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "SurveyViewModel_성식당"
@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val submitSurveyUseCase: SubmitSurveyUseCase,
    private val getSurveyUseCase : GetSurveyUseCase
) : ViewModel() {

    private val _surveyList = MutableStateFlow(mutableListOf<SurveyInfo>())
    val surveyList : StateFlow<MutableList<SurveyInfo>> = _surveyList

    private val _errorMessage = MutableStateFlow("")
    val errorMessage : StateFlow<String> = _errorMessage

    private val _isSurveySuccess = MutableStateFlow(false)
    val isSurveySuccess : StateFlow<Boolean> = _isSurveySuccess

    fun submitSurvey(selectSurveyList : List<Int>) {
        viewModelScope.launch {
            submitSurveyUseCase.submitSurvey(selectSurveyList).collect {
                when (it) {
                    is DataState.Success -> {
                        _isSurveySuccess.emit(true)
                    }

                    is DataState.Error -> {
                        _errorMessage.emit(
                            failSurveyMessage(it.apiError.code)
                        )
                    }

                    is DataState.Loading -> {

                    }
                }
            }
        }
    }

    fun getSurveyList() {
        viewModelScope.launch {
            getSurveyUseCase.getSurveyList().collect{
                when (it) {
                    is DataState.Success -> {
                        Log.d(TAG, "getSurveyList: ${it.data.surveyList}")
                        _surveyList.emit(it.data.surveyList)
                    }

                    is DataState.Error -> {
                        _errorMessage.emit(it.apiError.message)
                    }

                    is DataState.Loading -> {

                    }
                }
            }
        }
    }

    private fun failSurveyMessage(code : Long) : String {
        return when (code) {
            // 에러코드에 따른 메시지
            400L -> SURVEY_CERTIFICATION_NUMBER
            else -> SERVER_INSTABILITY
        }
    }
}