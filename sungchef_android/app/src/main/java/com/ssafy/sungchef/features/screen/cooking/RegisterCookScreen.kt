package com.ssafy.sungchef.features.screen.cooking

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssafy.sungchef.R
import com.ssafy.sungchef.features.component.ImageComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.component.TextFieldComponent
import com.ssafy.sungchef.features.component.TopAppBarComponent
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun RegisterCookScreen(
    id: Int
) {
    Scaffold(
        topBar = { TopAppBarComponent(title = { TextComponent(text = "국가 권력급 김치찌개")}) }
    ) {
        Content(paddingValues = it)
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
private fun Content(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {
    val context = LocalContext.current
    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    var text by remember { mutableStateOf("") }
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                // 사진 촬영 성공 후 처리 로직
                // TODO 서버에 사진을 보내는 로직 필요
                bitmap = setImgUri(cameraImageUri!!, context)
            }
        }
    )
    val createImageUri: () -> Uri? = {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
        // Manifest의 authority와 FileProvider.getUriForFile의 authority를 통일 시켜야함
        FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }
    Column(
        modifier = modifier
            .padding(paddingValues)
            .padding(horizontal = 20.dp)
            .fillMaxSize()
    ) {
        Box(
            modifier = modifier
                .fillMaxHeight(0.3f)
                .fillMaxWidth()
                .clickable {
                    cameraImageUri = createImageUri().also {
                        it?.let { uri ->
                            takePictureLauncher.launch(uri)
                        }
                    }
                }
        ) {
            if (bitmap == null) {
                LottieAnimationComponent()
            } else {
                Image(bitmap = bitmap!!.asImageBitmap(), contentDescription = "dlalsl")
            }
        }
        TextFieldComponent(value = text, onValueChange = { text = it}, hintText = "한 줄 평을 입력하세요.")
    }
}

@Composable
fun LottieAnimationComponent(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.photo_animation)
    )
    val progress by animateLottieCompositionAsState(
        composition, true, iterations = LottieConstants.IterateForever, restartOnPlay = false
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier
    )
}

fun setImgUri(imgUri: Uri, context: Context): Bitmap {
    imgUri.let {
        val bitmap: Bitmap
        if (Build.VERSION.SDK_INT < 28) {
            bitmap = MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                imgUri
            )
        } else {
            val source =
                ImageDecoder.createSource(context.contentResolver, imgUri)
            bitmap = ImageDecoder.decodeBitmap(source)
        }
        return bitmap
    }
}
