package com.ssafy.sungchef.features.screen.mypage

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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ssafy.sungchef.R
import com.ssafy.sungchef.data.model.responsedto.UserProfile
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import com.ssafy.sungchef.features.component.ImageComponent
import com.ssafy.sungchef.features.component.LazyVerticalGridComponent
import com.ssafy.sungchef.features.component.TopAppBarComponent
import com.ssafy.sungchef.features.screen.home.HomeScreen
import com.ssafy.sungchef.features.screen.home.HomeViewModel
import com.ssafy.sungchef.features.screen.mypage.navigation.settingNavigationRoute


val TAG = "MyPage"
var tstcnt = 0
var isUserSimpleFinished = false
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(navController: NavController, viewModel: MyPageViewModel){
    
    val userSimple by viewModel.userSimpleData.collectAsState()
    val makeRecipeList by viewModel.makeRecipeListData.collectAsState()

    

    LaunchedEffect(true) {
        viewModel.getUserSimple()
    }

    // 현재 선택된 탭의 인덱스를 추적하는 상태 변수
    var selectedTabIndex by remember { mutableStateOf(0) }

    // 탭의 제목을 나열
    val tabTitles = listOf("업로드", "즐겨찾기")

    Scaffold(
        topBar = { TopAppBarComponent()}
    ) { paddingValues ->
            Surface(
                modifier = Modifier
//                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                Column {
                    Log.d(TAG, "MyPageScreen: 두번찍히니?")
                    Log.d(TAG, "MyPageScreen: $userSimple")
//                    val userSimple = UserSimple(0,"", UserProfile("","",0,0))

                    if(userSimple?.code!=0 && !isUserSimpleFinished){
                        //왜 for문 진입 두번?
                        Log.d(TAG, "MyPageScreen: for문진입")
                        isUserSimpleFinished = true
                        val makeRecipeCount = userSimple!!.data.makeRecipeCount
                        val pageCount = if(makeRecipeCount%9==0) makeRecipeCount/9 else {makeRecipeCount/9+1}
                        for (page in 1..pageCount){
                            //여기 await를 안 하면.. 1,2페이지 순서로 호출한다고 해서 그 순서대로 list에 add된다는 보장 X
                            Log.d(TAG, "MyPageScreen: cnt : ${tstcnt++}")
                            viewModel.getMakeRecipeList(page)
                        }
                        Log.d(TAG, "MyPageScreen: 업로드 리스트 ${makeRecipeList}")
                    }
                    Profile(navController, userSimple!!)

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

                    var makeRecipeURLList = mutableListOf<String>()
                    for (lst in makeRecipeList.data.makeRecipeList){
                        makeRecipeURLList.add(lst.makeRecipeImage)
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
                        0 -> LazyVerticalGridComponent(photoUrls = makeRecipeURLList, onClick = {
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
@Preview
fun preview(){
    val navController = rememberNavController()
    MyPageScreen(navController = navController , hiltViewModel())
}


@Composable
fun Profile(navController: NavController, userSimple : UserSimple){
    Row(modifier = Modifier
        .height(80.dp)
        .padding(start = 16.dp)) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ){
            ProfileImage(userSimple)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Nickname(navController, userSimple)
            UserRecord(userSimple)

        }
    }
}

@Composable
fun ProfileImage(userSimple: UserSimple){
    var showDialog by remember { mutableStateOf(false) }

    ImageComponent(modifier = Modifier
        .size(70.dp)
        .clickable { showDialog = true }
        .clip(CircleShape), imageResource = userSimple.data.userImage)
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop // 이미지가 Dialog 내부에 맞게 조정됨
            )
        }
    }
}

@Composable
fun Nickname(navController: NavController, userSimple: UserSimple){
    val TAG = ""
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text=userSimple.data.userNickname, fontSize = 20.sp )
        Image(
            imageVector = Icons.Filled.Settings,
            contentDescription = "이미지 설명",
            modifier = Modifier
                .clickable { /* 클릭 시 수행할 작업 */
                    navController.navigate(settingNavigationRoute)
                }
                .size(32.dp)
                .padding(start = 10.dp)
        )

    }
}

@Composable
fun UserRecord(userSimple: UserSimple){
    var uploads = userSimple.data.makeRecipeCount
    var favorites = userSimple.data.bookmarkRecipeCount
    Row() {
        Text(text = "업로드 $uploads    ")
        Text(text = "즐겨찾기 $favorites")
    }
}
