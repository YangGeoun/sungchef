package com.ssafy.sungchef.features.screen.signup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.sungchef.commons.DUPLICATE_CONFIRM
import com.ssafy.sungchef.commons.INPUT_NICKNAME
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.screen.signup.common.SignupNickname
import com.ssafy.sungchef.features.screen.signup.common.SignupTopBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignupScreen(
    viewModel: SignupViewModel,
    onMoveNextPage : () -> Unit
) {
    Scaffold (
        topBar = {
            SignupTopBar(viewModel.topBarNumber.intValue)
        }
    ) { paddingValues ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box{
                Column (
                    modifier = Modifier
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 40.dp
                        ),
                ){
                    TextComponent(
                        text = INPUT_NICKNAME,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )
                    SignupNickname(true, viewModel)

                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                    )
                }
                FilledButtonComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter), // Box 내에서 하단 중앙 정렬
                    text = DUPLICATE_CONFIRM
                ) {
                    // TODO 중복확인 API 달기
                    // TODO 화면 넘길 때 Topbar 숫자 배경 바꾸기
                    // TODO 로그인 화면 완성 시 뒤로 가기 구현 (onBackPressed 포함)
                    if (!viewModel.checkNickname()){
                        viewModel.topBarNumber.intValue++
                        onMoveNextPage()
                    }
                }
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SignupScreenPreview(){
    SignupScreen(
        viewModel = SignupViewModel(),
    ){

    }
}

