package com.hicham.inbox.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.hicham.components.TaskItem
import com.hicham.inbox.ui.InBoxAction.OnTaskCheckChanged
import com.hicham.inbox.ui.InBoxAction.OnTaskFavoriteClicked
import com.hicham.inbox.ui.InBoxAction.OnTaskSelected

@Composable
fun InboxScreen(viewModel: InboxViewModel = hiltViewModel()) {

    val state = viewModel.viewState.collectAsState()
    LazyColumn {
        items(state.value.tasks, key = { it.id!! }) {
            TaskItem(it, onItemClicked = {
                viewModel.processViewActions(OnTaskSelected(it))
            }, onChecked = { isDone ->
                viewModel.processViewActions(OnTaskCheckChanged(isDone, it))
            }, onFavoriteClicked = { isFavorite ->
                viewModel.processViewActions(OnTaskFavoriteClicked(isFavorite, it))
            })
        }
    }
}