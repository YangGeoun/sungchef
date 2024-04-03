package com.ssafy.sungchef.features.screen.menu

import androidx.compose.foundation.layout.height
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.ssafy.sungchef.R
import com.ssafy.sungchef.domain.model.recipe.RecipeDetail
import com.ssafy.sungchef.domain.model.ingredient.IngredientInfo
import com.ssafy.sungchef.features.component.CardComponent
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.IngredientCardComponent
import com.ssafy.sungchef.features.component.RecipeCardComponent
import com.ssafy.sungchef.features.component.RecipeInfoCardComponent
import com.ssafy.sungchef.features.component.TextComponent

@Composable
fun MenuDetailScreen(
    recipeId: String = "",
    viewModel: MenuDetailViewModel,
    onChangeNav: () -> (Unit),
    onBackNavigate: () -> (Unit),
    onNavigateCooking: (Int) -> (Unit)
) {
    val context: Context = LocalContext.current
    var isCook by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        viewModel.getDetailRecipe(recipeId.toInt())
        onChangeNav()
    }
    val viewState = viewModel.uiState.collectAsState().value
    if (viewState.isError) {
        LaunchedEffect(true) {
            Toast.makeText(context, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
            onBackNavigate()
        }
    }

    if (viewState.lackIngredient != null && isCook) {
        DialogComponent(
            text = viewState.dialogTitle,
            ingredientList = viewState.lackIngredient.ingredientInfo,
            id = viewState.lackIngredient.recipeId,
            onChangeState = { isCook = false }
        ) {
            onNavigateCooking(viewState.recipeDetail!!.recipeId)
        }
    }

    // Todo 서버 통신
    Scaffold { paddingValues ->
        if (viewState.recipeDetail != null) {
            Content(
                paddingValues = paddingValues,
                recipeDetail = viewState.recipeDetail
            ) {
                viewModel.getLackIngredient(it)
                isCook = true
            }
        }
    }
    BackHandler {
        onBackNavigate()
    }
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    recipeDetail: RecipeDetail,
    checkLack: (Int) -> (Unit)
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .padding(paddingValues)
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            RecipeImageComponent(
                modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f),
                recipeDetail.recipeImage
            )
            RecipeInfoCardComponent(
                modifier = modifier,
                title = recipeDetail.recipeName,
                description = recipeDetail.recipeDescription,
                volume = recipeDetail.recipeVolume,
                time = recipeDetail.recipeCookingTime
            )
            CardComponent(text = "재료") {
                for (recipeIngredientInfo in recipeDetail.recipeIngredientInfoList.ingredientInfo) {
                    if (recipeIngredientInfo.recipeIngredientList.isNotEmpty()) {
                        IngredientCardComponent(
                            classification = recipeIngredientInfo.recipeIngredientType,
                            id = recipeIngredientInfo.image,
                            recipeIngredients = recipeIngredientInfo.recipeIngredientList
                        )
                    }
                }
            }
            CardComponent(text = "레시피") {
                for (recipeDetailInfo in recipeDetail.recipeDetailInfoList)
                    RecipeCardComponent(modifier = modifier, recipeDetailInfo = recipeDetailInfo)
            }
        }
        FilledButtonComponent(text = "요리하기") {
            // 요리하는 화면으로 이동
            checkLack(recipeDetail.recipeId)
        }
    }
}

@Composable
fun DialogComponent(
    modifier: Modifier = Modifier,
    text: String = "",
    ingredientList: List<IngredientInfo>,
    id: Int,
    onChangeState: () -> (Unit),
    navigateCook: (Int) -> (Unit)
) {
    Dialog(onDismissRequest = { onChangeState() }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.background(Color.White)
        ) {
            TextComponent(text = text, style = MaterialTheme.typography.titleMedium)
            LazyColumn(
                modifier = modifier.height(200.dp)
            ) {
                if (ingredientList.isNotEmpty()) {
                    itemsIndexed(ingredientList) { _, item ->
                        IngredientCardComponent(
                            classification = item.recipeIngredientType,
                            id = item.image,
                            recipeIngredients = item.recipeIngredientList
                        )
                    }
                }
            }
            Row {
                Button(
                    modifier = modifier
                        .weight(1f)
                        .height(50.dp),
                    onClick = { onChangeState() },
                    shape = RoundedCornerShape(0),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    TextComponent(text = "취소", color = Color.Black)
                }
                Button(
                    modifier = modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(0),
                    onClick = {
                        onChangeState()
                        navigateCook(id)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    TextComponent(text = "확인", color = Color.Black)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeImageComponent(
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