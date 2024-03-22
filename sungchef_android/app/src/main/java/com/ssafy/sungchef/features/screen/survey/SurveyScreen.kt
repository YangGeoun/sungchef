package com.ssafy.sungchef.features.screen.survey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.sungchef.R
import com.ssafy.sungchef.commons.NEXT_STEP
import com.ssafy.sungchef.commons.SURVEY_DESCRIPTION
import com.ssafy.sungchef.commons.SURVEY_TITLE
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.ImageComponent
import com.ssafy.sungchef.features.component.TextComponent


@Composable
fun SurveyScreen(

) {

    val photoUrl = listOf(R.drawable.test_image, R.drawable.test_image, R.drawable.test_image, R.drawable.test_image
                    ,R.drawable.test_image, R.drawable.test_image, R.drawable.test_image, R.drawable.test_image)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 40.dp
                )
        ) {
            TextComponent(
                text = SURVEY_TITLE,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(
                modifier = Modifier
                    .padding(top = 10.dp)
            )

            TextComponent(
                text = SURVEY_DESCRIPTION,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier
                    .padding(top = 30.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(), // Row를 Column의 너비만큼 채움
                horizontalArrangement = Arrangement.End // 내부 컴포넌트를 오른쪽으로 정렬
            ) {
                TextComponent(
                    // TODO 서버통신 후 전체 갯수 받아와서 표시
                    text = "3/7",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            SurveyLazyGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .weight(1f)
                    .padding(bottom = 60.dp),
                photoUrl = photoUrl
            )
        }
        FilledButtonComponent(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            text = NEXT_STEP
        ) {
            // TODO 설문 완료 API 붙히기
        }
    }
}


@Composable
fun SurveyLazyGrid(
    modifier : Modifier,
    photoUrl : List<Int> = listOf()
){

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        // TODO 서버통신 후 itemsIndexed로 바꾸기
        itemsIndexed(photoUrl){ index, url ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center, // 수평 방향으로 가운데 정렬
                verticalAlignment = Alignment.CenterVertically // 수직 방향으로 가운데 정렬
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ImageComponent(
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .size(120.dp),
                        imageResource = url,
                    )
                    TextComponent(
                        text = "김치찌개"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SurveyBodyPreview() {
    SurveyScreen()
}