package com.ssafy.sungchef.features.screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.sungchef.R
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.ImageComponent
import com.ssafy.sungchef.features.component.TextFieldComponent
import com.ssafy.sungchef.features.component.LazyVerticalGridComponent
import com.ssafy.sungchef.features.component.OutlinedButtonComponent
import com.ssafy.sungchef.features.component.SmallTextButtonComponent
import com.ssafy.sungchef.features.ui.theme.SungchefTheme
import dagger.hilt.android.AndroidEntryPoint

val TAG = "MainActivity";
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SungchefTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

//    ImageComponent(
//        modifier = Modifier.
//            size(120.dp)
//            .clip(CircleShape),
//            imageResource = R.drawable.test_image,
//    )
//    SmallClickTextComponent(
//        text = "로그아웃",
//        color = MaterialTheme.colorScheme.primary,
//        onClick={ Log.d(TAG, "Greeting: 로그아웃 클릭")})

    OutlinedButtonComponent(
        text = "outlined 버튼입니다.",
        shape = RoundedCornerShape(15),
        borderColor = MaterialTheme.colorScheme.primary,
        onClick = { Log.d("TAG", "OutlinedButtonComponentPreview: ")}
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SungchefTheme {
        Greeting("Android")
    }
}