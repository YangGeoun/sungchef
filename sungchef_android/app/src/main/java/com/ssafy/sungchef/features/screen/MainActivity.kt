package com.ssafy.sungchef.features.screen

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.ssafy.sungchef.commons.CAMERA_PERMISSON_DENIED
import com.ssafy.sungchef.commons.CAMERA_PERMISSON_GRANTED
import com.ssafy.sungchef.domain.model.survey.SurveyInfo
import com.ssafy.sungchef.features.navigation.NavGraph
import com.ssafy.sungchef.features.ui.theme.SungchefTheme
import com.ssafy.sungchef.util.PermissionChecker
import com.ssafy.sungchef.util.PermissionResultListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


private const val TAG = "MainActivity_성식당"
@AndroidEntryPoint
class MainActivity : ComponentActivity(), PermissionResultListener {

    private val viewModel : MainViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionChecker = PermissionChecker(this).also {
            it.setPermissionResultListener(this)
        }

        permissionChecker.requestCameraPermission()
//        naverUnlink()

        // 자동 로그인 상태 기다리기
        lifecycleScope.launch {
            val isSuccessAutoLogin = viewModel.isSuccessAutoLogin.first { it != null }
            val startDestination = if (isSuccessAutoLogin == true) {
                "home_screen"
            } else {
                "login_screen"
            }
            // UI 설정
            setContent {
                SungchefTheme {
                    Surface(color = MaterialTheme.colorScheme.background) {
                        // 상태에 따라 동적으로 NavGraph 설정
                        NavGraph(startDestination = startDestination) {
                            requestedOrientation = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                            } else {
                                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onPermissionGranted() {

    }

    override fun onPermissionDenied() {
        Toast.makeText(this, CAMERA_PERMISSON_DENIED, Toast.LENGTH_SHORT).show()
        finish()
    }

    // 네이버 로그아웃
    private fun naverLogout(){
        NaverIdLoginSDK.logout()
    }

    // 네이버 연결끊기
    private fun naverUnlink(){
        NidOAuthLogin().callDeleteTokenApi(object : OAuthLoginCallback {
            override fun onSuccess() {
                //서버에서 토큰 삭제에 성공한 상태입니다.
            }
            override fun onFailure(httpStatus: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                Log.d("naver", "errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}")
                Log.d("naver", "errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}")
            }
            override fun onError(errorCode: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                onFailure(errorCode, message)
            }
        })
    }
}
