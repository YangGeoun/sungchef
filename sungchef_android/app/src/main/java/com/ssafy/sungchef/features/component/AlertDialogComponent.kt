package com.ssafy.sungchef.features.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun AlertDialogComponent(
    modifier : Modifier = Modifier,
    dialogTitle : String = "",
    dialogText : String,
    onDismissRequest : () -> Unit,
    showDialog : (Boolean) -> Unit,
    isNextPage : (Boolean) -> Unit = {},
    initUiState : () -> Unit = {}
) {
   AlertDialog(
       modifier = modifier,
       title = {
           TextComponent(
               text = dialogTitle,
               style = MaterialTheme.typography.titleLarge
           )
       },
       text = {
           TextComponent(
               text = dialogText,
               style = MaterialTheme.typography.bodyLarge
           )
       },
       onDismissRequest = onDismissRequest,
       confirmButton = {
            TextButton(
                onClick = {
                    isNextPage(true)
                    showDialog(false)
                }
            ) {
                TextComponent(text = "확인")
            }
       },
       dismissButton = {
           TextButton(
               onClick = {
                   initUiState()
                   showDialog(false)
               }
           ) {
               TextComponent(text = "취소")
           }
       }
   )
}