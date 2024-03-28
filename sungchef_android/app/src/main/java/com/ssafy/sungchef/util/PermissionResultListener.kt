package com.ssafy.sungchef.util

interface PermissionResultListener {
    fun onPermissionGranted()
    fun onPermissionDenied()
}