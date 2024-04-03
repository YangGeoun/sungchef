package com.ssafy.sungchef.features.screen.cooking

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.sungchef.domain.model.ingredient.Ingredient
import com.ssafy.sungchef.domain.model.ingredient.IngredientList
import com.ssafy.sungchef.domain.model.ingredient.LackIngredient
import com.ssafy.sungchef.features.component.AlertDialogComponent
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
    var isDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.getUsedIngredient(id)
        changeNavVisibility()
    }

    if (isDialog) {
        AlertDialog(
            modifier = Modifier,
            title = {
                TextComponent(
                    text = "정말 삭제하시겠습니까?",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            onDismissRequest = {isDialog = false},
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteIngredient()
                        isDialog = false
                    }
                ) {
                    TextComponent(text = "확인", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isDialog = false
                    }
                ) {
                    TextComponent(text = "취소")
                }
            }
        )
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
                usingIngredient = uiState.usingIngredient,
                onDeleteIngredient = {
                    isDialog = true
                    viewModel.setIngredient(it)
                                     },
                onNavigateRegisterCook = { onNavigateRegisterCook(id) }
            )
        }
    }
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    modifier: Modifier,
    usingIngredient: List<Ingredient>,
    onDeleteIngredient: (Ingredient) -> (Unit),
    onNavigateRegisterCook: () -> (Unit)
) {
    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        TextComponent(
            modifier = modifier
                .padding(top = 10.dp)
                .padding(horizontal = 20.dp),
            text = "소진된 재료를 선택해주세요.\n메뉴 추천이 더욱 정확해져요.",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary
        )
        LazyColumn(
            modifier = modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
                .padding(vertical = 10.dp)
                .background(color = Color.White)
        ) {
            itemsIndexed(usingIngredient) { index, item ->
                IngredientSelectComponent(
                    modifier = modifier,
                    name = item.recipeIngredientName,
                    onDelete = { onDeleteIngredient(item) }
                )
            }
        }
        FilledButtonComponent(text = "다음") {
            onNavigateRegisterCook()
        }
    }
}