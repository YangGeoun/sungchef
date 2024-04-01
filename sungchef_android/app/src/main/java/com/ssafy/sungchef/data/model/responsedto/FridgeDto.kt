package com.ssafy.sungchef.data.model.responsedto

data class FridgeDto (
    val code : Int,
    val message : String,
    val data : FridgeData
)

data class FridgeData(
    val ingredientInfoList : List<IngredientInfoList>,
)

data class IngredientInfoList(
    val ingredientType : String,
    val ingredientList : List<IngredientListData>
)
data class IngredientListData(
    val ingredientName : String,
    val ingredientId : Int
)