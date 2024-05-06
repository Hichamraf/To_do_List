package com.hicham.task.addtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.hicham.task.R
import com.hicham.task.addtask.ui.AddTaskAction.OnAddTask
import com.hicham.task.addtask.ui.AddTaskAction.OnNameTextChanged
import java.util.*
import java.util.concurrent.TimeUnit

@Composable
fun AddTaskScreen(viewModel: AddTaskViewModel = hiltViewModel(), onGoBack: () -> Unit) {

    LaunchedEffect(key1 = viewModel.viewState) {
        viewModel.coordinatorEvent.collect {
            onGoBack.invoke()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.weight(1f))
            TaskInfos(viewModel)
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskInfos(viewModel: AddTaskViewModel) {

    val state by viewModel.viewState.collectAsState()
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val calendar = remember {
        Calendar.getInstance()
    }
    val currentYear = remember {
        calendar.get(Calendar.YEAR)
    }
    val dateState = rememberDatePickerState(
        yearRange = IntRange(currentYear, currentYear),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis > calendar.timeInMillis
            }

            override fun isSelectableYear(year: Int): Boolean {
                return year >= currentYear
            }
        }
    )
    val timeState = rememberTimePickerState()
    val showTimePicker = remember {
        mutableStateOf(false)
    }
    val showDatePicker = remember {
        mutableStateOf(false)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    DisposableEffect(key1 = state) {
        keyboardController?.show()
        onDispose { }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 2.dp, end = 2.dp, top = 0.dp, bottom = 0.dp),
            value = name, onValueChange = {
                name = it
                viewModel.processViewActions(OnNameTextChanged)
            },
            placeholder = {
                Text(text = stringResource(R.string.add_task_name_ph))
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            isError = state.nameError,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            })

        )

        TextField(
            modifier = Modifier
                .padding(start = 2.dp, end = 2.dp, top = 0.dp, bottom = 0.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            value = description, onValueChange = {
                description = it
            },
            placeholder = {
                Text(text = stringResource(R.string.task_description_ph))
            },
            singleLine = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            })
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { showDatePicker.value = true },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(Icons.Default.DateRange, contentDescription = "Calender")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Today")
            }
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(onClick = {
                viewModel.processViewActions(
                    OnAddTask(
                        name, description,
                        (dateState.selectedDateMillis?.plus(TimeUnit.HOURS.toMillis(timeState.hour.toLong())) ?: 0)
                                + TimeUnit.MINUTES.toMillis(timeState.minute.toLong())
                    )
                )
            }) {
                Icon(Icons.AutoMirrored.Outlined.Send, contentDescription = "Send")
            }
        }
    }
    CalenderPicker(datePicker = dateState, showDatePicker, showTimePicker, timeState)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalenderPicker(datePicker: DatePickerState, showDatePicker: MutableState<Boolean>, showTimePicker: MutableState<Boolean>, timeState: TimePickerState) {
    if (showDatePicker.value) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker.value = false
                showDatePicker.value = false
            },
            confirmButton = {
                OutlinedButton(onClick = {
                    showTimePicker.value = true
                    showDatePicker.value = false
                }) {
                    Text(text = "Choose Time")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDatePicker.value = false }) {
                    Text(text = "Dismiss")
                }

            },
            properties = DialogProperties()
        ) {
            datePicker.selectableDates
            DatePicker(state = datePicker)
        }
    }

    if (showTimePicker.value) {
        DatePickerDialog(onDismissRequest = { showTimePicker.value = false }, confirmButton = {
            OutlinedButton(onClick = { showTimePicker.value = false }) {
                Text(text = "Done")
            }
        },
            dismissButton = {
                OutlinedButton(onClick = {
                    showTimePicker.value = false
                }) {
                    Text(text = "Cancel")
                }
            }
        ) {
            TimePicker(state = timeState)
        }
    }

}

@Preview
@Composable
fun AddTaskPreview() {
    AddTaskScreen {

    }
}