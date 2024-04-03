package com.ssafy.sungchef.features.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.sungchef.R

@Composable
fun MenuCardComponent(
    modifier: Modifier,
    imageResource: Any,
    title: String,
    views: String,
    bookmarks:String,
    servings: String,
    timer: String,
    painter: Painter = painterResource(id = R.drawable.bookmark),
    bookmark: Boolean,
    color: Color = Color.Black,
    onClick: () -> (Unit),
    onBookMarkClick: (Boolean) -> (Unit),
) {
    var bookMarkState by rememberSaveable { mutableStateOf(bookmark) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = modifier
                .height(120.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            ImageComponent(
                modifier = modifier.size(120.dp),
                imageResource = imageResource,
                contentDescription = "음식 사진"
            )
            Spacer(modifier = modifier.size(10.dp))
            Column(
                modifier = modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextComponent(
                        modifier = modifier.weight(1f),
                        text = title,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (bookMarkState) {
                        Icon(
                            painter = painterResource(id = R.drawable.filled_bookmark),
                            contentDescription = "즐겨찾기",
                            modifier = modifier
                                .padding(end = 10.dp)
                                .clickable {
                                    bookMarkState = !bookMarkState
                                    onBookMarkClick(bookMarkState)
                                },
                            tint = Color(0xFF9FD18D)
                        )
                    } else {
                        Icon(
                            painter = painter,
                            contentDescription = "즐겨찾기",
                            modifier = modifier
                                .padding(end = 10.dp)
                                .clickable {
                                    bookMarkState = !bookMarkState
                                    onBookMarkClick(bookMarkState)
                                },
                            tint = Color(0xFF9FD18D)
                        )
                    }
                }
                Row {
                    IconTextRowComponent(
                        modifier = modifier.weight(1f),
                        painter = painterResource(id = R.drawable.visibility),
                        text = views
                    )
                    IconTextRowComponent(
                        modifier = modifier.weight(1f),
                        painter = painterResource(id = R.drawable.filled_bookmark),
                        text = bookmarks
                    )
                }
                Row {
                    IconTextRowComponent(
                        modifier = modifier.weight(1f),
                        painter = painterResource(id = R.drawable.groups),
                        text = servings
                    )
                    IconTextRowComponent(
                        modifier = modifier.weight(1f),
                        painter = painterResource(id = R.drawable.timer),
                        text = timer
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuCardComponentPreview() {
    MenuCardComponent(
        modifier = Modifier,
        imageResource = painterResource(id = R.drawable.test_image),
        title = "돼지 김치찌개",
        views = "9999",
        bookmarks = "9999",
        servings = "2인분",
        timer = "15분 이내",
        painter = painterResource(id = R.drawable.filled_bookmark),
        bookmark = true,
        color = Color.Red,
        onClick = {}
    ) {

    }
}