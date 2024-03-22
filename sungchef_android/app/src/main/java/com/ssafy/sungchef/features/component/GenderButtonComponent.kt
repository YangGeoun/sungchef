package com.ssafy.sungchef.features.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.sungchef.R

@Composable
fun GenderButtonComponent(
    modifier : Modifier = Modifier,
    gender : String,
    genderResource : Int,
    shape : RoundedCornerShape = RoundedCornerShape(10),
    isSelected : Boolean = false,
    onClick : () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = toggleColor(isSelected),
                shape = shape
            )
            .clickable {
                onClick()
            }
    ){
        TextComponent(
            text = gender,
            fontSize = 28.sp,
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp)
                .align(Alignment.TopStart)
        )
        ImageComponent(
            modifier = Modifier
                .width(100.dp)
                .height(112.dp)
                .align(Alignment.BottomEnd),
            imageResource = genderResource
        )
    }
}

@Composable
fun toggleColor(isSelected : Boolean) : Color {
    return if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.primaryContainer
    }
}

@Preview(showBackground = true)
@Composable
private fun BodyPreview() {
    GenderButtonComponent(
        Modifier.fillMaxSize(),
        "남자",
        R.drawable.gender_man,
        onClick = {

        }
    )
}