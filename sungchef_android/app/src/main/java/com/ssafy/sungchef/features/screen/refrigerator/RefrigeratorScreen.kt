package com.ssafy.sungchef.features.screen.refrigerator

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ssafy.sungchef.R
import com.ssafy.sungchef.data.model.responsedto.IngredientItem
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.TextComponent

val TAG = "태그";
@Composable
fun RefrigeratorScreen(
    onMoveReceiptScreen : () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize(1f)) {
        RefridgeArray(onMoveReceiptScreen)
//        FridgeComponent(image = painterResource(id = R.drawable.fruit), number = 2, labelText = "과일")
    }
}

@Composable
fun FridgeComponent(
    image: Painter, // 여기서 image는 Painter 객체를 받습니다. 예를 들면 painterResource(id = R.drawable.fruit_image) 같은 것을 사용하세요.
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
            painter = image,
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
    onMoveReceiptScreen : () -> Unit
){
    val items = listOf(
        IngredientItem(painterResource(id = R.drawable.fruit), 3, "과일"),
        IngredientItem(painterResource(id = R.drawable.vegetable), 3, "채소"),
        IngredientItem(painterResource(id = R.drawable.rice_grain), 3, "쌀/잡곡"),
        IngredientItem(painterResource(id = R.drawable.meat_egg), 3, "육류"),
        IngredientItem(painterResource(id = R.drawable.fish), 4, "수산"),
        IngredientItem(painterResource(id = R.drawable.milk), 4, "유제품"),
        IngredientItem(painterResource(id = R.drawable.sauce), 3, "조미료"),
        IngredientItem(painterResource(id = R.drawable.etc), 3, "기타"),

    )
    val numberOfColumns = 2

    var showDialog by remember { mutableStateOf(false) }
    var showNestedDialog by remember { mutableStateOf(false) }

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
                            }
                            Spacer(modifier = Modifier.padding(30.dp))
                            FridgeComponent(items[i+1].painter, items[i+1].num, items[i+1].label){
                                Log.d(TAG, "RefridgeArray: FridgeComponent clicked")
                                showDialog = true
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

    val foodItems = listOf<String>("삼겹살","소고기")
    var selectedFoodItem by remember { mutableStateOf<String?>(null) }

    // 첫 번째 다이얼로그
    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                // 라디오 버튼들과 음식 이름들을 표시
                foodItems.forEach { foodItem ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedFoodItem = foodItem }
                            .padding(vertical = 8.dp)
                    ) {
                        RadioButton(
                            selected = selectedFoodItem == foodItem,
                            onClick = { selectedFoodItem = foodItem }
                        )
//                        Image(
//                            painter = painterResource(id = foodItem.imageResource),
//                            contentDescription = foodItem.name,
//                            modifier = Modifier.size(40.dp)
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = foodItem)
                    }
                }

                // 삭제하기 버튼
                Button(
                    onClick = {
                        selectedFoodItem?.let {
                            showNestedDialog = true
//                            showDialog = false
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 16.dp),
                    enabled = selectedFoodItem != null // 선택된 항목이 있을 때만 버튼 활성화
                ) {
                    Text("삭제하기")
                }
            }
        }
    }

    // 중첩된 다이얼로그
    if (showNestedDialog) {
        Dialog(onDismissRequest = { showNestedDialog = false }) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("중첩된 다이얼로그")
                Spacer(Modifier.height(8.dp))
                Button(onClick = { showNestedDialog = false }) {
                    Text("닫기")
                }
            }
        }
    }


}

@Preview
@Composable
fun FridgeComponentPreview() {
    RefridgeArray(
        {}
    )
}
