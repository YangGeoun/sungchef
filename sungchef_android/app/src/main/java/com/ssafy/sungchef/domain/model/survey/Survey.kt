package com.ssafy.sungchef.domain.model.survey

data class Survey(
    val surveyList : MutableList<SurveyInfo> = mutableListOf()
)

data class SurveyInfo(
    val foodId : Int,
    val foodImage : String,
    val foodName : String
)
