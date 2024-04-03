package com.ssafy.sungchef.features.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.sungchef.R
import com.ssafy.sungchef.domain.model.recommendation.RecommendedFood
import com.ssafy.sungchef.domain.model.recommendation.RecommendedFoodList
import com.ssafy.sungchef.domain.model.recommendation.RecommendedRecipe
import com.ssafy.sungchef.domain.model.recommendation.RecommendedRecipeList
import com.ssafy.sungchef.domain.model.recommendation.UserInfo
import com.ssafy.sungchef.features.component.ImageTextColumnComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.component.TopAppBarComponent
import com.ssafy.sungchef.features.screen.cooking.LottieAnimationComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navVisibility: () -> Unit,
    onNavigateMenu: (String) -> (Unit),
    onNavigateDetail: (Int) -> (Unit)
) {
    LaunchedEffect(true) {
        viewModel.getRecommendation()
        viewModel.getUserData()
        navVisibility()
    }
    val viewState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = { TopAppBarComponent() }
    ) { paddingValues ->
        if (viewState.user == null || viewState.recommendFood == null || viewState.recommendRecipe == null) {
            LottieAnimationComponent(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                id = R.raw.loading_animation
            )
        } else {
            Content(
                paddingValues = paddingValues,
                onNavigateDetail = { onNavigateDetail(it) },
                onNavigateMenu = onNavigateMenu,
                userInfo = viewState.user,
                recommendedFoodList = viewState.recommendFood,
                recommendedRecipeList = viewState.recommendRecipe
            )
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onNavigateDetail: (Int) -> (Unit),
    onNavigateMenu: (String) -> (Unit),
    userInfo: UserInfo,
    recommendedFoodList: List<RecommendedFoodList>?,
    recommendedRecipeList: List<RecommendedRecipeList>?
) {
    LazyColumn(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        item {
            if (recommendedFoodList != null)
                RecommendFoodBody(
                    modifier = modifier,
                    text = "오늘은 무엇을 먹을까?",
                    dataList = recommendedFoodList[0].recommendedFoodList,
                    size = 300
                ) {
                    onNavigateMenu(it)
                }
        }
        item {
            RecommendRecipeBody(
                modifier = modifier,
                text = "냉장고를 털어보자",
                dataList = if (recommendedRecipeList != null) recommendedRecipeList[0].recommendedRecipeList else listOf(),
            ) {
                onNavigateDetail(it)
            }
        }
        item {
            if (recommendedFoodList != null)
                RecommendFoodBody(
                    modifier = modifier,
                    text = "${userInfo.nickName}님이 좋아할 만한 음식",
                    dataList = recommendedFoodList[1].recommendedFoodList,
                    size = 120
                ) {
                    onNavigateMenu(it)
                }
        }
        item {
            if (recommendedFoodList != null)
                RecommendFoodBody(
                    modifier = modifier,
                    text = "${userInfo.gender}이 좋아하는 음식",
                    dataList = recommendedFoodList[2].recommendedFoodList,
                    size = 120
                ) {
                    onNavigateMenu(it)
                }
        }
        item {
            if (recommendedFoodList != null)
                RecommendFoodBody(
                    modifier = modifier,
                    text = "${userInfo.birthdate}가 좋아하는 음식",
                    dataList = recommendedFoodList[3].recommendedFoodList,
                    size = 120
                ) {
                    onNavigateMenu(it)
                }
        }
        item {
            if (recommendedFoodList != null)
                RecommendFoodBody(
                    modifier = modifier,
                    text = "오늘같은 날씨에 먹어봐요",
                    dataList = recommendedFoodList[4].recommendedFoodList,
                    size = 120
                ) {
                    onNavigateMenu(it)
                }
        }
    }
}

@Composable
private fun RecommendFoodBody(
    modifier: Modifier = Modifier,
    text: String = "",
    size: Int,
    dataList: List<RecommendedFood>?,
    onClick: (String) -> (Unit)
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
                        imageResource = item.foodImage,
                        text = item.foodName,
                        size = size
                    ) {
                        onClick(item.foodName)
                    }
                }
            }
        }
    }
}

@SuppressLint("ResourceAsColor")
@Composable
private fun RecommendRecipeBody(
    modifier: Modifier = Modifier,
    text: String = "",
    dataList: List<RecommendedRecipe>,
    onClick: (Int) -> (Unit)
) {
    Column {
        TextComponent(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.padding(top = 10.dp, bottom = 10.dp, start = 20.dp)
        )
        if (!dataList.isNotEmpty()) {
            LazyRow(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                items(dataList) { item ->
                    ImageTextColumnComponent(
                        modifier = modifier
                            .padding(horizontal = 20.dp)
                            .padding(vertical = 10.dp),
                        imageResource = item.recipeImage,
                        text = item.recipeName,
                        size = 120
                    ) {
                        onClick(item.recipeId)
                    }
                }
            }
        } else {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .blur(20.dp),
                ) {
                    ImageTextColumnComponent(
                        modifier = modifier
                            .padding(horizontal = 20.dp)
                            .padding(vertical = 10.dp),
                        imageResource = R.drawable.icon_image_fail,
                        size = 120,
                        text = "더미 이미지"
                    ) {}
                    ImageTextColumnComponent(
                        modifier = modifier
                            .padding(horizontal = 20.dp)
                            .padding(vertical = 10.dp),
                        imageResource = R.drawable.icon_image_fail,
                        size = 120,
                        text = "더미 이미지"
                    ) {}
                    ImageTextColumnComponent(
                        modifier = modifier
                            .padding(horizontal = 20.dp)
                            .padding(vertical = 10.dp),
                        imageResource = R.drawable.icon_image_fail,
                        size = 120,
                        text = "더미 이미지"
                    ) {}
                }
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .background(color = Color(0x00000000)),
                    contentAlignment = Alignment.Center
                ) {
                    TextComponent(text = "냉장고에 재료를 넣어주세요.")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendBodyPreview() {
    HomeScreen(viewModel = hiltViewModel(), navVisibility = {}, onNavigateMenu = {}) {}
}