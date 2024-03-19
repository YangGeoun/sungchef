package com.ssafy.sungchef.features.screen.signup

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.ssafy.sungchef.commons.BIRTH
import com.ssafy.sungchef.commons.BIRTH_FORMAT
import com.ssafy.sungchef.commons.DUPLICATE_CONFIRM
import com.ssafy.sungchef.commons.INPUT_BIRTH
import com.ssafy.sungchef.commons.INPUT_NICKNAME
import com.ssafy.sungchef.commons.NEXT_STEP
import com.ssafy.sungchef.features.component.DatePickerDialogComponent
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.IconComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.component.TextFieldComponent
import com.ssafy.sungchef.features.component.TopAppBarComponent
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Locale

private const val TAG = "SignupBirthScreen_성식당"
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignupBirthScreen() {
    Scaffold (
        topBar = {
            SignupTopBar()
        }
    ) {
        paddingValues ->

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
                        text = INPUT_BIRTH,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )

                    SignupBirth()
                    SignupNickname(false)

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
                    // TODO navigation으로 screen 이동
                    // TODO 화면 넘길 때 Topbar 숫자 배경 바꾸기
                    // TODO ViewModel에 생년월일 저장
                    // TODO 뒤로 가기 구현 (onBackPressed 포함)
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignupBirth(
    isClickable: Boolean = true,
    disabledBorderColor : Color = MaterialTheme.colorScheme.primary
){
    var birth by remember { mutableStateOf("") }

    val date = remember { mutableStateOf(LocalDate.now())}
    val isOpen = remember { mutableStateOf(false) }

    TextFieldComponent(
        modifier = Modifier
            .then(
                if (isClickable) Modifier.clickable {
                    isOpen.value = true
                }
                else Modifier
            ),
        value = birth,
        onValueChange = {
            birth = it
        },
        hintText = BIRTH,
        trailingIcon = {
            IconButton(
                onClick = {
                    if (isClickable) isOpen.value = true
                }
            ) {
                IconComponent(
                    size = 40,
                    painter = painterResource(id = R.drawable.icon_calendar))
            }
        },
        supportingText = {
            TextComponent(
                text = BIRTH_FORMAT,
                color = MaterialTheme.colorScheme.primary
            )
        },
        enabled = false,
        disabledBorderColor = disabledBorderColor
    )

    if (isOpen.value) {
        DatePickerDialogComponent(
            onAccept = { time ->
                isOpen.value = false

                time?.let {
                    date.value = Instant
                        .ofEpochMilli(it)
                        .atZone(ZoneId.of("Asia/Seoul"))
                        .toLocalDate()

                    birth = date.value.toString()
                }
            },
            onCancel = {
                isOpen.value = false
            }
        )
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SignupBirthPreview(){
    SignupBirthScreen()
}
