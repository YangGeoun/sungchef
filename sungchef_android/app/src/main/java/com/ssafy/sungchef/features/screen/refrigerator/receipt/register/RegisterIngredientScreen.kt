package com.ssafy.sungchef.features.screen.refrigerator.receipt.register

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.sungchef.R
import com.ssafy.sungchef.commons.ADD_INGREDIENT
import com.ssafy.sungchef.commons.REGISTER_INGREDIENT_BUTTON
import com.ssafy.sungchef.commons.SEARCH_INGREDIENT
import com.ssafy.sungchef.domain.model.refrigerator.SearchIngredient
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.IconComponent
import com.ssafy.sungchef.features.component.ImageComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.util.IngredientType

private const val TAG = "RegisterIngredientScree_성식당"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterIngredientScreen(
    viewModel : RegisterIngredientViewModel
) {

    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val searchResult by viewModel.searchIngredientList.collectAsState()
    val ingredientList by viewModel.ingredientList.collectAsState()
    val ingredientIdList by viewModel.ingredientIdList.collectAsState()

    Log.d(TAG, "ingredientIdList: $ingredientIdList")
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextComponent(
            modifier = Modifier
                .padding(top = 80.dp),
            text = ADD_INGREDIENT,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(
            modifier = Modifier
                .padding(30.dp)
        )
        SearchBar(
            query = searchText,
            onQueryChange = viewModel::onSearchTextChange,
            onSearch = viewModel::onSearchTextChange,
            active = isSearching,
            onActiveChange = {
                viewModel.onToggleSearch()
            },
            modifier = Modifier
                .padding(horizontal = 40.dp),
            placeholder = {
                TextComponent(
                    text = SEARCH_INGREDIENT
                )
            },
            colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            LazyColumn{
                itemsIndexed(searchResult) { _, value ->
                    Row(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                            .clickable {
                                // TODO 클릭 시 LazyColumn을 닫고 화면에 띄우기
                                viewModel.onToggleSearch()
                                viewModel.addIngredient(value)
                            }
                    ){
                        ImageComponent(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape),
                            imageResource = IngredientType.ingredientType[value.ingredientType] ?: R.drawable.etc
                        )

                        TextComponent(
                            modifier = Modifier
                                .padding(start = 10.dp),
                            text = value.ingredientName
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier
                .padding(top = 20.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, bottom = 80.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(15.dp)
                )
        ){
            // 추가된 재료를 보여준다.
            LazyColumn (
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 20.dp)
            ){
                // Map의 Key는 header, value는 items로 나타냄
                ingredientList.forEach { (header, items) ->
                    item {
                        Row(
                            modifier = Modifier
                                .padding(start = 10.dp, top = 10.dp)
                        ) {
                            ImageComponent(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape),
                                imageResource = IngredientType.ingredientType[header] ?: R.drawable.etc
                            )

                            TextComponent(
                                modifier = Modifier
                                    .padding(start = 10.dp),
                                text = header
                            )
                        }
                    }

                    items(items) { searchIngredient ->
                        IngredientItem(
                            item = searchIngredient,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }

        FilledButtonComponent(
            modifier = Modifier
                .fillMaxWidth(),
            text = REGISTER_INGREDIENT_BUTTON
        ) {
            // TODO 서버에 재료를 등록하고 냉장고 조회 화면으로 넘어가기
        }
    }
}

@Composable
fun IngredientItem(
    item : SearchIngredient,
    viewModel: RegisterIngredientViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Row(
            modifier = Modifier
                .border(
                    width = 1.dp, // 테두리 두께
                    color = Color.Black, // 테두리 색상
                    shape = RoundedCornerShape(15.dp) // 테두리 모양 (여기서는 둥근 모서리)
                )
                .background(
                    color = Color.White
                )
                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                .weight(9f)
        ){
            TextComponent(
                text = item.ingredientName
//                text = "김치찌개"
            )
        }
        Spacer(
            modifier = Modifier
                .weight(0.1f)
        )

        IconComponent(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    viewModel.deleteIngredient(item)
                },
            painter = painterResource(id = R.drawable.icon_cancel)
        )
    }
}

@Preview
@Composable
fun RegisterIngredientPreview() {
    RegisterIngredientScreen(
        hiltViewModel()
    )
}
