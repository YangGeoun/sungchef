package com.ssafy.sungchef.data.mapper.recommendation

import com.ssafy.sungchef.data.model.responsedto.UserSettingInfoData
import com.ssafy.sungchef.data.model.responsedto.recommendation.RecommendedFoodResponse
import com.ssafy.sungchef.data.model.responsedto.recommendation.RecommendedFoodListResponse
import com.ssafy.sungchef.data.model.responsedto.recommendation.RecommendationResponse
import com.ssafy.sungchef.data.model.responsedto.recommendation.RecommendedRecipeListResponse
import com.ssafy.sungchef.data.model.responsedto.recommendation.RecommendedRecipeResponse
import com.ssafy.sungchef.domain.model.recommendation.RecommendedFood
import com.ssafy.sungchef.domain.model.recommendation.RecommendedRecipe
import com.ssafy.sungchef.domain.model.recommendation.RecommendedFoodList
import com.ssafy.sungchef.domain.model.recommendation.RecommendedRecipeList
import com.ssafy.sungchef.domain.model.recommendation.Recommendation
import com.ssafy.sungchef.domain.model.recommendation.UserInfo
import java.util.Calendar
import kotlin.math.floor

fun RecommendationResponse.toRecommendation(): Recommendation {
    return Recommendation(
        this.recommendFoodList.map {
            it.toRecommendFood()
        },
        this.recommendRecipeList.map {
            it.toRecommendRecipe()
        }
    )
}

fun RecommendedFoodListResponse.toRecommendFood(): RecommendedFoodList {
    return RecommendedFoodList(
        this.foodList.map {
            it.toFood()
        }
    )
}

fun RecommendedFoodResponse.toFood(): RecommendedFood {
    return RecommendedFood(
        this.foodImage,
        this.foodName
    )
}

fun RecommendedRecipeListResponse.toRecommendRecipe(): RecommendedRecipeList {
    return RecommendedRecipeList(
        this.recipeList.map {
            it.toRecipe()
        }
    )
}

fun RecommendedRecipeResponse.toRecipe(): RecommendedRecipe {
    return RecommendedRecipe(
        this.recipeId,
        this.recipeImage,
        this.recipeName
    )
}

fun UserSettingInfoData.toUserInfo(): UserInfo {
    val today = Calendar.getInstance()
    val age = today.get(Calendar.YEAR) - this.userBirthdate.substring(0, 4).toInt() + 1
    var result = ""
    if (floor(age.toDouble() / 10) == 0.0) {
        result="미취학"
    }else{
        result = (floor(age.toDouble() / 10) * 10).toInt().toString().plus("대")
    }

    return UserInfo(
        this.userNickName,
        if (this.userGender == "F") "여성" else "남성",
        result
    )
}