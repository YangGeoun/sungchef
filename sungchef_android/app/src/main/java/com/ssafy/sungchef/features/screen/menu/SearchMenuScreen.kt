package com.ssafy.sungchef.features.screen.menu

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.ssafy.sungchef.features.component.IconButtonComponent
import com.ssafy.sungchef.features.component.IconComponent
import com.ssafy.sungchef.features.component.MenuCardComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.component.TextFieldComponent
import com.ssafy.sungchef.features.component.TopAppBarComponent
import com.ssafy.sungchef.features.screen.cooking.LottieAnimationComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMenuScreen(
    viewModel: MenuViewModel,
    menu: String,
    changeNav: () -> (Unit),
    navigateDetailScreen: (Int) -> (Unit)
) {
    val viewState = viewModel.uiState.collectAsState().value
    val searchText by viewModel.searchText.collectAsState()
    var standard by remember { mutableStateOf("조회순") }

    LaunchedEffect(true) {
        viewModel.getSearchedVisitRecipeInfo(0, menu)
        changeNav()
    }

    Scaffold(
        topBar = { TopAppBarComponent(title = { TextComponent(text = menu, fontSize = 22.sp) }) }
    ) { paddingValues ->
        if (viewState.isSearchLoading) {
            LottieAnimationComponent(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit,
                id = R.raw.loading_animation
            )
        } else {
            Content(
                paddingValues,
                viewState.pagedData,
                menu = menu,
                standard = standard,
                changeStandard = { standard = it },
                onVisitClick = { viewModel.getVisitRecipeInfo(0) },
                onBookMarkClick = {
                    if (searchText == "") viewModel.getBookMarkRecipeInfo(0) else viewModel.getSearchedBookmarkRecipeInfo(
                        0,
                        searchText
                    )
                },
                onClick = { navigateDetailScreen(it) }
            ) { recipeId, bookmark ->
                viewModel.changeBookmarkRecipe(recipeId, bookmark)
            }
        }
    }
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    recipeInfoList: Flow<PagingData<RecipeInfo>>? = null,
    modifier: Modifier = Modifier,
    menu: String,
    standard: String,
    changeStandard: (String) -> (Unit),
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
            menu = menu,
            standard = standard,
            onVisitClick = { onVisitClick() },
            changeStandard = changeStandard,
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
                            bookmarks = it1.recipeBookmarkCount.toString(),
                            servings = it1.recipeVolume,
                            timer = it1.recipeCookingTime,
                            bookmark = it1.bookmark,
                            onClick = { onClick(it1.recipeId) }
                        ) { bookmark ->
                            changeBookMarkState(it, bookmark)
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
    menu: String,
    standard: String,
    onVisitClick: () -> (Unit),
    changeStandard: (String) -> (Unit),
    onBookMarkClick: () -> (Unit)
) {
    var menuVisibility by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.End
    ) {
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
                        changeStandard("조회순")
                        onVisitClick()
                    }
                )
                DropdownMenuItem(
                    text = { TextComponent(text = "즐겨찾기순") },
                    onClick = {
                        menuVisibility = !menuVisibility
                        changeStandard("즐겨찾기순")
                        onBookMarkClick()
                    }
                )
            }
        }
    }
}