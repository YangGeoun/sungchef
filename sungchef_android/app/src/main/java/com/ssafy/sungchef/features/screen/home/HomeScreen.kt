package com.ssafy.sungchef.features.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.sungchef.domain.model.recommendation.RecommendedFoodList
import com.ssafy.sungchef.domain.model.recommendation.RecommendedRecipeList
import com.ssafy.sungchef.features.component.ImageTextColumnComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.component.TopAppBarComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    LaunchedEffect(true) {
        viewModel.getRecommendation()
    }
    val viewState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = { TopAppBarComponent() }
    ) { paddingValues ->
        Content(
            paddingValues = paddingValues,
            recommendedFoodList = viewState.recommendFood,
            recommendedRecipeList = viewState.recommendRecipe
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    recommendedFoodList: List<RecommendedFoodList>?,
    recommendedRecipeList: List<RecommendedRecipeList>?
) {
    if (recommendedFoodList != null && recommendedRecipeList != null) {
        val scrollState = rememberScrollState()
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            RecommendBody(
                modifier = modifier,
                text = "오늘은 무엇을 먹을까?",
                dataList = recommendedRecipeList[0].recommendedRecipeList,
                size = 300
            ) {

            }
            RecommendBody(
                modifier = modifier,
                text = "냉장고를 털어보자",
                dataList = recommendedRecipeList[1].recommendedRecipeList,
                size = 120
            ) {

            }
            RecommendBody(
                modifier = modifier,
                text = "좋아할 만한 음식",
                dataList = recommendedFoodList[0].recommendedFoodList,
                size = 120
            ) {

            }
            RecommendBody(
                modifier = modifier,
                text = "남자가 좋아하는 음식",
                dataList = recommendedFoodList[1].recommendedFoodList,
                size = 120
            ) {

            }
            RecommendBody(
                modifier = modifier,
                text = "20대가 좋아하는 음식",
                dataList = recommendedFoodList[2].recommendedFoodList,
                size = 120
            ) {

            }
            RecommendBody(
                modifier = modifier,
                text = "오늘같은 날씨에 먹어봐요",
                dataList = recommendedFoodList[3].recommendedFoodList,
                size = 120
            ) {

            }
        }
    }
}

@Composable
private fun <T> RecommendBody(
    modifier: Modifier = Modifier,
    text: String = "",
    size: Int,
    dataList: List<T>?,
    onClick: () -> (Unit)
) {
    Column {
        TextComponent(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.padding(top = 10.dp, bottom = 10.dp, start = 20.dp)
        )
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            if (dataList != null) {
                items(dataList) { item ->
                    ImageTextColumnComponent(
                        modifier = modifier
                            .padding(horizontal = 20.dp)
                            .padding(vertical = 10.dp),
                        imageResource = "",
                        text = item.toString(),
                        size = size
                    ) {
                        onClick()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendBodyPreview() {
    HomeScreen(viewModel = hiltViewModel())
}