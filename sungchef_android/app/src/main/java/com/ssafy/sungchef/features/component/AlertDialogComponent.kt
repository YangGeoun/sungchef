package com.ssafy.sungchef.features.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


private const val TAG = "AlertDialogComponent_성식당"
@Composable
fun AlertDialogComponent(
    modifier : Modifier = Modifier,
    dialogTitle : String = "",
    dialogText : String,
    onDismissRequest : () -> Unit,
    showDialog : (Boolean) -> Unit,
    isNextPage : (Boolean) -> Unit = {},
    initUiState : () -> Unit = {},
    onCancel : () -> Unit = {}
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
                    onCancel()
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
                   onCancel()
               }
           ) {
               TextComponent(text = "취소")
           }
       }
   )
}