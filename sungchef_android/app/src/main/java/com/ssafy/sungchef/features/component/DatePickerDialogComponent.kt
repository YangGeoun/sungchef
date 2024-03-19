package com.ssafy.sungchef.features.component

import android.app.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

/**
 * showDatePicker : DatePicker를 화면에 보여줄지 말지 정하는 State
 * onDateSelected : 사용자가 선택한 날짜를 전달
 * onDismiss : showDatePicker false로 변경
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogComponent(
    onAccept : (Long?) -> Unit,
    onCancel : () -> Unit
) {
    val state = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = { },
        confirmButton = {
            Button(
                onClick = {
                    onAccept(state.selectedDateMillis)
                }

            ) {
                Text(
                  text = "확인"
                )
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text(
                    text = "취소"
                )
            }
        }
    ) {
        DatePicker(state = state)
    }
}