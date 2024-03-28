package com.ssafy.sungchef.data.model.responsedto

import androidx.compose.ui.graphics.painter.Painter

data class IngredientItem (
    val painter: Painter,
    val num : Int,
    val label : String
){
}