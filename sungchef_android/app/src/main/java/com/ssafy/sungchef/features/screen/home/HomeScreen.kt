package com.ssafy.sungchef.features.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.sungchef.features.component.ImageTextColumnComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.component.TopAppBarComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(

) {
    Scaffold(
        topBar = { TopAppBarComponent() }
    ) { paddingValues ->
        Content(paddingValues = paddingValues)
    }
}

@Composable
private fun Content(
    modifier:Modifier = Modifier,
    paddingValues: PaddingValues
){
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ){
        RecommendBody(
            modifier = modifier,
            text = "오늘은 무엇을 먹을까?",
            size = 300
        ) {

        }
        RecommendBody(
            modifier = modifier,
            text = "냉장고를 털어보자",
            size = 120
        ) {

        }
        RecommendBody(
            modifier = modifier,
            text = "좋아할 만한 음식",
            size = 120
        ) {

        }
        RecommendBody(
            modifier = modifier,
            text = "남자가 좋아하는 음식",
            size = 120
        ) {

        }
        RecommendBody(
            modifier = modifier,
            text = "20대가 좋아하는 음식",
            size = 120
        ) {

        }
        RecommendBody(
            modifier = modifier,
            text = "오늘같은 날씨에 먹어봐요",
            size = 120
        ) {

        }
    }
}

@Composable
private fun RecommendBody(
    modifier:Modifier = Modifier,
    text: String = "",
    size: Int,
    onClick: ()->(Unit)
){
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
        ){
            items(10){item ->
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RecommendBodyPreview(){
    Scaffold(
        topBar = { TopAppBarComponent() }
    ) { paddingValues ->
        Content(paddingValues = paddingValues)
    }
}