package com.hicham.task.uicomponents

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalenderPicker(
    datePicker: DatePickerState,
    showDatePicker: MutableState<Boolean>,
    onDateChanged: () -> Unit
) {
    if (showDatePicker.value) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker.value = false
            },
            confirmButton = {
                OutlinedButton(onClick = {
                    onDateChanged.invoke()
                    showDatePicker.value = false
                }) {
                    Text(text = "Save")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDatePicker.value = false }) {
                    Text(text = "Dismiss")
                }

            },
            properties = DialogProperties()
        ) {
            DatePicker(state = datePicker)
        }
    }
}


@Composable
fun PriorityMenu(
    priorityMenuShow: MutableState<Boolean>, keyboardController: SoftwareKeyboardController?,
    priorities: Array<String>,
    onItemSelected: (Int) -> Unit,
) {
    DropdownMenu(expanded = priorityMenuShow.value,
        onDismissRequest = {
            priorityMenuShow.value = false
            keyboardController?.show()
        })
    {
        priorities.forEachIndexed { index, s ->
            DropdownMenuItem(text = { Text(text = s) }, onClick = {
                priorityMenuShow.value = false
                onItemSelected(index)
            })
        }
    }
}


