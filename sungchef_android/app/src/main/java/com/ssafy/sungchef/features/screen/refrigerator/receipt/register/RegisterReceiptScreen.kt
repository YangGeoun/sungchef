package com.ssafy.sungchef.features.screen.refrigerator.receipt.register

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.sungchef.R
import com.ssafy.sungchef.commons.MOVE_HOME_SCREEN
import com.ssafy.sungchef.commons.NEED_INGREDIENT_CONVERT
import com.ssafy.sungchef.commons.REGISTER_INGREDIENT_BUTTON
import com.ssafy.sungchef.commons.REGISTER_RECEIPT_TITLE
import com.ssafy.sungchef.commons.SEARCH_INGREDIENT
import com.ssafy.sungchef.data.model.responsedto.ocr.ConvertInfo
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.IconComponent
import com.ssafy.sungchef.features.component.ImageComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.screen.refrigerator.RefridgeArray
import com.ssafy.sungchef.features.ui.theme.lightGray
import com.ssafy.sungchef.util.IngredientType

private const val TAG = "RegisterReceiptScreen_성식당"
@Composable
fun RegisterReceiptScreen(
    viewModel : RegisterReceiptViewModel,
    imageUrl : String,
    onMovePage : () -> Unit
) {
    var initState by remember { mutableStateOf(true) }
    val ingredientMap by viewModel.ingredientMap.collectAsState()
    val ingredientIdList by viewModel.ingredientIdList.collectAsState()
    val registerState by viewModel.registerState.collectAsState()

    val context = LocalContext.current

    CheckUiState(
        uiState = registerState,
        onMovePage = onMovePage
    )

    Log.d(TAG, "RegisterReceiptScreen: $ingredientIdList")

    // recomposition으로 새로 통신하는 것을 방지
    if (initState) {
        viewModel.getOcrIngredient(imageUrl)
        initState = false
    }

    Log.d(TAG, "ingredientMap: $ingredientMap")

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) { 
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 80.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .padding(top = 50.dp)
            )

            TextComponent(
                text = REGISTER_RECEIPT_TITLE,
                style = MaterialTheme.typography.titleLarge
            )

            TextComponent(
                text = NEED_INGREDIENT_CONVERT,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier
                    .padding(top = 10.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(bottom = 80.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 45.dp, end = 45.dp, top = 45.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    TextComponent(
                        text = "영수증"
                    )

                    IconComponent(
                        painter = painterResource(id = R.drawable.icon_arrow)
                    )

                    TextComponent(
                        text = "수정 결과"
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp)
                ){
                    ingredientMap.forEach { (header, items) ->
                        if (items.isNotEmpty()) {
                            item {
                                Row(
                                    modifier = Modifier
                                        .padding(start = 10.dp, top = 20.dp)
                                ) {
                                    ImageComponent(
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clip(CircleShape),
                                        imageResource = IngredientType.ingredientEnglishType[header] ?: R.drawable.etc
                                    )

                                    TextComponent(
                                        modifier = Modifier
                                            .padding(start = 10.dp),
                                        text = IngredientType.transformToKorean(header)
                                    )
                                }
                            }

                            items(items) { convertInfo ->
                                ConvertIngredientItem(
                                    type = header,
                                    item = convertInfo,
                                    viewModel = viewModel
                                )
                            }
                        }
                    }
                }
            }
        }
        FilledButtonComponent(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            text = REGISTER_INGREDIENT_BUTTON
        ) {
            if (ingredientMap.containsKey("NOT_CONVERTED")) {
                Toast.makeText(context, "수정 사항을 확인해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.registerIngredient()
            }
        }
    }
}

@Composable
fun ConvertIngredientItem(
    type : String,
    item : ConvertInfo,
    viewModel : RegisterReceiptViewModel
) {
    var isSearch by remember { mutableStateOf(false) }

    ShowSearchIngredientDialog(
        isSearch = isSearch,
        viewModel = viewModel
    ){
        isSearch = false
    }

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
                    color = setColorBackground(item.ingredientId)
                )
                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                .weight(9f)
                .clickable {
                    if (item.ingredientId < 0) {
                        isSearch = true

                        // 재료 수정 후 NOT_CONVERTED를 지워주기 위해 사용
                        viewModel.notConvertedIngredient = Pair(type, item)
                    }
                }
        ){
            TextComponent(
                text = item.convertedName
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
                    viewModel.deleteIngredient(type, item)
                },
            painter = painterResource(id = R.drawable.icon_cancel)
        )
    }
}

@Composable
fun setColorBackground(id : Int) : Color {
    return if (id < 0) {
        lightGray
    } else {
        Color.White
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowSearchIngredientDialog(
    isSearch : Boolean,
    viewModel : RegisterReceiptViewModel,
    onCancel : () -> Unit
) {

    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val searchResult by viewModel.searchIngredientList.collectAsState()

    if (isSearch) {
        Dialog(
            onDismissRequest = {
                onCancel()
            }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.extraLarge
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    SearchBar(
                        query = searchText,
                        onQueryChange = viewModel::onSearchTextChange,
                        onSearch = viewModel::onSearchTextChange,
                        active = isSearching,
                        onActiveChange = {
                            viewModel.onToggleSearch()
                        },
                        modifier = Modifier
                            .fillMaxWidth(),

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
                                            viewModel.onToggleSearch()
                                            viewModel.addIngredient(value)
                                            onCancel()
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
                }
            }
        }
    }
}

@Preview
@Composable
fun RegisterReceiptPreview() {
    RegisterReceiptScreen(
        hiltViewModel(),
        "",
        {}
    )
}