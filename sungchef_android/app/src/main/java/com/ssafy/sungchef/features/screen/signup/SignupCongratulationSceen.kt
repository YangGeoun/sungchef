package com.ssafy.sungchef.features.screen.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation

import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssafy.sungchef.R
import com.ssafy.sungchef.commons.CONGRATULATION
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.screen.survey.SurveyScreen
import java.lang.reflect.Modifier

@Composable
fun SignupCongratulationScreen(
    onMoveSurveyPage : () -> Unit,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.signup_congratulation)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextComponent(
            text = CONGRATULATION,
            style = MaterialTheme.typography.headlineMedium
        )

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
