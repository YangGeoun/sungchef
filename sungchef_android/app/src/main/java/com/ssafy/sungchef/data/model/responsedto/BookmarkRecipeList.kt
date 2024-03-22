package com.ssafy.sungchef.data.model.responsedto

data class BookmarkRecipeList (
    val code : Int,
    val message : String,
    val data : BookmarkRecipeListData
)

data class BookmarkRecipeListData(
    val bookmarkRecipeCount: Int,
    val bookmarkRecipeList : List<BookmarkRecipeDetailList>

)

data class BookmarkRecipeDetailList(
    val recipeId : Int,
    val recipeImage : String

)