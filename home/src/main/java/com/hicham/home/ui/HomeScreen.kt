package com.hicham.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hicham.core.theme.ToDoListTheme
import com.hicham.data.persistence.model.Task
import com.hicham.home.R
import com.hicham.home.ui.HomeAction.OnTaskCheckChanged


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onAddClicked: () -> Unit,
    onItemClicked: () -> Unit
) {

    val state by viewModel.viewState.collectAsState()

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            shape = CircleShape,
            onClick = { onAddClicked.invoke() }) {
            Icon(painter = painterResource(R.drawable.baseline_add_24), null)
        }
    }, content = { _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp)
        ) {
            LazyColumn {
                items(state.taskList) {
                    TaskItem(it, onItemClicked = {
                        viewModel.processViewActions(HomeAction.OnTaskSelected(it))
                        onItemClicked.invoke()
                    },
                        onFavoriteClicked = { isFav ->
                            viewModel.processViewActions(HomeAction.OnTaskFavoriteClicked(isFav, it))
                        },
                        onChecked = { isChecked ->
                            viewModel.processViewActions(OnTaskCheckChanged(isChecked, it))
                        })
                }
            }
        }
    })


}


@Composable
fun TaskItem(
    task: Task, onItemClicked: () -> Unit,
    onChecked: (Boolean) -> Unit,
    onFavoriteClicked: (Boolean) -> Unit
) {
    val priorities = LocalContext.current.resources.getStringArray(com.hicham.core.R.array.priorities)
    ToDoListTheme {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClicked.invoke()
                }
                .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
            colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.background),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Row(modifier = Modifier.padding(8.dp)) {
                Checkbox(checked = task.isDone, onCheckedChange = {
                    onChecked(it)
                })
                Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                    Text(
                        text = task.name,
                        textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None
                    )
                    if (task.description.isNotEmpty())
                        Text(
                            modifier = Modifier.width(200.dp),
                            text = task.description,
                            textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                }
                AssistChip(
                    onClick = { },
                    label = { Text(text = priorities[task.priority], fontSize = 8.sp) },
                    colors = AssistChipDefaults.assistChipColors().copy(containerColor = getChipColor(task.priority),
                        labelColor = Color.Black)
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        onFavoriteClicked.invoke(!task.isFavorite)
                    },
                    enabled = task.isDone.not()
                )
                {
                    Icon(
                        painter = if (task.isFavorite) painterResource(id = com.hicham.core.R.drawable.favorite_filled)
                        else painterResource(id = com.hicham.core.R.drawable.favorite_empty),
                        contentDescription = "favorite button",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 16.dp)
                            .fillMaxHeight()


                    )
                }
            }
        }
    }


}

private fun getChipColor(priority: Int): Color {
    return when (priority) {
        0 -> Color.White
        1 -> Color.Yellow
        2 -> Color.Green
        3 -> Color.Red
        else -> Color.Yellow
    }
}

@Preview
@Composable
fun Preview() {

}