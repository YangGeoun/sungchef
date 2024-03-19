package com.ssafy.sungchef.features.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ssafy.sungchef.R
import com.ssafy.sungchef.features.component.ImageComponent
import com.ssafy.sungchef.features.component.LazyVerticalGridComponent
import com.ssafy.sungchef.features.component.TopAppBarComponent


val TAG = "MyPage"
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MyPage(){
    // 현재 선택된 탭의 인덱스를 추적하는 상태 변수
    var selectedTabIndex by remember { mutableStateOf(0) }

    // 탭의 제목을 나열
    val tabTitles = listOf("Tab 1", "Tab 2")

    Scaffold(
        topBar = { TopAppBarComponent()}
    ) { paddingValues ->
            Surface(
                modifier = Modifier
//                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                Column {


                    Profile()

                    // TabRow를 사용하여 탭 바 생성
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
//                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colorScheme.primary
                    ) {
                        // 각 탭에 대해 반복
                        tabTitles.forEachIndexed { index, title ->
                            Tab(
                                text = { Text(title) },
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                // 선택된 탭과 그렇지 않은 탭의 스타일을 다르게 할 수 있습니다.
                            )
                        }
                    }

                    val lst1 = mutableListOf<String>()
                    lst1.add("원1")
                    lst1.add("투1")
                    lst1.add("쓰1리")
                    lst1.add("포1")
                    lst1.add("ㅍ1ㅇㅂ")
                    lst1.add("ㅅ1ㅅ")
                    lst1.add("ㅅ1ㅂ")
                    lst1.add("ㄷ1ㄷ")
                    lst1.add("ㅜ1ㅇ")
                    lst1.add("ㅌ1")
                    val lst2 = mutableListOf<String>()
                    lst2.add("원2")
                    lst2.add("투2")
                    lst2.add("쓰2리")
                    lst2.add("포2")
                    lst2.add("ㅍ2ㅇㅂ")



                    // 선택된 탭의 내용을 표시
                    when (selectedTabIndex) {
                        0 -> LazyVerticalGridComponent(photoUrls = lst1, onClick = {
                            Log.d(
                                TAG,
                                "MyPage1: $it"
                            )}, )
                        1 -> LazyVerticalGridComponent(photoUrls = lst2, onClick = {
                            Log.d(
                                TAG,
                                "MyPage2: $it"
                            )}, )
                    }



                }
            }
    }
}


@Composable
fun Profile(){
    Row(modifier = Modifier
        .height(80.dp)
        .padding(start = 16.dp)) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ){
            ProfileImage()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Nickname()
            UserRecord()

        }
    }
}

@Composable
fun ProfileImage(){
    var showDialog by remember { mutableStateOf(false) }

    ImageComponent(modifier = Modifier
        .size(70.dp)
        .clickable { showDialog = true }
        .clip(CircleShape), imageResource = R.drawable.test_image)
    val imagePainter: Painter = painterResource(id = R.drawable.test_image) // 프로필사진

    // Dialog를 통해 이미지 확대 보기
    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            // 확대된 이미지를 보여주는 Dialog 내용
            Image(
                painter = imagePainter,
                contentDescription = "확대된 이미지",
                modifier = Modifier.fillMaxWidth().height(300.dp),
                contentScale = ContentScale.Crop // 이미지가 Dialog 내부에 맞게 조정됨
            )
        }
    }
}

@Composable
fun Nickname(){
    val TAG = ""
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text="south_hyun99", fontSize = 20.sp )
        Image(
            imageVector = Icons.Filled.Settings,
            contentDescription = "이미지 설명",
            modifier = Modifier
                .clickable { /* 클릭 시 수행할 작업 */
                    Log.d(TAG, "Nickname: Setting Clicked")
                }
                .size(32.dp)
                .padding(start = 10.dp)
        )

    }
}

@Composable
fun UserRecord(){
    var uploads = 3
    var favorites = 5
    Row() {
        Text(text = "업로드 $uploads    ")
        Text(text = "즐겨찾기 $favorites")
    }
}



