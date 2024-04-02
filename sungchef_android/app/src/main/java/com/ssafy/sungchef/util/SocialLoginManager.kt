package com.ssafy.sungchef.util

import android.util.Log
import com.kakao.sdk.user.UserApiClient

private const val TAG = "SocialLoginManager_성식당"
class SocialLoginManager {

    fun kakaoLogout() {
        // 연결 끊기
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e(TAG, "연결 끊기 실패", error)
            }
            else {
                Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }

}