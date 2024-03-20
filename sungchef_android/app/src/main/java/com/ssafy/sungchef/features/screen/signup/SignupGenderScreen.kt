package com.ssafy.sungchef.features.screen.signup

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.ssafy.sungchef.commons.FEMALE
import com.ssafy.sungchef.commons.INPUT_BIRTH
import com.ssafy.sungchef.commons.INPUT_GENDER
import com.ssafy.sungchef.commons.MALE
import com.ssafy.sungchef.commons.NEXT_STEP
import com.ssafy.sungchef.commons.SEND_FEMALE
import com.ssafy.sungchef.commons.SEND_MALE
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.GenderButtonComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.screen.signup.common.SignupNickname
import com.ssafy.sungchef.features.screen.signup.common.SignupTopBar

private const val TAG = "SignupGenderScreen_성식당"
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignupGenderScreen(
    viewModel : SignupViewModel,
    onMoveSurveyPage : () -> Unit,
    onMovePreviousPage : () -> Unit
) {
    Scaffold (
        topBar = {
            SignupTopBar(
                viewModel.topBarNumber.intValue,
                viewModel,
                onMovePreviousPage
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box {
                Column(
                    modifier = Modifier
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 40.dp
                        )
                ) {
                    TextComponent(
                        text = INPUT_GENDER,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(
                        modifier = Modifier
                            .padding(top = 15.dp)
                    )

                    SignupGender(viewModel)

                    Spacer(
                        modifier = Modifier
                            .padding(top = 15.dp)
                    )

                    SignupBirth(
                        isClickable = false,
                        disabledBorderColor = Color.LightGray,
                        viewModel = viewModel
                    )

                    Spacer(
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )

                    SignupNickname(false, viewModel)

                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                    )
                }
                FilledButtonComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter), // Box 내에서 하단 중앙 정렬
                    text = NEXT_STEP
                ) {
                    // TODO 뒤로 가기 구현 (onBackPressed 포함)
                    if (viewModel.checkGender()) {
                        onMoveSurveyPage()
                        Log.d(TAG, "SignupGenderScreen: 회원가입 성공")
                    }
                }
            }
        }
    }
}

@Composable
fun SignupGender(
    viewModel : SignupViewModel
){

    var isManSelected by remember { mutableStateOf(false) }
    var isWomanSelected by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .height(161.dp)
    ){
        GenderButtonComponent(
            gender = MALE,
            genderResource = R.drawable.gender_man,
            isSelected = isManSelected,
            onClick = {
                isManSelected = !isManSelected

                if (isManSelected) {
                    viewModel.gender.value = SEND_MALE
                    isWomanSelected = false
                }
            }
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        GenderButtonComponent(
            gender = FEMALE,
            genderResource = R.drawable.gender_woman,
            isSelected = isWomanSelected,
            onClick = {
                isWomanSelected = !isWomanSelected

                if (isWomanSelected) {
                    viewModel.gender.value = SEND_FEMALE
                    isManSelected = false
                }
            }
        )
    }
}
