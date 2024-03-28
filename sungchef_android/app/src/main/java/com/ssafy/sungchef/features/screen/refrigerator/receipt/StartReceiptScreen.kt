package com.ssafy.sungchef.features.screen.refrigerator.receipt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.sungchef.commons.START_RECEIPT_TITLE
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.screen.signup.SignupCongratulationScreen

@Composable
fun StartReceiptScreen(

) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

       Spacer(
           modifier = Modifier
               .padding(top = 100.dp)
       )

       TextComponent(
           text = START_RECEIPT_TITLE,
           style = MaterialTheme.typography.headlineMedium
       )
    }
}

@Preview(showBackground = true)
@Composable
fun StartReceiptPreview() {
    StartReceiptScreen(

    )
}