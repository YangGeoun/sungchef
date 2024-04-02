package com.ssafy.sungchef.features.screen.cooking

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.sungchef.domain.model.ingredient.Ingredient
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
    onNavigateRegisterCook: (Int) -> (Unit),
    changeNavVisibility: () -> (Unit)
) {
    val uiState = viewModel.uiState.collectAsState().value
    val selectedList = viewModel.selectedList

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
                onDeleteIngredient = { viewModel.deleteIngredient() },
                onNavigateRegisterCook = { onNavigateRegisterCook(id) }
            ) {
                viewModel.selectIngredient(it)
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
    onDeleteIngredient: () -> (Unit),
    onNavigateRegisterCook: () -> (Unit),
    onClick: (Int) -> (Unit)
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
        Spacer(modifier = modifier.padding(10.dp))
        LazyColumn(
            modifier = modifier.weight(1f)
        ) {
            usedIngredient.ingredientInfo.map { ingredientInfo ->
                items(ingredientInfo.recipeIngredientList) {ingredient->
                    IngredientSelectComponent(
                        selected = selectedList.ingredientList.any { it.ingredientId == ingredient.recipeIngredientId },
                        name = ingredient.recipeIngredientName
                    ) {
                        onClick(ingredient.recipeIngredientId)
                    }
                }
            }
        }
        FilledButtonComponent(text = "다음") {
            onNavigateRegisterCook()
        }
    }
}