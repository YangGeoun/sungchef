package com.ssafy.sungchef.features.screen.refrigerator.receipt.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.sungchef.R
import com.ssafy.sungchef.commons.MOVE_HOME_SCREEN
import com.ssafy.sungchef.commons.NEED_INGREDIENT_CONVERT
import com.ssafy.sungchef.commons.REGISTER_INGREDIENT_BUTTON
import com.ssafy.sungchef.commons.REGISTER_RECEIPT_TITLE
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.IconComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.screen.refrigerator.RefridgeArray
@Composable
fun RegisterReceiptScreen(
    
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) { 
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 80.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .padding(top = 50.dp)
            )

            TextComponent(
                text = REGISTER_RECEIPT_TITLE,
                style = MaterialTheme.typography.titleLarge
            )

            TextComponent(
                text = NEED_INGREDIENT_CONVERT,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier
                    .padding(top = 10.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(bottom = 80.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 45.dp,
                            vertical = 25.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    TextComponent(
                        text = "영수증"
                    )

                    IconComponent(
                        painter = painterResource(id = R.drawable.icon_arrow)
                    )

                    TextComponent(
                        text = "수정 결과"
                    )
                }
            }
        }
        FilledButtonComponent(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            text = REGISTER_INGREDIENT_BUTTON
        ) {

        }
    }
}

@Preview
@Composable
fun RegisterReceiptPreview() {
    RegisterReceiptScreen()
}