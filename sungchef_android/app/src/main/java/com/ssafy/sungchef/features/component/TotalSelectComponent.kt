package com.ssafy.sungchef.features.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.sungchef.commons.DELETE_ALL

@Composable
fun TotalSelectComponent(
    selected : Boolean,
    totalCount : Int,
    onSelect : () -> Unit = {}

) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(color = Color.White),
        verticalAlignment = Alignment.CenterVertically
    ){
        RadioButton(
            selected = selected,
            onClick = {
                onSelect()
            }
        )
        TextComponent(
            modifier = Modifier
                .weight(1f),
            text = "전체 ${totalCount}개"
        )

        TextComponent(
            text = DELETE_ALL,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BodyPreview() {
    TotalSelectComponent(
        true,
        3
    )
}