package com.ssafy.sungchef.data.mapper.survey

import android.util.Log
import com.ssafy.sungchef.data.model.responsedto.survey.SurveyInfoResponse
import com.ssafy.sungchef.data.model.responsedto.survey.SurveyResponse
import com.ssafy.sungchef.domain.model.survey.Survey
import com.ssafy.sungchef.domain.model.survey.SurveyInfo
import com.ssafy.sungchef.features.screen.mypage.Logout

private const val TAG = "SurveyDataToDomain_성식당"
fun SurveyResponse.toSurvey() : Survey {
    Log.d(TAG, "toSurvey: ${this.surveyList}")
    return Survey(
        this.surveyList.map {
            it.toSurveyInfo()
        }.toMutableList()
    )
}

fun SurveyInfoResponse.toSurveyInfo() : SurveyInfo {
    return SurveyInfo(
        this.foodId,
        this.foodImage,
        this.foodName
    )
}