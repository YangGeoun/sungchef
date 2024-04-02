package com.ssafy.sungchef.features.screen.refrigerator.receipt.start

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.FileProvider
import com.ssafy.sungchef.commons.REGISTER_INGREDIENT
import com.ssafy.sungchef.commons.START_RECEIPT_TITLE
import com.ssafy.sungchef.features.component.AlertDialogComponent
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.PrimaryContainerButtonComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.screen.login.ShowLoadingDialog
import com.ssafy.sungchef.features.ui.theme.dialogBackgroundColor
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.activity.compose.rememberLauncherForActivityResult as rememberLauncherForActivityResult1


private const val TAG = "StartReceiptScreen_성식당"
@Composable
fun StartReceiptScreen(
    viewModel : StartReceiptViewModel,
    onMoveRegisterReceiptPage : (String) -> Unit,
    onMoveRegisterIngredientPage : () -> Unit,
    onBackNavigate : () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    ShowLoadingDialog(isLoading = uiState.isLoading)

    when (uiState.code) {
        200 -> {
            onMoveRegisterReceiptPage(uiState.imageUrl)
        }
        else -> {
            ShowErrorDialog(
                showDialog = showDialog,
                dialogText = uiState.dialogTitle,
                onCancel = {
                    showDialog = false
                }
            )
        }
    }

    BackHandler {
        onBackNavigate()
    }

    var isShowDialog by remember { mutableStateOf(false) }

    ShowDialog(
        viewModel = viewModel,
        isShowDialog = isShowDialog,
        onMoveRegisterReceiptPage
    ) {
        isShowDialog = false
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
        ){
            Spacer(
                modifier = Modifier
                    .padding(top = 100.dp)
            )

            TextComponent(
                text = START_RECEIPT_TITLE,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(
                modifier = Modifier
                    .padding(top = 10.dp)
            )

            TextComponent(
                text = REGISTER_INGREDIENT,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(
            modifier = Modifier
                .weight(0.3f)
        )

        Column(
            modifier = Modifier
                .padding(start = 60.dp, end = 60.dp)
                .weight(1f)
        ){
            PrimaryContainerButtonComponent(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                ),
                text = "영수증으로 등록하기",
                shape = RoundedCornerShape(15.dp)
            ) {
                isShowDialog = true
            }

            Spacer(
                modifier = Modifier
                    .padding(top = 30.dp)
            )

            FilledButtonComponent(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "직접 등록하기",
                shape = RoundedCornerShape(15.dp)
            ) {
                onMoveRegisterIngredientPage()
            }
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Composable
fun ShowDialog(
    viewModel : StartReceiptViewModel,
    isShowDialog : Boolean,
    onMoveRegisterReceiptPage : (String) -> Unit,
    onCancel : () -> Unit
) {
    if (isShowDialog){
        Dialog(
            onDismissRequest = {
                onCancel()
            }
        ) {
            DialogContext(
                viewModel,
                onCancel,
                onMoveRegisterReceiptPage
            )

        }
    }
}

@Composable
fun DialogContext(
    viewModel : StartReceiptViewModel,
    onCancel: () -> Unit,
    onMoveRegisterReceiptPage : (String) -> Unit
) {
    val context = LocalContext.current
    val activity = context.findActivity()

    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }

    // 이미지 파일을 저장할 URI 생성
    val createImageUri: () -> Uri? = {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
        // Manifest의 authority와 FileProvider.getUriForFile의 authority를 통일 시켜야함
        FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    // 카메라로 사진 촬영
    // 카메라 촬영 결과를 파일로 저장하기 위한 URI 생성 필요
    val takePictureLauncher = rememberLauncherForActivityResult1(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                // 사진 촬영 성공 후 처리 로직
                // TODO 서버에 사진을 보내는 로직 필요
                onCancel()
//                onMoveRegisterReceiptPage() // 영수증 등록화면으로 이동
                Log.d(TAG, "cameraImageUri: $cameraImageUri")
            }
        }
    )

    // 앨범에서 이미지 선택
    val pickImageLauncher = rememberLauncherForActivityResult1(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        Log.d(TAG, "uri: $uri")
        uri?.let {
            onCancel()
            // 서버에 사진을 보냅니다.
            viewModel.registerReceipt(it, context)
//            onMoveRegisterReceiptPage() // 영수증 등록화면으로 이동
        }
    }

    Column(
        modifier = Modifier
            .width(350.dp)
            .height(210.dp)
            .background(
                color = dialogBackgroundColor,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(start = 20.dp, end = 20.dp)
            ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PrimaryContainerButtonComponent(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White,
            ),
            text = "영수증 촬영하기",
            shape = RoundedCornerShape(15.dp)
        ) {
            // 카메라 실행
            cameraImageUri = createImageUri().also {
                it?.let { uri ->
                    takePictureLauncher.launch(uri)
                }
            }
        }

        Spacer(
            modifier = Modifier
                .padding(top = 20.dp)
        )

        FilledButtonComponent(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White,
            ),
            text = "앨범에서 가져오기",
            shape = RoundedCornerShape(15.dp)
        ) {
            // TODO 앨범에서 결과를 가져오는 기능 구현
            pickImageLauncher.launch("image/*")
        }
    }
}

@Composable
fun ShowErrorDialog(
    showDialog : Boolean,
    dialogText : String,
    onCancel: () -> Unit
) {
    if (showDialog){
        AlertDialogComponent(
            dialogText = dialogText,
            onDismissRequest = {
                onCancel()
            },
            showDialog = {
                onCancel()
            }
        )
    }

}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

