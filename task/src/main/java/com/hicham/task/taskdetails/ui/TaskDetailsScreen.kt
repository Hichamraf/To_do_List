package com.hicham.task.taskdetails.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hicham.task.R


@Composable
fun TaskDetailsScreen(
    viewModel: TaskDetailsViewModel = hiltViewModel(),
    goBack: () -> Unit
) {

    LaunchedEffect(key1 = viewModel.viewState) {
        viewModel.coordinatorEvent.collect {
            goBack.invoke()
        }
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.weight(1f))
            TaskInfos(viewModel)
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}


@Composable
private fun TaskInfos(viewModel: TaskDetailsViewModel) {

    val state by viewModel.viewState.collectAsState()
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val isFirstLoad = remember {
        mutableStateOf(true)
    }
    if (state.task?.name?.isNotEmpty() == true && isFirstLoad.value) {
        name = state.task?.name.orEmpty()
        description = state.task?.description.orEmpty()
        isFirstLoad.value = false
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            value = name,
            onValueChange = {
                name = it
                viewModel.processViewActions(TaskDetailAction.OnNameTextChanged)
            },
            label = {
                Text(text = stringResource(R.string.add_task_name_ph))
            },
            supportingText = {
                if (state.nameError) {
                    Text(
                        text = "Please enter a task name", color = MaterialTheme.colorScheme.error
                    )
                }
            },
            shape = RoundedCornerShape(8.dp),
            isError = state.nameError,

            )

        OutlinedTextField(modifier = Modifier
            .padding(all = 16.dp)
            .height(200.dp)
            .fillMaxWidth(),
            value = description,

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

        ElevatedButton(modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp), onClick = {
            viewModel.processViewActions(TaskDetailAction.UpdateTask(name, description, state.task?.isDone ?: false))
        }) {
            Text(text = stringResource(R.string.update_task_btn_name))
        }
    }
}
