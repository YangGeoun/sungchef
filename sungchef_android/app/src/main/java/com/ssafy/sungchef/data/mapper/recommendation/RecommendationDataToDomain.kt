package com.ssafy.sungchef.data.mapper.recommendation

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