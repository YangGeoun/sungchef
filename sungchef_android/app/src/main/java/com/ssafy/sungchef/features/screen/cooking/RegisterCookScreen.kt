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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
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
import com.ssafy.sungchef.features.component.FilledButtonComponent
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
    viewModel: CookingViewModel,
    navigateHome: () -> (Unit)
) {
    val uiState = viewModel.uiState.collectAsState().value

    if (uiState.isNavigateToHome){
        navigateHome()
    }
    
    Scaffold(
        topBar = { TopAppBarComponent(title = { TextComponent(text = "내가 만든 음식", style = MaterialTheme.typography.titleLarge) }) }
    ) { paddingValues ->
        Content(
            paddingValues = paddingValues,
            setBitmap = {
                        viewModel.setBitmap(it)
            },
            setFile = {
                viewModel.setFile(it)
            }
        ) {
            viewModel.registerCooking(uiState.usedIngredient!!.recipeId, it)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
private fun Content(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    setFile: (File) -> (Unit),
    setBitmap: (Bitmap) -> (Unit),
    registerCooking: (String) -> (Unit)
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
                bitmap = setImgUri(cameraImageUri!!, context)
                setBitmap(bitmap!!)
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
        setFile(file)
        // Manifest의 authority와 FileProvider.getUriForFile의 authority를 통일 시켜야함
        FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }
    Column(
        modifier = modifier
            .padding(paddingValues)
            .padding(top = 10.dp)
            .padding(horizontal = 20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
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
                },
            contentAlignment = Alignment.Center
        ) {
            if (bitmap == null) {
                LottieAnimationComponent(id = R.raw.photo_animation)
            } else {
                Image(
                    modifier = modifier.fillMaxSize(),
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = "dlalsl"
                )
            }
        }
        TextFieldComponent(value = text, onValueChange = { text = it }, hintText = "한 줄 평을 입력하세요.")
        FilledButtonComponent(text = "등록") {
            registerCooking(text)
        }
    }
}

@Composable
fun LottieAnimationComponent(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    id: Int,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(id)
    )
    val progress by animateLottieCompositionAsState(
        composition, true, iterations = LottieConstants.IterateForever, restartOnPlay = false
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier,
        contentScale = contentScale,
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
