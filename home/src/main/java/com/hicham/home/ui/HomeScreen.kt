package com.hicham.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.hicham.data.persistence.model.Task
import com.hicham.home.R

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), onAddClicked: () -> Unit) {

    val state by viewModel.viewState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(state.taskList) {
                TaskItem(it) {
                }
            }
        }

        FloatingActionButton(
            shape = CircleShape,
            onClick = { onAddClicked.invoke() }) {
            Icon(painter = painterResource(R.drawable.baseline_add_24), null)
        }
    }
}


@Composable
fun TaskItem(task: Task, onChecked: (Boolean) -> Unit) {
    Row {
        Checkbox(checked = task.isDone, onCheckedChange = onChecked)
        Column {
            Text(task.name)
            Text(task.description)
        }
    }
}