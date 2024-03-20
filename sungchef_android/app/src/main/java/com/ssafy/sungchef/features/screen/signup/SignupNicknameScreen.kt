package com.ssafy.sungchef.features.screen.signup

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.sungchef.R
import com.ssafy.sungchef.commons.DUPLICATE_CONFIRM
import com.ssafy.sungchef.commons.EMPTY
import com.ssafy.sungchef.commons.INPUT_NICKNAME
import com.ssafy.sungchef.commons.LIMIT_INPUT_NICKNAME
import com.ssafy.sungchef.commons.NICKNAME
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.IconButtonComponent
import com.ssafy.sungchef.features.component.IconComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.component.TextFieldComponent
import com.ssafy.sungchef.features.component.TopAppBarComponent
import com.ssafy.sungchef.features.navigation.NavGraph

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupTopBar(pageNumber : Int) {
    TopAppBarComponent(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        navigationIcon = {
            IconButtonComponent(
                onClick = {
                    // TODO Login 화면 전환 기능 구현
                },
                painter = painterResource(id = R.drawable.icon_back)
            )
        },
        title = {
            SignupStep(pageNumber)
        }
    )
}

@Composable
fun SignupStep(pageNumber : Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        CircleBackgroundText(num = 1, pageNumber)

        Spacer(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
        )

        CircleBackgroundText(num = 2, pageNumber)

        Spacer(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
        )
        CircleBackgroundText(num = 3, pageNumber)
    }
}

@Composable
fun CircleBackgroundText(
    num : Int,
    pageNumber: Int
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(50.dp)
            .height(50.dp)
            .background(
                color = setCircleColor(
                    num = num,
                    pageNumber = pageNumber
                ),
                shape = CircleShape
            ) // 원형 배경 색상과 모양 설정
    ) { // 텍스트 주변에 패딩을 줘서 배경과의 간격을 생성
        TextComponent(
            text = "$num",
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium
        ) // 텍스트 컴포넌트
    }
}


@Composable
fun SignupNickname(
    enable : Boolean,
    viewModel : SignupViewModel
) {

    var nickname by remember { viewModel.nickname }

    TextFieldComponent(
        enabled = enable,
        value = nickname,
        onValueChange = {
            nickname = it
        },
        hintText = NICKNAME,
        trailingIcon = {
            IconButtonComponent(
                onClick = {
                  nickname = EMPTY
                },
                painter = painterResource(id = R.drawable.icon_input_delete),
                enabled = enable
            )
        },
        supportingText = {
            TextComponent(
                text = LIMIT_INPUT_NICKNAME,
                color = textErrorColor(
                    isError = viewModel.checkNickname()
                )
            )
        },
        isError = viewModel.checkNickname()
    )
}

@Composable
fun textErrorColor(isError : Boolean) : Color {
    return if (isError) {
        Color.Red
    } else {
        MaterialTheme.colorScheme.primary
    }
}

@Composable
fun setCircleColor(num : Int, pageNumber: Int) : Color {
    val activeColor = MaterialTheme.colorScheme.inversePrimary
    val nonActiveColor = MaterialTheme.colorScheme.primaryContainer

    return if (num == pageNumber) {
        activeColor
    } else {
        nonActiveColor
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

