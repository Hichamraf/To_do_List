package com.hicham.task.taskdetails.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hicham.task.R
import com.hicham.task.taskdetails.ui.DetailEvent.GoBack
import com.hicham.task.taskdetails.ui.TaskDetailAction.OnDateChanged
import com.hicham.task.taskdetails.ui.TaskDetailAction.OnGoBack
import com.hicham.task.taskdetails.ui.TaskDetailAction.OnNameTextChanged
import com.hicham.task.taskdetails.ui.TaskDetailAction.OnTaskCheckBoxChanged
import com.hicham.task.taskdetails.ui.TaskDetailAction.UpdateTask
import com.hicham.task.uicomponents.CalenderPicker
import com.hicham.task.uicomponents.PriorityMenu
import com.hicham.task.utils.createCustomSelectableDates
import java.util.*
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(
    viewModel: TaskDetailsViewModel = hiltViewModel()
) {

    ModalBottomSheet(onDismissRequest = { viewModel.processViewActions(OnGoBack) }) {
        Column {
            TaskInfos(viewModel)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskInfos(viewModel: TaskDetailsViewModel) {

    val state by viewModel.viewState.collectAsState()
    var name by remember {
        mutableStateOf(
            TextFieldValue(
                text = ""
            )
        )
    }
    var description by remember { mutableStateOf("") }
    val isFirstLoad = remember {
        mutableStateOf(true)
    }
    val priorities = LocalContext.current.resources.getStringArray(com.hicham.core.R.array.priorities)
    val isTaskDone = remember {
        mutableStateOf(false)
    }
    val priority = remember {
        mutableIntStateOf(0)
    }
    if (state.task?.name?.isNotEmpty() == true && isFirstLoad.value) {
        val value = name.text.plus(state.task?.name.orEmpty())
        name = TextFieldValue(
            text = value,
            selection = TextRange(value.length)
        )
        description = state.task?.description.orEmpty()
        isTaskDone.value = state.task?.isDone ?: false
        priority.intValue = state.task?.priority ?: 0
        isFirstLoad.value = false
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = focusRequester) {
        focusRequester.requestFocus()
        delay(100)
        keyboardController?.show()
    }

    val currentYear = remember {
        Calendar.getInstance().get(Calendar.YEAR)
    }
    val showDatePicker = remember {
        mutableStateOf(false)
    }
    val showPriorityPicker = remember {
        mutableStateOf(false)
    }
    val dateState = rememberDatePickerState(
        selectableDates = createCustomSelectableDates(),
        yearRange = IntRange(currentYear, currentYear)
    )

    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Checkbox(checked = state.task?.isDone ?: false, onCheckedChange = {
                viewModel.processViewActions(OnTaskCheckBoxChanged(it))
                isTaskDone.value = it
            })
            OutlinedTextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = name,
                onValueChange = {
                    name = it
                    viewModel.processViewActions(OnNameTextChanged(it.text))
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                label = {
                    Text(text = stringResource(R.string.add_task_name_ph))
                },
                shape = RoundedCornerShape(8.dp),
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Info, contentDescription = "Info", modifier = Modifier.padding(start = 12.dp))
            OutlinedTextField(
                modifier = Modifier.padding(start = 8.dp),
                value = description,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                onValueChange = {
                    description = it
                }, label = {
                    Text(text = stringResource(R.string.task_description_ph))
                }, singleLine = false, keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ), keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                })
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                modifier = Modifier
                    .padding(all = 4.dp),
                onClick = {
                    showDatePicker.value = true
                },
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.DateRange, contentDescription = "Date")
                Text(text = state.date ?: "Today", modifier = Modifier.padding(start = 8.dp))
            }
            OutlinedButton(
                modifier = Modifier
                    .padding(all = 4.dp),
                onClick = { showPriorityPicker.value = true },
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_flag), contentDescription = "Priority")
                Text(text = priorities[state.task?.priority ?: 0], modifier = Modifier.padding(start = 8.dp))
            }
            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                shape = RoundedCornerShape(8.dp),
                enabled = state.sendButtonEnabled,
                modifier = Modifier
                    .padding(all = 4.dp), onClick = {
                    viewModel.processViewActions(
                        UpdateTask(
                            name.text, description,
                            isTaskDone.value, dateState.selectedDateMillis, priority.intValue
                        )
                    )
                }) {
                Icon(Icons.AutoMirrored.Outlined.Send, contentDescription = "Send")
            }
        }

    }

    CalenderPicker(dateState, showDatePicker) {
        dateState.selectedDateMillis?.let {
            viewModel.processViewActions(OnDateChanged(it))
        }
    }
    PriorityMenu(priorityMenuShow = showPriorityPicker, keyboardController = keyboardController, priorities = priorities) {
        priority.intValue = it
    }
}
