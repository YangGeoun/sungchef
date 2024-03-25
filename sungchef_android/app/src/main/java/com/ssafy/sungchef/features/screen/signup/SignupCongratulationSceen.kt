package com.ssafy.sungchef.features.screen.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation

import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssafy.sungchef.R
import com.ssafy.sungchef.commons.CONGRATULATION
import com.ssafy.sungchef.commons.GO_SURVEY
import com.ssafy.sungchef.features.component.TextComponent

@Composable
fun SignupCongratulationScreen(
    onMoveSurveyPage : () -> Unit,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.signup_congratulation)
    )

    Column(
        modifier = Modifier
            .clickable {
                onMoveSurveyPage()
            }
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(15.dp))
                .padding(16.dp), // 내부 컨텐츠와의 간격을 주기 위해 패딩을 추가
            contentAlignment = Alignment.Center // Box 내부 컨텐츠의 정렬을 중앙으로 설정
        ) {
            Column {
                TextComponent(
                    text = CONGRATULATION,
                    style = MaterialTheme.typography.headlineMedium
                )
                TextComponent(
                    modifier = Modifier.padding(top = 10.dp),
                    text = GO_SURVEY,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        LottieAnimation(
            composition = composition,
            iterations = 50 // 애니메이션을 50번 반복
        )
    }


}

@Preview(showBackground = true)
@Composable
fun CongrScreenPreview() {
    SignupCongratulationScreen(
        {}
    )
}
