package com.ssafy.sungchef.features.screen.menu

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.sungchef.R
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo
import com.ssafy.sungchef.features.component.IconComponent
import com.ssafy.sungchef.features.component.MenuCardComponent
import com.ssafy.sungchef.features.component.TextComponent
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MenuScreen(
    viewModel: MenuViewModel,
    navigateDetailScreen: (Int) -> (Unit)
) {
    val viewState = viewModel.uiState.collectAsState().value
    val isSearching by viewModel.isSearching.collectAsState()
    val searchText by viewModel.searchText.collectAsState()

    Scaffold(
        topBar = {
            SearchBar(
                query = searchText,
                onQueryChange = viewModel::onSearchTextChange,
                onSearch = viewModel::onSearchTextChange,
                active = isSearching,
                onActiveChange = { viewModel.onToggleSearch() },
                trailingIcon = {
                    if (isSearching) {
                        IconComponent(painter = painterResource(id = R.drawable.icon_delete))
                    }
                },
                leadingIcon = {
                    IconComponent(painter = painterResource(id = R.drawable.search))
                },
                placeholder = { TextComponent(text = "레시피를 검색하세요.") },
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth(),
                colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.background)
            ) {
                LazyColumn {
                    itemsIndexed(viewState.foodList) { _, item ->
                        TextComponent(
                            text = item.name,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onSearchTextChange(item.name)
                                    viewModel.onToggleSearch()
                                    viewModel.getSearchedVisitRecipeInfo(0, item.name)
                                }
                                .padding(start = 20.dp, top = 10.dp)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Content(
            paddingValues,
            viewState.pagedData,
            onVisitClick = { viewModel.getVisitRecipeInfo(0) },
            onBookMarkClick = {
                if (searchText == "") viewModel.getBookMarkRecipeInfo(0) else viewModel.getSearchedBookmarkRecipeInfo(0, searchText)
            },
            onClick = { navigateDetailScreen(it) }
        ) { recipeId, bookmark ->
            // Todo 즐겨찾기 통신 만들기
            viewModel.changeBookmarkRecipe(recipeId, bookmark)
        }
    }
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    recipeInfoList: Flow<PagingData<RecipeInfo>>? = null,
    modifier: Modifier = Modifier,
    onVisitClick: () -> (Unit),
    onBookMarkClick: () -> (Unit),
    onClick: (Int) -> (Unit),
    changeBookMarkState: (Int, Boolean) -> (Unit)
) {
    var pagingItems: LazyPagingItems<RecipeInfo>? = null
    recipeInfoList?.let {
        pagingItems = rememberFlowWithLifecycle(it).collectAsLazyPagingItems()
    }
    Column(
        modifier = modifier
            .padding(paddingValues)
            .padding(horizontal = 20.dp)
    ) {
        RecipeInfo(
            modifier = modifier,
            300,
            onVisitClick = { onVisitClick() },
            onBookMarkClick = { onBookMarkClick() }
        )
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            if (recipeInfoList != null && pagingItems != null) {
                Log.d("TAG", "Content: ${pagingItems!!.itemCount}")
                items(pagingItems!!.itemCount) {
                    pagingItems!![it]?.let { it1 ->
                        MenuCardComponent(
                            modifier = modifier,
                            imageResource = it1.recipeImage,
                            title = it1.recipeName,
                            views = it1.recipeVisitCount.toString(),
                            servings = it1.recipeVolume,
                            timer = it1.recipeCookingTime,
                            bookmark = it1.bookmark,
                            onClick = { onClick(it1.recipeId) }
                        ) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RecipeInfo(
    modifier: Modifier,
    count: Int,
    onVisitClick: () -> (Unit),
    onBookMarkClick: () -> (Unit)
) {
    var menuVisibility by remember { mutableStateOf(false) }
    var standard by remember { mutableStateOf("조회순") }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextComponent(
            modifier = modifier,
            text = "성식당 레시피($count)",
            color = Color.Black,
            fontSize = 16.sp
        )
        Row(
            modifier = modifier.clickable { menuVisibility = !menuVisibility },
            horizontalArrangement = Arrangement.End
        ) {
            TextComponent(
                modifier = modifier,
                text = standard,
                color = Color.Black,
                fontSize = 16.sp
            )
            IconComponent(painter = painterResource(id = R.drawable.tune))
            DropdownMenu(
                modifier = modifier.background(color = Color.White),
                expanded = menuVisibility,
                onDismissRequest = { menuVisibility = !menuVisibility }
            ) {
                DropdownMenuItem(
                    text = { TextComponent(text = "조회순") },
                    onClick = {
                        menuVisibility = !menuVisibility
                        standard = "조회순"
                        onVisitClick()
                    }
                )
                DropdownMenuItem(
                    text = { TextComponent(text = "즐겨찾기순") },
                    onClick = {
                        menuVisibility = !menuVisibility
                        standard = "즐겨찾기순"
                        onBookMarkClick()
                    }
                )
            }
        }
    }
}

@Composable
fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(lifecycle, minActiveState)
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MenuScreen(hiltViewModel()) {}
}