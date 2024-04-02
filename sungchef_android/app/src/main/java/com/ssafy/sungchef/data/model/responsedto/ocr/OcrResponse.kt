package com.ssafy.sungchef.data.model.responsedto.ocr

data class OcrResponse(
    val convertProductList : List<ConvertIngredient>
)

data class ConvertIngredient(
    val ingredientType : String,
    val convertProductList : List<ConvertInfo>
)

data class ConvertInfo(
    val convertedName : String,
    val ingredientId : Int,
    val converted : Boolean
)


