package com.ssafy.sungchef.features.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun AlertDialogComponent(
    modifier : Modifier = Modifier,
    dialogTitle : String = "",
    dialogText : String,
    onDismissRequest : () -> Unit,
    showDialog : (Boolean) -> Unit
) {
   AlertDialog(
       modifier = modifier,
       title = {
           TextComponent(
               text = dialogTitle
           )
       },
       text = {
           TextComponent(
               text = dialogText
           )
       },
       onDismissRequest = onDismissRequest,
       confirmButton = {
            TextButton(
                onClick = {
                    showDialog(false)
                }
            ) {
                TextComponent(text = "확인")
            }
       }
   )
}