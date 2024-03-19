package com.ssafy.sungchef.features.screen.signup

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
import com.ssafy.sungchef.R
import com.ssafy.sungchef.commons.DUPLICATE_CONFIRM
import com.ssafy.sungchef.commons.INPUT_NICKNAME
import com.ssafy.sungchef.commons.NICKNAME
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.IconComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.component.TextFieldComponent
import com.ssafy.sungchef.features.component.TopAppBarComponent

@Composable
fun SignupScreen() {
    Scaffold (
        topBar = {
            SignupTopBar()
        }
    ) { paddingValues ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
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
                SignupNickname()

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                FilledButtonComponent(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = DUPLICATE_CONFIRM
                ) {
                    // TODO navigation으로 screen 이동
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupTopBar() {
    TopAppBarComponent(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        navigationIcon = {
            IconComponent(
                painter = painterResource(id = R.drawable.icon_back)
            )
        },
        title = {
            SignupStep()
        }
    )
}

@Composable
fun SignupStep() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        CircleBackgroundText(num = 1)

        Spacer(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
        )

        CircleBackgroundText(num = 2)

        Spacer(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
        )
        CircleBackgroundText(num = 3)
    }
}

@Composable
fun CircleBackgroundText(num : Int){

    val activeColor = MaterialTheme.colorScheme.inversePrimary
    val nonActiveColor = MaterialTheme.colorScheme.primaryContainer

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(50.dp)
            .height(50.dp)
            .background(
                color = activeColor,
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
fun SignupNickname() {

    var nickname by remember { mutableStateOf("") }

    TextFieldComponent(
        value = nickname,
        onValueChange = {
            nickname = it
        },
        hintText = NICKNAME,
        trailingIcon = {
            IconComponent(
                painter = painterResource(id = R.drawable.icon_input_delete) 
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SignupScreenPreview(){
    SignupScreen()
}

