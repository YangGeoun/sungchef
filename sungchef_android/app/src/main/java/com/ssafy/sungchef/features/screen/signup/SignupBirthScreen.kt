package com.ssafy.sungchef.features.screen.signup

import android.os.Build
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.sungchef.R
import com.ssafy.sungchef.commons.BIRTH
import com.ssafy.sungchef.commons.BIRTH_FAIL_MESSAGE
import com.ssafy.sungchef.commons.BIRTH_FORMAT
import com.ssafy.sungchef.commons.DUPLICATE_CONFIRM
import com.ssafy.sungchef.commons.INPUT_BIRTH
import com.ssafy.sungchef.commons.INPUT_NICKNAME
import com.ssafy.sungchef.commons.NEXT_STEP
import com.ssafy.sungchef.features.component.DatePickerDialogComponent
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.IconButtonComponent
import com.ssafy.sungchef.features.component.IconComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.component.TextFieldComponent
import com.ssafy.sungchef.features.component.TopAppBarComponent
import com.ssafy.sungchef.features.screen.signup.common.SignupNickname
import com.ssafy.sungchef.features.screen.signup.common.SignupTopBar
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Locale

private const val TAG = "SignupBirthScreen_성식당"
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignupBirthScreen(
    viewModel : SignupViewModel,
    onMoveNextPage : () -> Unit,
    onMovePreviousPage : () -> Unit
) {
    val context = LocalContext.current

    Scaffold (
        topBar = {
            SignupTopBar(
                viewModel.topBarNumber.intValue,
                viewModel,
                onMovePreviousPage
            )
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

                    SignupBirth(viewModel = viewModel)

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
                    if (viewModel.checkBirth()) {
                        onMoveNextPage()
                        viewModel.moveNextPage()
                    } else {
                        Toast.makeText(context, INPUT_BIRTH, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignupBirth(
    enable : Boolean = true,
    isClickable: Boolean = true,
    disabledBorderColor : Color = MaterialTheme.colorScheme.primary,
    viewModel : SignupViewModel
){
    var birth by remember { viewModel.birth }

    val date = remember { mutableStateOf(LocalDate.now())}
    val isOpen = remember { mutableStateOf(false) }

    val context = LocalContext.current // 현재 액티비티의 context를 나타냄

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
            IconButtonComponent(
                size = 40,
                onClick = {
                    if (isClickable) isOpen.value = true
                },
                painter = painterResource(id = R.drawable.icon_calendar),
                enabled = enable,
            )
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
            onCancel = { isFuture ->
                if (isFuture){
                    Toast.makeText(context, BIRTH_FAIL_MESSAGE, Toast.LENGTH_SHORT).show()
                }
                isOpen.value = false
            }
        )
    }
}
