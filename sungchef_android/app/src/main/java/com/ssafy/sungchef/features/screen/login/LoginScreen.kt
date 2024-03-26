package com.ssafy.sungchef.features.screen.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.sungchef.R
import com.ssafy.sungchef.commons.KAKAO
import com.ssafy.sungchef.data.model.requestdto.UserSnsIdRequestDTO
import com.ssafy.sungchef.data.repository.UserDataStoreRepositoryImpl
import com.ssafy.sungchef.domain.model.user.LoginState
import com.ssafy.sungchef.features.component.AlertDialogComponent
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.ImageComponent
import com.ssafy.sungchef.features.component.LoginImageComponent
import com.ssafy.sungchef.features.screen.survey.SurveyScreen
import kotlinx.coroutines.flow.toCollection


private const val TAG = "LoginScreen_성식당"
@Composable
fun LoginScreen(
    viewModel : LoginViewModel,
    onMoveSignupPage : () -> Unit,
    onMoveSurveyPage : () -> Unit,
    onMoveHomePage : () -> Unit
) {
    val context = LocalContext.current
    val loginState : LoginState by viewModel.loginState.collectAsState()

    var isNextPage by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Log.d(TAG, "LoginScreen: $loginState")

    movePage(
        loginState,
        onMoveSignupPage,
        onMoveSurveyPage,
        onMoveHomePage,
        showDialog = {
            showDialog = it
        },
        isNextPage = isNextPage,
        initLoginState = {
            viewModel.initLoginStateCode()
        }
    )

    showMovePageDialog(
        showDialog = showDialog,
        onCancel = {
            Log.d(TAG, "LoginScreen: $showDialog")
           // 다이얼로그 닫기
           showDialog = false
            Log.d(TAG, "LoginScreen2: $showDialog")
        },
        loginState = loginState,
        isNextPage = {
            isNextPage = it
        },
        initLoginState = {
            viewModel.initLoginStateCode()
        }
    )

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
                .padding(top = 40.dp)
        )

        LoginImageComponent(
            modifier = Modifier
                .width(300.dp)
                .height(60.dp)
                .clickable {
                    kakaoLogin(context, viewModel)
                },
            imageResource = R.drawable.kakao_login,
        )

        Spacer(
            modifier = Modifier
                .padding(top = 20.dp)
        )

        LoginImageComponent(
            modifier = Modifier
                .width(300.dp)
                .height(60.dp),
            imageResource = R.drawable.naver_login
        )
    }
}

fun kakaoLogin(
    context : Context,
    viewModel : LoginViewModel
){

    // 카카오계정으로 로그인 공통 callback 구성
    // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            getKakaoUserInfo(viewModel)
        }
    }

    // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오톡으로 로그인 실패", error)

                // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }

                // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            } else if (token != null) {
                Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                getKakaoUserInfo(viewModel)
            }
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
    }
}

// 카카오 로그인에 성공한 유저의 정보 받기
fun getKakaoUserInfo(viewModel : LoginViewModel) {
    // 사용자 정보 요청 (기본)
    UserApiClient.instance.me { user, error ->
        if (error != null) {
            Log.e(TAG, "사용자 정보 요청 실패", error)
        }
        else if (user != null) {
            Log.i(TAG, "사용자 정보 요청 성공" +
                    "\n회원번호: ${user.id}" +
                    "\n이메일: ${user.kakaoAccount?.email}" +
                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                    "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
            val userSnsIdRequestDto =  UserSnsIdRequestDTO(user.id.toString())
            Log.d(TAG, "getKakaoUserInfo: 정보를 받습니다.")
            viewModel.login(userSnsIdRequestDto, KAKAO)
        }
    }
}

fun movePage(
    loginState : LoginState,
    onMoveSignupPage : () -> Unit,
    onMoveSurveyPage : () -> Unit,
    onMoveHomePage : () -> Unit,
    showDialog : (Boolean) -> Unit,
    isNextPage: Boolean,
    initLoginState : () -> Unit
) {
    when (loginState.code) {
        200 -> {
            onMoveHomePage()
        }
        400 -> {
            showDialog(true)
        }
        403 -> {
            showDialog(true)
            if (isNextPage){
                onMoveSurveyPage()
                showDialog(false)
                initLoginState()
            }
        }
        404 -> {
            showDialog(true)
            if (isNextPage) {
                onMoveSignupPage()
                showDialog(false)
                initLoginState()
            }
        }
        500 -> {
            showDialog(true)
        }
    }
}

@Composable
fun showMovePageDialog(
    showDialog : Boolean,
    onCancel : (Boolean) -> Unit,
    loginState : LoginState,
    isNextPage : (Boolean) -> Unit,
    initLoginState: () -> Unit,
) {
    if (showDialog){
        AlertDialogComponent(
            dialogText = loginState.message,
            onDismissRequest = {
                onCancel(false)
            },
            showDialog = {
                onCancel(false)
            },
            isNextPage = isNextPage,
            initUiState = initLoginState
        )
    }

}

@Preview(showBackground = true)
@Composable
fun LoginBodyPreview() {
    LoginScreen(
        viewModel = hiltViewModel(),
        {},
        {},
        {}
    )
}