package com.hicham.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.hicham.home.domain.model.Task

@Composable
fun HomeScreen(state: HomeScreenState) {
    Column {
        LazyColumn {
          items(state.taskList){
              TaskItem(it) {

              }
          }
        }
    }
}


@Composable
fun TaskItem(task: Task, onChecked: (Boolean) -> Unit) {
    Row {
        Checkbox(checked = task.isTaskDone, onCheckedChange = onChecked)
        Column {
            Text(task.taskName)
            Text(task.taskDescription)
        }
    }
}