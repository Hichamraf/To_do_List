package com.hicham.home.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hicham.data.persistence.model.Task
import com.hicham.home.R
import com.hicham.home.ui.HomeAction.OnTaskCheckChanged


@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), onAddClicked: () -> Unit) {

    val state by viewModel.viewState.collectAsState()

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            shape = CircleShape,
            onClick = { onAddClicked.invoke() }) {
            Icon(painter = painterResource(R.drawable.baseline_add_24), null)
        }
    }, content =  {_->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)) {
            LazyColumn {
                items(state.taskList) {
                    TaskItem(it) {isChecked->
                        viewModel.processViewActions(OnTaskCheckChanged(isChecked,it))
                    }
                }
            }
        }
    })


}


@Composable
fun TaskItem(task: Task, onChecked: (Boolean) -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
        colors = CardDefaults.cardColors().copy(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)) {
        Row(modifier = Modifier.padding(8.dp)) {
            Checkbox(checked = task.isDone, onCheckedChange = {
                onChecked(it)
            })
            Column {
                Text(task.name)
                Text(task.description)
            }
        }
    }

}

@Preview
@Composable
fun Preview() {
    HomeScreen {

    }
}