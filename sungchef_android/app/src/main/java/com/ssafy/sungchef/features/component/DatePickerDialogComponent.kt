package com.ssafy.sungchef.features.component

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable

/**
 * showDatePicker : DatePicker를 화면에 보여줄지 말지 정하는 State
 * onDateSelected : 사용자가 선택한 날짜를 전달
 * onDismiss : showDatePicker false로 변경
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogComponent(
    onAccept : (Long?) -> Unit,
    onCancel : (Boolean) -> Unit
) {
    val state = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = {
            onCancel(false)
        },
        confirmButton = {
            Button(
                onClick = {
                    val selectedDateMillis = state.selectedDateMillis
                    val todayMillis = System.currentTimeMillis()

                    if (selectedDateMillis != null && selectedDateMillis <= todayMillis) {
                        // 선택된 날짜가 오늘 이하인 경우, 선택을 수락합니다.
                        onAccept(selectedDateMillis)
                    } else {
                        // 미래 날짜가 선택된 경우, 예를 들어 사용자에게 메시지를 표시할 수 있습니다.
                        // 여기에 사용자에게 경고하는 코드를 추가하세요.
                        onCancel(true) // 또는 다른 적절한 반응을 할 수 있습니다.
                    }
                }
            ) {
                Text(
                  text = "확인"
                )
            }
        },
        dismissButton = {
            Button(onClick = { onCancel(false) }) {
                Text(
                    text = "취소"
                )
            }
        }
    ) {
        DatePicker(state = state)
    }
}