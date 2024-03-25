package com.ssafy.sungchef.features.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.sungchef.R
import com.ssafy.sungchef.features.component.ImageComponent
import com.ssafy.sungchef.features.screen.survey.SurveyScreen


@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .padding(top = 100.dp)
        )
        ImageComponent(
            modifier = Modifier
                .size(300.dp),
            imageResource = R.drawable.sungchef
        )

        Spacer(
            modifier = Modifier
                .padding(top = 60.dp)
        )

        Column {
            ImageComponent(
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp),
                imageResource = R.drawable.kakao_login
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginBodyPreview() {
    LoginScreen(

    )
}