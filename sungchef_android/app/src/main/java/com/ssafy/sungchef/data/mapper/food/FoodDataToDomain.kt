package com.ssafy.sungchef.data.mapper.food

import com.ssafy.sungchef.data.model.responsedto.food.FoodResponse
import com.ssafy.sungchef.domain.model.food.FoodName

fun FoodResponse.toFoodName(): FoodName =
    FoodName(this.foodName, this.foodId)