package com.ssafy.sungchef.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

/**
 * 권한 설정을 관리하는 클래스
 */
class PermissionChecker(private val context : Context) {

    private var permissionResultListener: PermissionResultListener? = null

    fun setPermissionResultListener(listener: PermissionResultListener) {
        this.permissionResultListener = listener
    }

    private val cameraPermissionRequest = (context as ComponentActivity).registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // 권한이 부여됨
            permissionResultListener?.onPermissionGranted()
        } else {
            // 권한 거부됨
            permissionResultListener?.onPermissionDenied()
        }
    }

    fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // 권한이 이미 있음
                permissionResultListener?.onPermissionGranted()
            }
            else -> {
                // 권한 요청
                cameraPermissionRequest.launch(Manifest.permission.CAMERA)
            }
        }
    }
}