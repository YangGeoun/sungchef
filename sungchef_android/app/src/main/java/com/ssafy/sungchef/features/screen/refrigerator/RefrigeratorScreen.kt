package com.ssafy.sungchef.features.screen.refrigerator

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.sungchef.R
import com.ssafy.sungchef.data.model.responsedto.IngredientItem
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.TextComponent

@Composable
fun RefrigeratorScreen(
) {
    Column(modifier = Modifier.fillMaxSize(1f)) {
        RefridgeArray()
//        FridgeComponent(image = painterResource(id = R.drawable.fruit), number = 2, labelText = "과일")
    }
}

@Composable
fun FridgeComponent(
    image: Painter, // 여기서 image는 Painter 객체를 받습니다. 예를 들면 painterResource(id = R.drawable.fruit_image) 같은 것을 사용하세요.
    number: Int,
    labelText: String
) {
    Box(modifier = Modifier.size(130.dp)
        ,contentAlignment = Alignment.Center,) { // Box를 사용하여 겹치는 레이아웃을 만듭니다.
        Image(
            painter = image,
            contentDescription = "Fruits", // contentDescription은 접근성을 위한 것입니다.
            contentScale = ContentScale.Crop,
            modifier = Modifier

                .size(90.dp) // 원하는 크기로 조절하세요.
                .clip(CircleShape) // 이미지를 원형으로 클립합니다.

        )

        // 빨간색 동그라미 안에 숫자를 표시합니다.
        Badge( // 배지
            containerColor = Color.Red, // 배지의 배경 색상
            contentColor = Color.White, // 배지의 내용 색상,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
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
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomEnd) // 하단 오른쪽 정렬
//                .padding(8.dp) // 패딩을 주어 좀 더 깔끔하게 정렬
        )
    }
}

@Composable
fun RefridgeArray(){
    val items = listOf(
        IngredientItem(painterResource(id = R.drawable.fruit), 3, "과일"),
        IngredientItem(painterResource(id = R.drawable.vegetable), 3, "채소"),
        IngredientItem(painterResource(id = R.drawable.rice_grain), 3, "쌀/잡곡"),
        IngredientItem(painterResource(id = R.drawable.meat_egg), 3, "육류"),
        IngredientItem(painterResource(id = R.drawable.fish), 4, "수산"),
        IngredientItem(painterResource(id = R.drawable.milk), 4, "유제품"),
        IngredientItem(painterResource(id = R.drawable.sauce), 3, "소스/양념"),
        IngredientItem(painterResource(id = R.drawable.etc), 3, "기타"),

    )
    val numberOfColumns = 2
    Column {
        TextComponent(text = "냉장고를 관리해보세요",
            fontSize = 36.sp, modifier = Modifier.padding(10.dp))
        TextComponent(text = "부족하거나 사용한 재료를 정리해주세요!",
            fontSize = 18.sp, modifier = Modifier.padding(10.dp),
            color = MaterialTheme.colorScheme.primary)
        Box(Modifier.fillMaxSize()){

            Column {
                Image(painter = painterResource(id = R.drawable.refridge),
                    contentDescription = "",
                    modifier = Modifier.height(550.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                    )
            }


            Column(){



                // 2열 그리드
                LazyVerticalGrid(
                    columns = GridCells.Fixed(numberOfColumns),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        //            modifier = Modifier.fillMaxHeight()
                ) {
                    items(items) { item ->
                        // 각 셀을 나타내는 컴포넌트
                        FridgeComponent(item.painter, item.num, item.label)
                    }

                    item {
                        // 추가 공간을 만들어 그리드의 마지막 줄에 버튼을 배치할 수 있도록 합니다.
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }


            }

            // 신규 등록 버튼
            FilledButtonComponent(text = "신규 등록",
                modifier = Modifier.align(Alignment.BottomCenter)) {

            }
        }
    }


}

@Preview
@Composable
fun FridgeComponentPreview() {
    RefridgeArray()
}
