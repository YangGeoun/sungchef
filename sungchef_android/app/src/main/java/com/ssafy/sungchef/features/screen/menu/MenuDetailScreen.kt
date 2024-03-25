package com.ssafy.sungchef.features.screen.menu

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.sungchef.R
import com.ssafy.sungchef.features.component.IconTextRowComponent
import com.ssafy.sungchef.features.component.TextComponent

@Composable
fun MenuDetailScreen(
    recipeId:String = ""
) {
    Log.d("TAG", "MenuDetailScreen: $recipeId")
    // Todo 서버 통신
    Scaffold {
        Content(it)
    }
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(paddingValues)
    ) {
        // Todo 이미지
        ImageComponent(
            modifier
                .fillMaxWidth()
                .aspectRatio(1.5f))
        // Todo 제목, 인분, 시간, 정보
        MenuInfoCard(modifier = modifier)
        TextComponent(text = "재료")
        TextComponent(text = "레시피")
    }
}

@Composable
fun ImageComponent(
    modifier: Modifier
){
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.test_image),
        contentScale = ContentScale.FillBounds,
        contentDescription = "음식 사진"
    )
}

@Composable
fun MenuInfoCard(
    modifier: Modifier
) {
    Card(
        modifier = modifier.padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        TextComponent(
            text = "국가 권력급 김치찌개",
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .padding(horizontal = 10.dp),
            fontSize = 22.sp
        )
        TextComponent(
            text = "하나의 김치찌개가 국가를 지.배.한.다.",
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .padding(horizontal = 10.dp),
            fontSize = 16.sp,
            color = Color.DarkGray
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp)
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconTextRowComponent(
                painter = painterResource(id = R.drawable.groups),
                text = "2인분"
            )
            IconTextRowComponent(
                painter = painterResource(id = R.drawable.timer),
                text = "15분 이내"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuInfoPreview() {
    Content(paddingValues = PaddingValues(20.dp),modifier = Modifier)
}