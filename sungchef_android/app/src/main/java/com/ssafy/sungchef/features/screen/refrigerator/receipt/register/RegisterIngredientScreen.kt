package com.ssafy.sungchef.features.screen.refrigerator.receipt.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.sungchef.commons.ADD_INGREDIENT
import com.ssafy.sungchef.features.component.TextComponent

@Composable
fun RegisterIngredientScreen(

) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 60.dp)
        ) {
            TextComponent(
                text = ADD_INGREDIENT,
                style = MaterialTheme.typography.titleLarge
            )


        }
    }
}

@Preview
@Composable
fun RegisterIngredientPreview() {
    RegisterIngredientScreen()
}
