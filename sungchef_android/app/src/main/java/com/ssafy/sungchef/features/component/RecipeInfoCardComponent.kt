package com.ssafy.sungchef.features.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.sungchef.R

@Composable
fun RecipeInfoCardComponent(
    modifier: Modifier,
    title: String,
    description: String,
    volume: String,
    time: String
) {
    Card(
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        TextComponent(
            text = title,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .padding(horizontal = 10.dp),
            fontSize = 22.sp
        )
        TextComponent(
            text = description,
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
                text = volume
            )
            IconTextRowComponent(
                painter = painterResource(id = R.drawable.timer),
                text = time
            )
        }
    }
}