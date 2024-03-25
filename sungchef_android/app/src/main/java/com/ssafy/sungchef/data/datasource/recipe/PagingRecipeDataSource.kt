package com.ssafy.sungchef.data.datasource.recipe

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.sungchef.data.api.RecipeService
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeInfoResponse
import javax.inject.Inject

class PagingRecipeDataSource @Inject constructor(
    private val recipeService: RecipeService,
    private val isVisit:Boolean = true
):PagingSource<Int, RecipeInfoResponse>() {
    override fun getRefreshKey(state: PagingState<Int, RecipeInfoResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecipeInfoResponse> {
        val page = params.key ?: 1
        return try {
            val response = if (isVisit) recipeService.getAllVisitRecipe(page) else recipeService.getAllBookMarkRecipe(page)
            LoadResult.Page(
                data = response.data.recipeList,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.data.recipeList.isEmpty()) null else page.plus(1)
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

}