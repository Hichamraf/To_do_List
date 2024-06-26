package com.hicham.task.addtask.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hicham.task.R
import com.hicham.task.addtask.ui.AddTaskAction.OnAddTask
import com.hicham.task.addtask.ui.AddTaskAction.OnNameTextChanged
import com.hicham.core.utils.getTodayStartOfDayMillis
import com.hicham.task.addtask.ui.AddTaskAction.OnDateChanged
import com.hicham.task.uicomponents.CalenderPicker
import com.hicham.task.uicomponents.PriorityMenu
import com.hicham.task.utils.createCustomSelectableDates
import java.util.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(viewModel: AddTaskViewModel = hiltViewModel()) {
    var showBottomSheet by remember { mutableStateOf(false) }
    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet = false
        }
    ) {
        Column {
            TaskInfos(viewModel) {
                showBottomSheet = false
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskInfos(viewModel: AddTaskViewModel, onBottomSheetDismissed: () -> Unit) {

    val state by viewModel.viewState.collectAsState()
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val showDatePicker = remember {
        mutableStateOf(false)
    }
    val calendar = remember {
        Calendar.getInstance()
    }
    val priorityMenuShow = remember {
        mutableStateOf(false)
    }

    val priorities = LocalContext.current.resources.getStringArray(com.hicham.core.R.array.priorities)

    var selectedIndex by remember { mutableIntStateOf(0) }
    val currentYear = remember {
        calendar.get(Calendar.YEAR)
    }
    val dateState = rememberDatePickerState(
        yearRange = IntRange(currentYear, currentYear),
        selectableDates = createCustomSelectableDates()
    )

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = focusRequester) {
        focusRequester.requestFocus()
        delay(100)
        keyboardController?.show()
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, end = 8.dp)
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .padding(start = 2.dp, end = 2.dp, top = 0.dp, bottom = 0.dp),
            value = name, onValueChange = {
                name = it
                viewModel.processViewActions(OnNameTextChanged(it))
            },
            placeholder = {
                Text(text = stringResource(R.string.add_task_name_ph))
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
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
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
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
                Text(text = state.dateButtonName ?: "Today")
            }
            OutlinedButton(
                onClick = {
                    priorityMenuShow.value = true
                    keyboardController?.hide()
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_flag), contentDescription = "Priority")
                Text(text = priorities[selectedIndex])
            }
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(
                onClick = {
                    onBottomSheetDismissed.invoke()
                    viewModel.processViewActions(
                        OnAddTask(
                            name, description,
                            getSelectedTime(dateState),
                            selectedIndex,

                            )
                    )
                },
                enabled = state.sendButtonEnabled
            ) {
                Icon(Icons.AutoMirrored.Outlined.Send, contentDescription = "Send")
            }
        }
    }
    CalenderPicker(dateState, showDatePicker) {
        dateState.selectedDateMillis?.let {
            viewModel.processViewActions(OnDateChanged(it))
        }
    }
    PriorityMenu(priorityMenuShow, keyboardController, priorities) {
        selectedIndex = it
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun getSelectedTime(dateState: DatePickerState) = dateState.selectedDateMillis ?: getTodayStartOfDayMillis()


