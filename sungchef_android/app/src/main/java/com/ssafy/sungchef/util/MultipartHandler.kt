package com.ssafy.sungchef.util

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class MultipartHandler {

    /**
     * Uri를 휴대폰 FilePath로 변환하는 함수
     */
    fun getFilePathUri(activity : Activity, uri : Uri) : String {
        val buildName = Build.MANUFACTURER

        // 샤오미 폰은 바로 경로 반환 가능
        if (buildName.equals("Xiaomi")) {
            return uri.path.toString()
        }

        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        var cursor = activity.contentResolver.query(uri, proj, null, null, null)

        if (cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }

        return cursor.getString(columnIndex)
    }

    /**
     * param
     * 1. filePath는 위 getFilePathUri에서 Uri를 filePath로 변환할 수 있습니다.
     * 2. imageKey는 서버와 맞춰야 합니다.
     */
    fun convertMultiPart(filePath : String, imageKey : String) : MultipartBody.Part {
        val file = File(filePath)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(imageKey, file.name, requestFile)
    }

    fun <T> createRequestBody(requestDTO : T) : RequestBody {
        val gson = Gson()
        val productJson = gson.toJson(requestDTO)
        return productJson.toRequestBody("application/json".toMediaTypeOrNull())
    }
}