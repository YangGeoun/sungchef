package com.ssafy.sungchef.data.model.responsedto.survey

import com.google.gson.annotations.SerializedName

data class SurveyResponse(
    @SerializedName("foodInfoList")
    val surveyList : MutableList<SurveyInfoResponse>
)

data class SurveyInfoResponse(
    val foodId : Int,
    val foodImage : String,
    val foodName : String
)