package com.ssafy.sungchef.features.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.sungchef.R
import com.ssafy.sungchef.commons.DELETE_ALL

@Composable
fun IngredientSelectComponent(
    modifier: Modifier = Modifier,
    name: String = "",
    onDelete: () -> (Unit)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .background(color = Color.White),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            modifier = modifier
                .weight(1f),
            text = name,
            fontSize = 16.sp
        )
        Button(
            onClick = onDelete,
            modifier = modifier.padding(end = 10.dp),
            content = {
                TextComponent(
                    modifier = modifier,
                    text = "삭제",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        )
    }
}