package com.ssafy.sungchef.features.screen.refrigerator

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.sungchef.data.model.responsedto.IngredientListData
import com.ssafy.sungchef.domain.model.ingredient.IngredientId
import com.ssafy.sungchef.domain.model.ingredient.IngredientList
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.OutlinedButtonComponentNotMax
import com.ssafy.sungchef.features.component.TextComponent
import java.lang.StringBuilder

val TAG = "RefrigeratorScreen_태그";
@Composable
fun RefrigeratorScreen(
    onMoveReceiptScreen : () -> Unit,
    viewModel: RefrigeratorViewModel,
    onChange : () -> Unit
) {
    LaunchedEffect(key1 = true){
        onChange()
    }

    val isRefrigeratorEmpty by viewModel.isRefrigeratorEmpty.collectAsState()
    val isRefreshNeeded by viewModel.isRefreshNeeded.collectAsState()
    val context = LocalContext.current


    Column(modifier = Modifier.fillMaxSize(1f)) {
        RefridgeArray(viewModel, onMoveReceiptScreen)
//        FridgeComponent(image = painterResource(id = R.drawable.fruit), number = 2, labelText = "과일")
    }

    if(isRefrigeratorEmpty){
        Log.d(TAG, "RefrigeratorScreen: isRefrigeratorEmpty")
        viewModel.setIsRefrigeratorEmptyFalse()
        Toast.makeText(context, "냉장고에 음식이 없습니다. 추가해보세요!", Toast.LENGTH_SHORT).show()
    }
    if(isRefreshNeeded){
        viewModel.setIsRefreshNeeded()
    }
}

@Composable
fun FridgeComponent(
    image: Int, // 여기서 image는 Painter 객체를 받습니다. 예를 들면 painterResource(id = R.drawable.fruit_image) 같은 것을 사용하세요.
    number: Int,
    labelText: String,
    onClick : () -> Unit
) {
    Box(modifier = Modifier
        .width(100.dp)
        .height(120.dp)
//        ,contentAlignment = Alignment.Center
        ,) { // Box를 사용하여 겹치는 레이아웃을 만듭니다.
        Image(
            painter = painterResource(id = image),
            contentDescription = "Fruits", // contentDescription은 접근성을 위한 것입니다.
            contentScale = ContentScale.Crop,
            modifier = Modifier

                .size(90.dp) // 원하는 크기로 조절하세요.
                .shadow(8.dp, RoundedCornerShape(35.dp), clip = false)
                .clip(CircleShape) // 이미지를 원형으로 클립합니다.
                .align(Alignment.TopCenter)
                .clickable {
                    Log.d(TAG, "FridgeComponent: Image ")
                    onClick()
                }
        )

        // 빨간색 동그라미 안에 숫자를 표시합니다.
        Badge( // 배지
            containerColor = Color.Red, // 배지의 배경 색상
            contentColor = Color.White, // 배지의 내용 색상,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(start = 0.dp, top = 5.dp)
        ) {
            Text( // 배지에 들어갈 숫자
                text = number.toString(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier
//                    .size(20.dp) // 배지의 크기
//                    .width(30.dp)
                    .padding(3.dp)
                    .clip(CircleShape) // 배지를 원형으로 클리핑
            )
        }

        Text( // 이미지 아래의 텍스트 라벨
            text = labelText,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center) // 하단 오른쪽 정렬
                .padding(top = 90.dp)
        )
    }
}

