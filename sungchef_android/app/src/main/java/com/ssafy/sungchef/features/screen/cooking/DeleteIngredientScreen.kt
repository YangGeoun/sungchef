package com.ssafy.sungchef.features.screen.cooking

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.sungchef.domain.model.ingredient.IngredientList
import com.ssafy.sungchef.domain.model.ingredient.LackIngredient
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.IngredientSelectComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.component.TopAppBarComponent
import com.ssafy.sungchef.features.component.TotalSelectComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteIngredientScreen(
    viewModel: CookingViewModel,
    id: Int,
    onNavigateHome: () -> (Unit),
    changeNavVisibility: () -> (Unit)
) {
    val uiState = viewModel.uiState.collectAsState().value
    val selectedList = viewModel.selectedList.collectAsState()
    LaunchedEffect(true) {
        viewModel.getUsedIngredient(id)
        changeNavVisibility()
    }

    Scaffold(
        topBar = {
            TopAppBarComponent(title = {
                TextComponent(
                    text = "사용한 재료",
                    style = MaterialTheme.typography.titleLarge
                )
            })
        }
    ) { paddingValues ->
        if (uiState.usedIngredient != null) {
            Content(
                paddingValues = paddingValues,
                modifier = Modifier,
                usedIngredient = uiState.usedIngredient,
                selectedList = selectedList.value,
                onNavigateHome = onNavigateHome
            ) {
                viewModel.changeAllSelect()
            }
        }
    }
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    modifier: Modifier,
    usedIngredient: LackIngredient,
    selectedList: IngredientList,
    onNavigateHome: () -> (Unit),
    onSelectedAll: () -> (Unit)
) {
    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        TextComponent(
            modifier = modifier
                .padding(vertical = 10.dp)
                .padding(horizontal = 20.dp),
            text = "소진된 재료를 선택해주세요.\n메뉴 추천이 더욱 정확해져요.",
            fontSize = 18.sp
        )
        TotalSelectComponent(
            selected = usedIngredient.ingredientInfo.size == selectedList.ingredientList.size,
            totalCount = usedIngredient.ingredientInfo.size
        ) {
            onSelectedAll()
        }
        Spacer(modifier = modifier.padding(10.dp))
        Column(
            modifier = modifier.weight(1f)
        ) {
            usedIngredient.ingredientInfo.map { ingredientInfo ->
                ingredientInfo.recipeIngredientList.map { ingredient ->
                    IngredientSelectComponent(
                        selected = selectedList.ingredientList.any { it.ingredientId == ingredient.recipeIngredientId },
                        name = ingredient.recipeIngredientName
                    ) {

                    }
                }
            }
        }
        FilledButtonComponent(text = "다음") {
            onNavigateHome()
        }
    }
}