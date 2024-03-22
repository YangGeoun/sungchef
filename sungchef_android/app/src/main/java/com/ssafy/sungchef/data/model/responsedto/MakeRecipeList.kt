package com.ssafy.sungchef.data.model.responsedto

data class MakeRecipeList (
    val code : Int,
    val message : String,
    val data : MakeRecipeListData
)

data class MakeRecipeListData(
    val makeRecipeCount: Int,
    val makeRecipeList : List<MakeRecipeDetailList>

)

data class MakeRecipeDetailList(
    val makeRecipeImage : String,
    val makeRecipeCreateDate : String,
    val makeRecipeReview : String
)