@Composable
fun RefridgeArray(
    viewModel: RefrigeratorViewModel,
    onMoveReceiptScreen: () -> Unit
){
    val items by viewModel.items.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var showNestedDialog by remember { mutableStateOf(false) }
    var selectedFoodCategory by remember { mutableIntStateOf(-1) }

    Column {
        TextComponent(text = "냉장고를 관리해보세요",
            fontSize = 36.sp, modifier = Modifier.padding(10.dp))
        TextComponent(text = "부족하거나 사용한 재료를 정리해주세요!",
            fontSize = 18.sp, modifier = Modifier.padding(10.dp),
            color = MaterialTheme.colorScheme.primary)
        Box(Modifier.fillMaxSize()){

            Column {
//                Image(painter = painterResource(id = R.drawable.refridge),
//                    contentDescription = "",
//                    modifier = Modifier.height(550.dp)
//                        .fillMaxWidth(),
//                    contentScale = ContentScale.Crop
//                    )
            }

            Box(){

                Column(modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.Center)) {
                    for (i in 0 until items.size step 2) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                            ,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            FridgeComponent(items[i].painter, items[i].num, items[i].label){
                                Log.d(TAG, "RefridgeArray: FridgeComponent clicked")
                                showDialog = true
                                selectedFoodCategory = i;
                            }
                            Spacer(modifier = Modifier.padding(30.dp))
                            FridgeComponent(items[i+1].painter, items[i+1].num, items[i+1].label){
                                Log.d(TAG, "RefridgeArray: FridgeComponent clicked")
                                showDialog = true
                                selectedFoodCategory = i+1;
                            }
                        }
                    }
                }
            }

            // 신규 등록 버튼
            FilledButtonComponent(text = "신규 등록",
                modifier = Modifier.align(Alignment.BottomCenter)) {
                onMoveReceiptScreen()
            }
        }
    }

    var foodItems = mutableListOf<IngredientListData>(IngredientListData("기본재료1", 1),IngredientListData("기본재료2", 2))
    var selectedItems by remember { mutableStateOf(mutableSetOf<IngredientListData>()) }
    val items_detail by viewModel.items_detail.collectAsState()

    // 첫 번째 다이얼로그
    if (showDialog) {
        Log.d(TAG, "RefridgeArray: ${items_detail[selectedFoodCategory]}")
        foodItems = items_detail[selectedFoodCategory]
        Dialog(onDismissRequest = {
            showDialog = false
            selectedItems.clear()
        }) {
            Surface(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                color = Color.White,
                shape = MaterialTheme.shapes.medium
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.padding(bottom = 10.dp)) {
//                        ImageComponent(modifier = Modifier.padding(end = 10.dp), imageResource = items[selectedFoodCategory].painter)
                        Image(painter = painterResource(id = items[selectedFoodCategory].painter), contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .align(Alignment.CenterVertically)
//                                .padding(end = 10.dp)
                        )
                        TextComponent(text = items[selectedFoodCategory].label, fontSize = 24.sp,
                            modifier = Modifier.padding(start = 10.dp))

                    }
                    // 음식 목록과 체크박스를 표시
                    foodItems.forEach { foodItem ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { // 이제 여기서 체크 상태를 토글합니다.
                                    val currentSet = selectedItems.toMutableSet()
                                    if (selectedItems.contains(foodItem)) {
                                        currentSet.remove(foodItem)
                                    } else {
                                        currentSet.add(foodItem)
                                    }
                                    selectedItems = currentSet
                                }
                                .padding(vertical = 8.dp)
                        ) {
                            Checkbox(
                                checked = selectedItems.contains(foodItem),
                                onCheckedChange = null // `Row`가 클릭 처리를 담당하므로 null로 설정합니다.
                            )
                            Text(
                                text = foodItem.ingredientName,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .weight(1f) // 체크박스 옆의 텍스트가 모든 공간을 차지하도록 합니다.
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 확인 버튼
                    Button(
                        onClick = {
//                            onConfirm(selectedItems.toList())
//                            selectedItems.clear() // 선택 상태 초기화
                            selectedItems?.let {
                                showNestedDialog = true
//                            showDialog = false
                            }
                        },
                        modifier = Modifier.align(Alignment.End),
                        enabled = selectedItems.isNotEmpty()
                    ) {
                        Text("삭제하기")
                    }
                }
            }
        }
    }

    // 중첩된 다이얼로그
    if (showNestedDialog) {
        Dialog(onDismissRequest = { showNestedDialog = false }) {
            Surface(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                color = Color.White,
                shape = MaterialTheme.shapes.small
            ){
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var nestedText : StringBuilder= StringBuilder();
                    selectedItems.forEach {
                        nestedText.append(it.ingredientName + ", ")
                    }

                    Text("${nestedText}을(를) 냉장고에서 삭제하시겠습니까?")
                    Spacer(Modifier.height(8.dp))
                    Row {
                        OutlinedButtonComponentNotMax(text = "닫기", borderColor = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(end = 10.dp)) {
                            showNestedDialog = false
                        }
                        Button(onClick = {
                            showNestedDialog = false
                            showDialog = false
                            var deleteList : IngredientList = IngredientList()
                            selectedItems.forEach {
                                deleteList.ingredientList.add(IngredientId(it.ingredientId))
                            }
                            selectedItems.clear()

                            Log.d(TAG, "RefridgeArray: 삭제리스트 : $deleteList")
                            //API 통신하여 삭제
                            viewModel.deleteFridgeIngredient(deleteList)
                        }) {
                            Text("삭제")
                        }

                    }
                }
            }
        }
    }


}

@Preview
@Composable
fun FridgeComponentPreview() {
    RefridgeArray(
        hiltViewModel()
    ) {}
}
