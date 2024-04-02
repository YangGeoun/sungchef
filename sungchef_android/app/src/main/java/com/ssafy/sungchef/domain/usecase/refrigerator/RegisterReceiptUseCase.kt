package com.ssafy.sungchef.domain.usecase.refrigerator

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.refrigerator.RegisterReceiptState
import com.ssafy.sungchef.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

private const val TAG = "RegisterReceiptUseCase_성식당"
class RegisterReceiptUseCase @Inject constructor(
    private val refrigeratorRepository: RefrigeratorRepository
) {
    // TODO 여기부터!!
    suspend fun registerReceipt(imageUri : Uri, context : Context) : Flow<DataState<RegisterReceiptState>> {
        // uri를 bitmap으로 바꾸기
        val imageBitmap = getBitmapFromUri(context, imageUri)

        // bitmap을 .jpg 파일로 바꾸기
        val imageFile = imageBitmap?.let { bitmap ->
            saveBitmapToFile(bitmap, context)
        }
        Log.d(TAG, "imageFile: ${imageFile.toString()}")
        return refrigeratorRepository.registerReceipt(imageFile!!)
    }

    // URI로부터 Bitmap 읽기
    fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // Android P 이상에서 ImageDecoder 사용
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            // Android P 미만에서는 이전 방식 사용
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    }

    // Bitmap을 내부 저장소에 파일로 저장하기
    fun saveBitmapToFile(bitmap: Bitmap, context: Context): File {
        // 내부 저장소의 캐시 디렉터리를 사용합니다.

        val timestamp = System.currentTimeMillis()  // 중복을 피하기 위해 현재 시간을 넣어줌
        val receiptFileName = "receipt_$timestamp.jpg"

        val directory = context.cacheDir
        val file = File(directory, receiptFileName)

        var outputStream: OutputStream? = null
        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        } finally {
            outputStream?.close()
        }
        return file
    }
}