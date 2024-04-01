package com.ssafy.sungchef.data.datasource.recipe

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.sungchef.data.api.RecipeService
import com.ssafy.sungchef.data.datasource.user.BookmarkDataSource
import com.ssafy.sungchef.data.local.dao.BookmarkDao
import com.ssafy.sungchef.data.mapper.recipe.toRecipeInfo
import com.ssafy.sungchef.data.model.entity.BookmarkEntity
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeInfoResponse
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo
import javax.inject.Inject

class PagingRecipeDataSource @Inject constructor(
    private val recipeService: RecipeService,
    private val bookmarkDataSource: BookmarkDataSource,
    private val isVisit: Boolean = true,
    private val name: String = ""
) : PagingSource<Int, RecipeInfo>() {
    override fun getRefreshKey(state: PagingState<Int, RecipeInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecipeInfo> {
        val page = params.key ?: 0
        return try {
            val response =
                if (isVisit) {
                    if (name == "") recipeService.getAllVisitRecipe(page) else recipeService.getSearchVisitRecipe(
                        name,
                        page
                    )
                } else {
                    if (name == "") recipeService.getAllBookMarkRecipe(page) else recipeService.getSearchBookmarkRecipe(
                        name,
                        page
                    )
                }

            val recipeList = if (response.isSuccessful) {
                response.body()?.data?.recipeList.orEmpty().map {
                    it.toRecipeInfo()
                }
            } else {
                emptyList()
            }

            if (recipeList.isNotEmpty()) {
                recipeList.map {
                    if (it.bookmark) {
                        bookmarkDataSource.insert(BookmarkEntity(it.recipeId))
                    }
                    val bookmark = bookmarkDataSource.selectBookmark(it.recipeId ?: -1)
                    it.bookmark = if (it.bookmark) true else bookmark != null
                }
            }

            LoadResult.Page(
                data = recipeList,
                prevKey = if (page == 0) null else page.minus(1),
                nextKey = if (recipeList.isEmpty()) null else page.plus(1)
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

}