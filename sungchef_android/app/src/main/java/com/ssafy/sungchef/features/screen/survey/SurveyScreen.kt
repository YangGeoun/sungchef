package com.ssafy.sungchef.features.screen.survey

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.sungchef.R
import com.ssafy.sungchef.commons.MOVE_HOME_SCREEN
import com.ssafy.sungchef.commons.NEXT_STEP
import com.ssafy.sungchef.commons.SURVEY_DESCRIPTION
import com.ssafy.sungchef.commons.SURVEY_SELECT_COUNT
import com.ssafy.sungchef.commons.SURVEY_TITLE
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.IconComponent
import com.ssafy.sungchef.features.component.ImageComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.screen.survey.navigation.SurveyViewModel
import com.ssafy.sungchef.features.ui.theme.primaryContainer50

private const val TAG = "SurveyScreen_성식당"
@Composable
fun SurveyScreen(
    viewModel : SurveyViewModel
) {

    val photoUrl = listOf(R.drawable.test_image, R.drawable.test_image, R.drawable.test_image, R.drawable.test_image
                    ,R.drawable.test_image, R.drawable.test_image, R.drawable.test_image, R.drawable.test_image)

    var selectSurveyCount by remember { mutableIntStateOf(0) }


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
                    .padding(top = 10.dp)
            )

            Text(
                text = SURVEY_SELECT_COUNT,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
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
                    text = "$selectSurveyCount / 전체 갯수",
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
                photoUrl = photoUrl,
                onSelectionChange = { newSelectionCount ->
                    // 선택된 아이템의 개수를 업데이트
                    selectSurveyCount = newSelectionCount
                }
            )
        }
        FilledButtonComponent(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            text = MOVE_HOME_SCREEN
        ) {
            // TODO 설문 완료 API 붙히기
        }
    }
}


@Composable
fun SurveyLazyGrid(
    modifier : Modifier,
    photoUrl : List<Int> = listOf(),
    onSelectionChange: (Int) -> Unit
){
    var selectedIndices by remember { mutableStateOf(listOf<Int>()) }
    Log.d(TAG, "SurveyLazyGrid: $selectedIndices")
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        // TODO 서버통신 후 itemsIndexed로 바꾸기
        itemsIndexed(photoUrl){ index, url ->

            val isSelected = index in selectedIndices

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.Center, // 수평 방향으로 가운데 정렬
                verticalAlignment = Alignment.CenterVertically // 수직 방향으로 가운데 정렬
            ){
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    // Box를 사용하여 이미지와 배경색을 겹침
                    Box(
                        modifier = Modifier
                            .background(setBackground(isSelected = isSelected)) // 선택 상태에 따라 배경색 설정
                            .size(120.dp)
                            .clip(RoundedCornerShape(15.dp))
                    ) {
                        ImageComponent(
                            modifier = Modifier
                                .clickable {
                                    // 클릭 시 선택 상태를 업데이트
                                    selectedIndices = if (isSelected) {
                                        // 이미 선택된 경우, 리스트에서 제거
                                        selectedIndices - index
                                    } else {
                                        // 선택되지 않은 경우, 리스트에 추가
                                        selectedIndices + index
                                    }
                                    // 몇 개를 클릭했는지 콜백함수로 바깥에 전달
                                    onSelectionChange(selectedIndices.size)
                                }
                                .fillMaxSize()
                                .clip(RoundedCornerShape(15.dp)), // 이미지의 모서리를 둥글게
                            imageResource = url,
                        )
                        // 아이템을 클릭하면 박스 덮어 씌우기
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .matchParentSize() // 부모와 같은 크기
                                    .background(setBackground(isSelected = true))
                                    .clip(RoundedCornerShape(15.dp)) // 배경에 둥근 모서리 적용
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .align(Alignment.Center),
                                    painter = painterResource(id = R.drawable.icon_survey_check),
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                    TextComponent(
                        text = "김치찌개"
                    )
                }
            }
        }
    }
}

// 아이템 클릭 시 덮어 씌우는 박스 background 설정
@Composable
fun setBackground(isSelected : Boolean) : Color {
    return if (isSelected) primaryContainer50
    else Color.Transparent
}

@Preview(showBackground = true)
@Composable
fun SurveyBodyPreview() {
    SurveyScreen(
        viewModel = hiltViewModel()
    )
}