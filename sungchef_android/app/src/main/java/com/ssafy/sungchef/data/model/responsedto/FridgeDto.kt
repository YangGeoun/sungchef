package com.ssafy.sungchef.data.model.responsedto

data class FridgeData(
    val ingredientInfoList : List<IngredientInfoList>,
)

data class IngredientInfoList(
    val ingredientType : String,
    val ingredientResList : List<IngredientListData>
)
data class IngredientListData(
    val ingredientName : String,
    val ingredientId : Int
)