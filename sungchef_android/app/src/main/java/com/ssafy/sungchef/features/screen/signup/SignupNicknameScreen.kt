package com.ssafy.sungchef.features.screen.signup

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.sungchef.commons.ALREADY_NICKNAME
import com.ssafy.sungchef.commons.DUPLICATE_CONFIRM
import com.ssafy.sungchef.commons.EMPTY_NICKNAME
import com.ssafy.sungchef.commons.INPUT_NICKNAME
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.features.component.AlertDialogComponent
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.screen.signup.common.SignupNickname
import com.ssafy.sungchef.features.screen.signup.common.SignupTopBar

private const val TAG = "SignupNicknameScreen_성식당"
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignupScreen(
    viewModel: SignupViewModel,
    onMoveNextPage : () -> Unit,
    onMovePreviousPage : () -> Unit
) {
    val context = LocalContext.current

    nicknameState(viewModel = viewModel, context, onMoveNextPage)

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
                    if (!viewModel.checkNickname()){
                        if (viewModel.nickname.value.isNotEmpty()) {
                            viewModel.isDuplicateNickname(viewModel.nickname.value)
                        } else {
                            Toast.makeText(context, EMPTY_NICKNAME, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun nicknameState(
    viewModel : SignupViewModel,
    context : Context,
    onMoveNextPage : () -> Unit,
) {
    val uiState: BaseModel by viewModel.isDuplicateName.collectAsState()
    val isError : Boolean by viewModel.isError.collectAsState()
    val isNextPage : Boolean by viewModel.isNextPage.collectAsState()

    val showDialog = remember { mutableStateOf(false) }

    if (isError) {
        showDialog.value = true

        if (showDialog.value){
            AlertDialog(
                title = {
                    TextComponent(
                        text = ALREADY_NICKNAME
                    )
                },
                onDismissRequest = {},
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog.value = false
                            // Error state 초기화
                            viewModel.initIsErrorState(false)
                        }
                    ) {
                        TextComponent(text = "확인")
                    }
                }
            )
        }
    } else {
        if (isNextPage) {
            LaunchedEffect(Unit) {
                Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
                onMoveNextPage()
                viewModel.moveNextPage()

                // 화면 전환하고 state를 다시 false로 초기화
                viewModel.initIsNextPageState(false)
            }
        }
    }
}

