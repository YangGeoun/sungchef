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
import androidx.datastore.preferences.preferencesDataStore
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.ssafy.sungchef.commons.CAMERA_PERMISSON_DENIED
import com.ssafy.sungchef.commons.CAMERA_PERMISSON_GRANTED
import com.ssafy.sungchef.features.navigation.NavGraph
import com.ssafy.sungchef.features.ui.theme.SungchefTheme
import com.ssafy.sungchef.util.PermissionChecker
import com.ssafy.sungchef.util.PermissionResultListener
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "MainActivity_성식당"
@AndroidEntryPoint
class MainActivity : ComponentActivity(), PermissionResultListener {

    private val viewModel : MainViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionChecker = PermissionChecker(this).also {
            it.setPermissionResultListener(this)
        }

        permissionChecker.requestCameraPermission()
//        naverUnlink()

        // TODO 자동 로그인 로직 필요
        setContent {
            SungchefTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(){
                        requestedOrientation = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        }else {
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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
