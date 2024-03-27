package com.ssafy.sungchef.features.screen.cooking

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssafy.sungchef.R
import com.ssafy.sungchef.domain.model.recipe.RecipeDetailInfo
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.IconButtonComponent
import com.ssafy.sungchef.features.component.IconComponent
import com.ssafy.sungchef.features.component.RecipeCardComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.component.TopAppBarComponent
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CookingScreen(
    viewModel: CookingViewModel,
    id: Int,
    onNavigateBack: () -> (Unit),
    changeNav: () -> (Unit)
) {
    var curNum by remember { mutableIntStateOf(1) }
    val uiState = viewModel.uiState.collectAsState().value
    val context: Context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.getRecipeStep(id)
    }

    if (uiState.isLoading) {
        TextComponent(text = "로딩중~~~")
    } else {
        changeNav()
        Scaffold(
            topBar = {
                TopAppBarComponent(
                    navigationIcon = {
                        IconButtonComponent(
                            onClick = { onNavigateBack() },
                            painter = painterResource(id = R.drawable.icon_back)
                        )
                    },
                    title = { TextComponent(text = "국가 권력급 김치찌개($curNum/${uiState.count})") },
                )
            }
        ) { paddingValues ->
            Content(
                paddingValues = paddingValues,
                recipeDetailList = uiState.recipeDetailList,
                speakDescription = {viewModel.textToSpeech(context,uiState.recipeDetailList[it].recipeDetailDescription)}
            ) {
                curNum = it + 1
                viewModel.textToSpeech(context,uiState.recipeDetailList[it].recipeDetailDescription)
            }
        }
    }

    BackHandler {
        onNavigateBack()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Content(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    recipeDetailList: List<RecipeDetailInfo>,
    speakDescription:(Int)-> (Unit),
    changeNum: (Int) -> (Unit)
) {
    if (recipeDetailList.isNotEmpty()) {
        val pagerState = rememberPagerState(pageCount = { recipeDetailList.size })
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                changeNum(page)
            }
        }
        Column(
            modifier = modifier.padding(paddingValues)
        ) {
            LinearProgressIndicator(
                progress = { (pagerState.currentPage + 1).toFloat() / pagerState.pageCount.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
            )
            HorizontalPager(
                state = pagerState,
                modifier = modifier.clickable { speakDescription(pagerState.currentPage) }
                    .fillMaxSize()
                    .background(Color.White),
                contentPadding = PaddingValues(10.dp)
            ) {
                RecipeCardComponent(
                    modifier = modifier,
                    size = 2,
                    recipeDetailInfo = recipeDetailList[it]
                )
            }
        }
    }
}