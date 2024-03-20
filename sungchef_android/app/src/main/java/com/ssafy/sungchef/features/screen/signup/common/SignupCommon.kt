package com.ssafy.sungchef.features.screen.signup.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssafy.sungchef.R
import com.ssafy.sungchef.commons.EMPTY
import com.ssafy.sungchef.commons.LIMIT_INPUT_NICKNAME
import com.ssafy.sungchef.commons.NICKNAME
import com.ssafy.sungchef.features.component.IconButtonComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.component.TextFieldComponent
import com.ssafy.sungchef.features.component.TopAppBarComponent
import com.ssafy.sungchef.features.screen.signup.SignupViewModel

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
                    // TODO 이전 화면 전환 구현
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
        CircleBackgroundText(num = 1, setCircleColor(
                num = 1,
                pageNumber = pageNumber
            )
        )


        Spacer(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
        )

        CircleBackgroundText(num = 2, setCircleColor(
                num = 2,
                pageNumber = pageNumber
            )
        )

        Spacer(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
        )
        CircleBackgroundText(num = 3, setCircleColor(
                num = 3,
                pageNumber = pageNumber
            )
        )
    }
}

@Composable
fun CircleBackgroundText(
    num : Int,
    color : Color
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(50.dp)
            .height(50.dp)
            .background(
                color = color,
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