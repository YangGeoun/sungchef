package com.ssafy.sungchef.features.screen.menu

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.sungchef.R
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo
import com.ssafy.sungchef.features.component.IconComponent
import com.ssafy.sungchef.features.component.MenuCardComponent
import com.ssafy.sungchef.features.component.TextComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    viewModel: MenuViewModel,
    navigateDetailScreen: (Int) -> (Unit)
) {
    val viewState = viewModel.uiState.collectAsState().value
    LaunchedEffect(true) {
        viewModel.getVisitRecipeInfo(0)
    }
    Scaffold(
        topBar = {
            SearchBar(
                query = "",
                onQueryChange = {},
                onSearch = {},
                active = false,
                onActiveChange = {},
                trailingIcon = {
                    IconComponent(painter = painterResource(id = R.drawable.search))
                },
                placeholder = { TextComponent(text = "레시피를 검색하세요.") },
                modifier = Modifier
                    .background(Color.White)
                    .padding(horizontal = 20.dp),
                colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.background)
            ) {

            }
        }
    ) { paddingValues ->
        if (!viewState.recipeInfoList.isNullOrEmpty()) {
            Content(
                paddingValues,
                viewState.recipeInfoList,
                onVisitClick = {viewModel.getVisitRecipeInfo(1)},
                onBookMarkClick = {viewModel.getBookMarkRecipeInfo(1)}
            ){
                // Todo 메뉴 상세페이지로 이동 및 데이터 받아오기
                navigateDetailScreen(it)
            }
        }
    }
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    recipeInfoList: List<RecipeInfo>,
    modifier: Modifier = Modifier,
    onVisitClick: () -> (Unit),
    onBookMarkClick:() -> (Unit),
    onClick:(Int)->(Unit)
) {
    Column(
        modifier = modifier
            .padding(paddingValues)
            .padding(horizontal = 20.dp)
    ) {
        RecipeInfo(
            modifier = modifier,
            300,
            onVisitClick = {onVisitClick()},
            onBookMarkClick = {onBookMarkClick()}
        )
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            itemsIndexed(recipeInfoList) { index, data ->
                MenuCardComponent(
                    modifier = modifier,
                    imageResource = data.recipeImage,
                    title = data.recipeName,
                    views = "${data.recipeVisitCount}",
                    servings = data.recipeVolume,
                    timer = data.recipeCookingTime,
                    onClick = { onClick(data.recipeId) }
                ) {

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
    onBookMarkClick:() -> (Unit)
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

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MenuScreen(hiltViewModel()){}
}