package com.ssafy.sungchef.features.screen.menu

import androidx.compose.foundation.layout.height


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.ssafy.sungchef.R
import com.ssafy.sungchef.domain.model.recipe.RecipeDetail
import com.ssafy.sungchef.domain.model.recipe.RecipeDetailInfo
import com.ssafy.sungchef.domain.model.recipe.RecipeIngredient
import com.ssafy.sungchef.features.component.CardComponent
import com.ssafy.sungchef.features.component.IconTextRowComponent
import com.ssafy.sungchef.features.component.TextComponent

@Composable
fun MenuDetailScreen(
    recipeId: String = "",
    viewModel: MenuViewModel,
    onBackNavigate:()->(Unit)
) {
    val context:Context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.getDetailRecipe(recipeId.toInt())
    }
    val viewState = viewModel.uiState.collectAsState().value
    if (viewState.isError){
        Toast.makeText(context, "잘못된 접근입니다.",Toast.LENGTH_SHORT).show()
        viewModel.resetError()
        onBackNavigate()
    }
    // Todo 서버 통신
    Scaffold {
        if (viewState.recipeDetail != null) {
            Content(
                paddingValues = it,
                recipeDetail = viewState.recipeDetail
            )
        }
    }
    BackHandler {
        onBackNavigate()
        viewModel.resetDetailRecipe()
    }
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    recipeDetail: RecipeDetail
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // Todo 이미지
        ImageComponent(
            modifier
                .fillMaxWidth()
                .aspectRatio(1.5f),
            recipeDetail.recipeImage
        )
        MenuInfoCard(
            modifier = modifier,
            title = recipeDetail.recipeName,
            description = recipeDetail.recipeDescription,
            volume = recipeDetail.recipeVolume,
            time = recipeDetail.recipeCookingTime
        )
        CardComponent(text = "재료") {
            // 반복문 돌려야함
            for (recipeIngredientInfo in recipeDetail.recipeIngredientInfoList) {
                if (recipeIngredientInfo.recipeIngredientList.isNotEmpty()) {
                    IngredientCardComponent(
                        classification = recipeIngredientInfo.recipeIngredientType,
                        recipeIngredients = recipeIngredientInfo.recipeIngredientList
                    )
                }
            }
        }
        CardComponent(text = "레시피") {
            for (recipeDetailInfo in recipeDetail.recipeDetailInfoList)
                RecipeCardComponent(modifier, recipeDetailInfo)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ImageComponent(
    modifier: Modifier,
    image: String,
) {
    GlideImage(
        model = image,
        modifier = modifier,
        contentDescription = "레시피 사진",
        contentScale = ContentScale.FillBounds,
        loading = placeholder(R.drawable.icon_image_fail),
        failure = placeholder(R.drawable.icon_image_fail)
    )
}

@Composable
fun MenuInfoCard(
    modifier: Modifier,
    title: String,
    description: String,
    volume: String,
    time: String
) {
    Card(
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        TextComponent(
            text = title,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .padding(horizontal = 10.dp),
            fontSize = 22.sp
        )
        TextComponent(
            text = description,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .padding(horizontal = 10.dp),
            fontSize = 16.sp,
            color = Color.DarkGray
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp)
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconTextRowComponent(
                painter = painterResource(id = R.drawable.groups),
                text = volume
            )
            IconTextRowComponent(
                painter = painterResource(id = R.drawable.timer),
                text = time
            )
        }
    }
}

@Composable
fun IngredientCardComponent(
    modifier: Modifier = Modifier,
    classification: String = "",
    recipeIngredients: List<RecipeIngredient>,
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            modifier = modifier
                .size(36.dp)
                .clip(CircleShape),
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.test_image),
            contentDescription = "사진"
        )
        Spacer(modifier = modifier.size(2.dp))
        //반복문 돌리기
        Column {
            TextComponent(
                modifier = modifier
                    .height(36.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                text = classification,
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
            for (recipeIngredient in recipeIngredients){
                Row(
                    modifier = modifier.padding(top = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = modifier.size(2.dp))
                    TextComponent(
                        text = recipeIngredient.recipeIngredientName,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = modifier.size(5.dp))
                    TextComponent(
                        text = recipeIngredient.recipeIngredientVolume,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeCardComponent(
    modifier: Modifier = Modifier,
    recipeDetailInfo: RecipeDetailInfo
) {
    Row(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 5.dp),
    ) {
        ImageComponent(
            modifier = modifier
                .size(120.dp)
                .clip(RoundedCornerShape(15.dp)),
            recipeDetailInfo.recipeDetailImage
        )
        Spacer(modifier = modifier.size(30.dp))
        Column {
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                TextComponent(
                    text = "Step${recipeDetailInfo.recipeDetailStep}",
                    modifier = modifier.padding(horizontal = 10.dp)
                )
            }
            TextComponent(
                text = recipeDetailInfo.recipeDetailDescription,
                modifier = modifier.padding(top = 10.dp)
            )
        }
    }
}